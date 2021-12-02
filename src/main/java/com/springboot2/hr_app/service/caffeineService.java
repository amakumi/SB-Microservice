package com.springboot2.hr_app.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.springboot2.hr_app.config.caffeineConfig;

import com.springboot2.hr_app.entity.employees;
import com.springboot2.hr_app.repository.employeeRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Service
@AllArgsConstructor
public class caffeineService {

    // this service will take over the remaining services - employeeService first
    //all logic will be put here

    //@Resource
    @Autowired
    private CacheManager caffeine;

    @Resource
    //@Autowired
    private caffeineConfig caffeineConfig;

    @Autowired
    private RestTemplate restTemp;

    @Autowired
    private employeeRepo empRepo;

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

    public ConcurrentMap<Object, Object> getCachedKeys (String cacheName) {
        LOG.info("Inside the cache service");
        CaffeineCache cache = (CaffeineCache) caffeineConfig.cacheManager().getCache(cacheName);
        if (cache != null) {
            Cache <Object, Object> nativeCache = cache.getNativeCache();
            System.out.println(nativeCache.asMap());
            return nativeCache.asMap();
        }
        else {
           LOG.info("Unable to find cache. Have you entered the correct cache?");
             return null;
        }

    }
    // functions/methods for to hit to employee Service

    @Cacheable(value = "employee")
    public void addCache(Integer employeeId) {
        LOG.info("Attempting to add cache into data...");
        employees emp = empRepo.findByemployee_id(employeeId);

        restTemp.put("http://EMPLOYEE-DATA-SERVICE/employee/"+ emp.getEmployee_id() ,employees.class);

    }

    @Cacheable(value = "employee")
    public void getCache(Integer employeeId) {
        LOG.info("Attempting to get cache into data...");
        employees emp = empRepo.findByemployee_id(employeeId);

        restTemp.getForEntity("http://EMPLOYEE-DATA-SERVICE/employee/" ,employees.class);

    }




}
