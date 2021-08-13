package com.paloalto.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheManagerConfiguration {

    public static final String ANAGRAM_CACHE_NAME = "anagram";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(ANAGRAM_CACHE_NAME);
    }
}
