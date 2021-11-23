package com.springboot2.hr_app;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.sun.media.jfxmedia.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//@EnableJpaRepositories("com.springboot2.hr_app.repository")
//@EnableEurekaClient
//@Configuration
@EnableCaching
public class HrAppApplication {

    @LoadBalanced
    public static void main(String[] args) {
        //Logger.INFO(refreshCache.stats());
        SpringApplication.run(HrAppApplication.class, args);
    }

    //@Bean(name="cacheManager")
    /*public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
                "countries","dept","employee","job","jobhistory","location","region");
        cacheManager.setCaffeine(cacheBuilder());
        //cacheManager.setCaffeine(cacheBuilder);


        return cacheManager;
    }

    //@Bean
    /*public Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder()
            .maximumSize(500)
            .initialCapacity(100)
            .recordStats()
            .weakKeys();
            //.build();*/

    //@Bean
    /*public Caffeine<Object, Object> cacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(500) // UNIT IS IN NON-NEGATIVE LONG...? - Specifies the maximum no of entries the cache may contain
                //.expireAfterAccess(10, TimeUnit.MINUTES)
                .weakKeys()
                .recordStats();
        //.build();
        //.refreshAfterWrite(10,TimeUnit.SECONDS);

    }*/

}
