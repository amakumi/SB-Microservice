package com.springboot2.hr_app.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.CacheManager;

@Configuration
public class caffeineConfig {

    //@Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
                "countries","dept","employee","job","jobhistory","location","region");
        cacheManager.setCaffeine(cacheBuilder());
        //cacheManager.setCaffeine(cacheBuilder);

        return cacheManager;
    }

    //@Bean
    public Caffeine <Object, Object> cacheBuilder() {

      return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(500) // UNIT IS IN NON-NEGATIVE LONG...? - Specifies the maximum no of entries the cache may contain
                //.expireAfterAccess(10, TimeUnit.MINUTES)
                .weakKeys()
                .recordStats();
                //.build();
                //.refreshAfterWrite(10,TimeUnit.SECONDS);

    }

    //@Bean
    /*public Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder()
            .maximumSize(500)
            .initialCapacity(100)
            .recordStats()
            .weakKeys();
            //.build();*/

}
