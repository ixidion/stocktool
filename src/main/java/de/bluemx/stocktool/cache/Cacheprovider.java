package de.bluemx.stocktool.cache;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
@Singleton
public class Cacheprovider {

    private Cache<String,String> cache;

    @Inject
    private Cacheprovider() {
        cache = createCache();
    }

    private Cache<String, String> createCache() {
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();
        MutableConfiguration<String, String> configuration =
                new MutableConfiguration<String, String>()
                        .setTypes(String.class, String.class)
                        .setStoreByValue(false)
                        .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ETERNAL));
        return cacheManager.createCache("jCache", configuration);
    }


    public Cache<String, String> getCache() {
        return cache;
    }
}
