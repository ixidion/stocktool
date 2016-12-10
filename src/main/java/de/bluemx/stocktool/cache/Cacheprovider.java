package de.bluemx.stocktool.cache;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;

public class Cacheprovider {

    private Cache<String,String> cache;

    private static Cacheprovider instance = new Cacheprovider();

    public static Cacheprovider getInstance() {return instance;}

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
