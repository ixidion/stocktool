package de.bluemx.stocktool.fetch;

import com.google.inject.Inject;
import de.bluemx.stocktool.annotations.*;
import de.bluemx.stocktool.converter.Conversion;
import de.bluemx.stocktool.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static de.bluemx.stocktool.helper.ReflectionUtil.*;
import static de.bluemx.stocktool.helper.StringUtil.replacePlaceholder;

public class GenericFetcher<T> {

    final static Logger log = LoggerFactory.getLogger(GenericFetcher.class);

    private Provider[] providers;
    private UrlFetcher urlFetcher;
    private StringUtil stringUtil;
    private Set<String> processedFields = new HashSet<>();

    @Inject
    public GenericFetcher(UrlFetcher urlFetcher) {
        this.urlFetcher = urlFetcher;
    }

    public <T> T process(T pojo) {
        Field[] fields = pojo.getClass().getDeclaredFields();

        saveClassAnnotations(pojo);
        for (Field field : fields) {
            if (!processedFields.contains(field.getName())) {
                log.debug("Processing field: {}", field.getName());
                recursiveFetchField(field, pojo);
            }
        }
        return pojo;

    }

    private void recursiveFetchField(Field field, Object pojo) {
        Resolvers resolvers = field.getAnnotation(Resolvers.class);
        if (resolvers instanceof Resolvers) {
            for (Resolver resolver : resolvers.value()) {
                String providername = resolver.provider();
                Provider provider = getProviderByName(providername);
                if (!provider.required().equals("")) {
                    Field newField = getFieldByName(provider.required(), pojo);
                    recursiveFetchField(newField, pojo);
                }
                fetchField(field, pojo, resolver, provider);
                if (reflectionGet(field, pojo) != null) {
                    break;
                }
                log.info("Fetching alternative for {} with Resolver {}", field.getName(), resolver.provider());
            }
        }
    }

    private void fetchField(Field field, Object pojo, Resolver resolver, Provider provider) {
        if (field.getAnnotation(ProviderMap.class) != null) {
            fetchProviderMap(field, pojo, resolver, provider);
        } else {
            fetchCommonField(field, pojo, resolver, provider);
        }
        processedFields.add(field.getName());
    }

    private void fetchCommonField(Field field, Object pojo, Resolver resolver, Provider provider) {
        try {
            String[] strArr = fetchUrlWith(provider, resolver, pojo);
            Conversion converter = getConverter(resolver);
            Object obj = converter.convert(strArr);
            reflectionSet(field, pojo, obj);
        } catch (ValidationException e) {
            log.error("Please check the Definition of field {}. Actual value: {} Expected value: {}",
                    field.getName(), e.getActual(), e.getValidator().expected());
        } catch (IllegalArgumentException e) {
            log.error("Converter Error at field {} with Resolver {}. Error-Msg: {}", field, resolver, e.getMessage());
        }
    }

    private void fetchProviderMap(Field field, Object pojo, Resolver resolver, Provider provider) {
        Map<Dataprovider, String> providerMap = null;
        providerMap = (Map<Dataprovider, String>) reflectionGet(field, pojo);
        if (providerMap == null) {
            providerMap = new HashMap<>();
        }
        String[] obj = fetchUrlWith(provider, resolver, pojo);
        if (obj != null && obj.length == 1) {
            providerMap.put(provider.dataprovider(), obj[0]);
            reflectionSet(field, pojo, providerMap);
        } else {
            log.error("At Field {} - Reduce Extract Annotations to one.", field.getName());
            throw new IllegalArgumentException("Annotation Providermap can only fetch one value!");
        }
    }

    private String[] fetchUrlWith(Provider provider, Resolver resolver, Object pojo) {
        String url = provider.url();
        Map<String, String> variablesList = getVariables(provider, pojo);
        String completeUrl = createResultingUrl(url, variablesList);
        return urlFetcher.urlFetch(completeUrl, provider, resolver, pojo);
    }

    private String createResultingUrl(String url, Map<String, String> variablesList) {
        String finishedUrl = url;
        for (String variableKey : variablesList.keySet()) {
            finishedUrl = replacePlaceholder(finishedUrl, variableKey, variablesList.get(variableKey));
        }
        log.info("Created URL: {}", finishedUrl);
        return finishedUrl;
    }

    private Map<String, String> getVariables(Provider provider, Object pojo) {
        Map<String, String> resultingVariables = new HashMap<>();
        Variable[] variableAnnotations = provider.variables();
        for (Variable variable : variableAnnotations) {
            Field field = getFieldByName(variable.source(), pojo);
            if (field.getType().equals(Map.class)) {
                Map<Dataprovider, String> configMap = (Map) reflectionGet(field, pojo);
                if (configMap != null) {
                    String variableValue = configMap.get(provider.dataprovider());
                    resultingVariables.put(variable.key(), variableValue);
                } else {
                    throw new IllegalArgumentException(
                            String.format("Definition is wrong. Field %s requested, but is null.", variable.source()));
                }
            } else if (field.getType().equals(String.class)) {
                String variableValue = (String) reflectionGet(field, pojo);
                resultingVariables.put(variable.key(), variableValue);
            } else {
                throw new IllegalArgumentException("Definition is wrong.");
            }
        }
        return resultingVariables;
    }


    private Field getFieldByName(String required, Object obj) {
        try {
            return obj.getClass().getDeclaredField(required);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(String.format("Fieldname %s not found.", required));
        }
    }

    private Provider getProviderByName(String name) {
        for (Provider provider : this.providers) {
            if (provider != null) {
                if (provider.name().equals(name)) return provider;
            }
        }
        log.error("Providername {} not found", name);
        throw new RuntimeException("Providername not found.");
    }

    private void saveClassAnnotations(Object obj) {
        Class stockClass = obj.getClass();

        Annotation[] stockClassAnnotations = stockClass.getAnnotations();

        for (Annotation annotation : stockClassAnnotations) {
            if (annotation instanceof Config) {
                Config config = (Config) annotation;
                this.providers = config.providers();
            }
        }
    }
}
