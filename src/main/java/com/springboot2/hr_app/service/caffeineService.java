package com.springboot2.hr_app.service;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.springboot2.hr_app.config.caffeineConfig;
import com.springboot2.hr_app.controller.CaffeineController;

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

@Service
//@CacheConfig()
public class caffeineService {

    //@Resource
    @Autowired
    private CacheManager caffeine;

    @Resource
    //@Autowired
    private caffeineConfig caffeineConfig;

    //@Resource
    @Autowired
    private CaffeineController controller;

    private static final Logger LOG = LoggerFactory.getLogger(caffeineService.class);

    public Map<String, Object> showStats(@PathVariable String cacheName) {
        LOG.info("Attempting to open stats on HashMap function");

        //CaffeineCache targetCache = (CaffeineCache) caffeineConfig.cacheManager().getCache(cacheName);

        CaffeineCache targetCache = (CaffeineCache) caffeine.getCache(cacheName);

        //CacheStats targetCache = (CacheStats) caffeine.getCache(cacheName);
        //caffeineConfig targetCache = (caffeineConfig) caffeine.getCache(cacheName);

        //CacheStats stats = targetCache.getNativeCache().stats();

        CacheStats stats;
        //LOG.info(controller.refreshCache(cacheName));

        if (targetCache != null) {
            LOG.info("Cache stats found. \n Cache Name: "+ cacheName);
            LOG.info("Caffeine cache content: \n Cache Name: "+ targetCache);

            //stats = caffeineCache.getNativeCache().stats();
            //targetCache.getNativeCache().stats();

            //stats = targetCache.getNativeCache().stats();

            stats = targetCache.getNativeCache().stats();

            //targetCache.cacheManager().getCache(cacheName);

            LOG.info(" \n the stats: "+ stats);

            //LOG.debug(showStats(cacheName));
            //stats.toString() = caffeineConfig.getStats3(cacheName);
            ///stats = caffeineConfig.cacheManager().getCache()

            if (stats.requestCount() > 0) {
                Map<String, Object> map = new HashMap<>(24,24);

                LOG.info(controller.refreshCache(cacheName));

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

}
