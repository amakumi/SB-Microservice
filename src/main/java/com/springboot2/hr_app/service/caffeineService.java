package com.springboot2.hr_app.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.springboot2.hr_app.config.caffeineConfig;
import com.springboot2.hr_app.controller.CaffeineController;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class caffeineService {

    //@Resource
    @Autowired
    private CacheManager caffeine;

    @Resource
    //@Autowired
    private caffeineConfig caffeineConfig;


    private static final Logger LOG = LoggerFactory.getLogger(caffeineService.class);

    public Map<String, Object> showStats(@PathVariable String cacheName) {
        LOG.info("Attempting to open stats on HashMap function");

        CaffeineCache targetCache = (CaffeineCache) caffeine.getCache(cacheName);

        CacheStats stats;

        if (targetCache != null) {
            LOG.info("Cache stats found. \n Cache Name: "+ cacheName);
            LOG.info("Caffeine cache content: \n Cache Name: "+ targetCache);

            stats = targetCache.getNativeCache().stats();

            LOG.info(" \n the stats: "+ stats);

            if (stats.requestCount() > 0) {
                Map<String, Object> map = new HashMap<>(24,24);

                map.put(" Retrieving Cache for  ", cacheName);
                assert false;
                map.put(" Number of requests    ", stats.requestCount());
                map.put(" Number of hits        ", stats.hitCount());
                map.put(" Number of hit rate    ", stats.hitRate());

                map.put(" Number of misses      ", stats.missCount());

                map.put(" Load success times    ", stats.loadSuccessCount());
                map.put(" Load failures         ", stats.loadFailureCount());
                map.put(" Failure Percentage    ", stats.loadFailureRate()+" %");

                map.put(" TOTAL LOAD TIME       ", stats.totalLoadTime()+" ns");

                map.put(" Total recycling time  ", stats.evictionCount());
                map.put(" Eviction weight       ", stats.evictionWeight());

                return map;
            }

            else {
                LOG.info("No requests compiled - Cache data not found: All zeroes...");
                return null;
                //return Collections.emptyMap();
            }
        }
        else {
            LOG.info("Cache data not found");
            return Collections.emptyMap();
        }

    }

    public Set<Object> getCachedKeys (String cacheName) {
        LOG.info("Inside the cache service");
        CaffeineCache cache = (CaffeineCache) caffeineConfig.cacheManager().getCache(cacheName);
        if (cache != null) {
            Cache <Object, Object> nativeCache = cache.getNativeCache();
            return nativeCache.asMap().keySet();
        }
        else {
            LOG.info("Stats not found. Have you accessed anything to the cache?");
            return null;
        }

    }



}
