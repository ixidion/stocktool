package de.bluemx.stocktool.fetch;

import de.bluemx.stocktool.annotations.*;
import de.bluemx.stocktool.model.StockQuoteData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.bluemx.stocktool.helper.ReflectionUtil.reflectionGet;
import static de.bluemx.stocktool.helper.ReflectionUtil.reflectionSet;

public class GenericFetcher<T> {

    final static Logger log = LoggerFactory.getLogger(GenericFetcher.class);

    private Provider[] providers;

    public <T> T process(T pojo) {
        Field[] fields = pojo.getClass().getDeclaredFields();

        saveClassAnnotations(pojo);
        for (Field field : fields) {
            recursiveFetch(field, pojo);
        }
        return pojo;

    }

    private void recursiveFetch(Field field, Object pojo) {
        Resolvers resolvers = field.getAnnotation(Resolvers.class);
        if (resolvers instanceof Resolvers) {
            for (Resolver resolver : resolvers.value()) {
                String providername = resolver.name();
                Provider provider = getProviderByName(providername);
                if (!provider.required().equals("")) {
                    Field newField = getFieldByName(provider.required(), pojo);
                    recursiveFetch(newField, pojo);
                }
                fetch(field, pojo, resolver, provider);
            }
        }
    }

    private void fetch(Field field, Object pojo, Resolver resolver, Provider provider) {
        if (field.getAnnotation(ProviderMap.class) != null) {
            Map<Dataprovider, String> providerMap = null;
            providerMap = (Map<Dataprovider, String> ) reflectionGet(field, pojo);
            if (providerMap == null) {
                providerMap = new HashMap<>();
            }
            String[]  obj = fetchValue(field, resolver, provider, pojo);
            if (obj != null && obj.length == 1) {
                providerMap.put(provider.dataprovider(), obj[0]);
                reflectionSet(field, pojo, providerMap);
            } else {
                log.error("At Field {} - Reduce Extract Annotations to one.", field.getName());
                throw new IllegalArgumentException("Annotation Providermap can only fetch one value!");
            }
        } else {
            String[] obj;
            obj = fetchValue(field, resolver, provider, pojo);
            reflectionSet(field, pojo, String.valueOf(obj[0]));
        }
    }

    private String[] fetchValue(Field field, Resolver resolver, Provider provider, Object pojo) {
        log.debug("Fetch field: {}", field.getName());
        List<String> extractedString = new ArrayList<>();
        String fetchedString = fetchUrlWith(provider, pojo);
        Extract[] extractors = resolver.extractors();

        for (Extract extractor : extractors) {
            if (extractor.searchType().equals(SearchType.REGEXP)) {
                Pattern pattern = Pattern.compile(extractor.expression());
                Matcher match = pattern.matcher(fetchedString);
//                if (match.groupCount() > 0) {
//                    extractedString.add(match.group());
                extractedString.add("REGEXP_MATCH_" + field.getName());
                log.debug("REGEXP_MATCH field: {}", field.getName());
//                }
            } else if (extractor.searchType().equals(SearchType.XPath)) {
                extractedString.add("XPATH_MATCH_" + field.getName());
                log.debug("XPATH_MATCH field: {}", field.getName());
            } else {
                throw new IllegalArgumentException("Given SearchType is not implemented.");
            }
        }

        return extractedString.toArray(new String[extractedString.size()]);
    }

    private String fetchUrlWith(Provider provider, Object pojo) {
        String url = provider.url();
        Variable[] variableAnnotations = provider.variables();
        Map<String, String> variablesList = getVariables(provider, pojo);

        return "";
    }

    private Map<String, String> getVariables(Provider provider, Object pojo) {
        Map<String, String> resultingVariables = new HashMap<>();
        Variable[] variableAnnotations = provider.variables();
        for (Variable variable : variableAnnotations) {
            Field field = getFieldByName(variable.source(), pojo);
            if (field.getType().equals(Map.class)) {
                Map<Dataprovider, String> configMap = (Map) reflectionGet(field, pojo);
                String variableValue = configMap.get(provider.dataprovider());
                resultingVariables.put(variable.key(), variableValue);
            } else if (field.getType().equals(String.class)) {
                String variableValue = (String) reflectionGet(field, pojo);
                resultingVariables.put(variable.key(), variableValue);
            } else {
                throw new IllegalArgumentException("Definition is wrong.");
            }
        }
        return resultingVariables;
    }

    private String replacePlaceholder(String text, String searchstring, String replacement) {
        String bracketSearchstring = "{" + searchstring + "}";
        if (StringUtils.countMatches(text, bracketSearchstring) == 1) {
            return StringUtils.replaceOnceIgnoreCase(text, bracketSearchstring, replacement);
        } else {
            log.error("String '{}' not found or found more than one time in text: {}", bracketSearchstring, text);
            throw new IllegalArgumentException("String not found.");
        }

    }


    private Field getFieldByName(String required, Object obj) {
        Field[] fields = obj.getClass().getFields();
        for (Field field : fields) {
            if (field.getName().equals(required)) return field;
        }
        log.error("Fieldname {} not found", required);
        throw new RuntimeException("Fieldname not found.");
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
