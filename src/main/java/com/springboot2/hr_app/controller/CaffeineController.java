package com.springboot2.hr_app.controller;

import com.springboot2.hr_app.HrAppApplication;
import com.springboot2.hr_app.config.caffeineConfig;
import com.springboot2.hr_app.config.caffeineConfig;
import com.springboot2.hr_app.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Collection;


@RestController
@RequestMapping("/caffeine")
public class CaffeineController {

    //@Resource
    @Autowired
    private caffeineConfig caffeineConfig;
    @Autowired
    private com.springboot2.hr_app.service.countryService countryService;
    @Autowired
    private com.springboot2.hr_app.service.regionService regionService;
    @Autowired
    private com.springboot2.hr_app.service.jobService jobService;
    @Autowired
    private com.springboot2.hr_app.service.jobHistoryService jobHistoryService;
    @Autowired
    private locationService locationService;
    @Autowired
    private departmentService deptService;
    @Autowired
    private employeeService employeeService;
    @Autowired
    private CacheManager caffeine;
    @Resource
    private caffeineService service;

    private static final Logger LOG = LoggerFactory.getLogger(CaffeineController.class);

    @GetMapping("/{cacheName}")
    public Collection<String> cacheNames() {
        return caffeineConfig.cacheManager().getCacheNames();
    }

    @PostMapping(path = "/{cacheName}")
    public String refreshCache(@PathVariable("cacheName") final String cacheName) {
        LOG.info("Attempting to refresh cache...");
        getAndClearCache(cacheName);
        Cache cache = caffeineConfig.cacheManager().getCache(cacheName);
        assert cache != null;
        cache.evict(cacheName);
        //cache.put(cacheName,cache);

        return "Done. Refreshed.";
            //cache.put(key,cacheName);

        /*Caffeine.newBuilder()
                .refreshAfterWrite(10, TimeUnit.SECONDS)
                .build(cache);*/
    }

    // CLEAR CACHE OF AN INSTANCE/DATUM
    @DeleteMapping(path = "/{cacheName}")
    public void invalidateCache(@PathVariable("cacheName") final String cacheName) {
        LOG.info("Attempting to invalidate cache: {}", cacheName);
        getAndClearCache(cacheName);
    }

    //CLEAR CACHE OF EVERY INSTANCE/ ALL DATA
    @PostMapping(path = "/")
    public void invalidateAllCache() {
        countryService.refreshAllCache();
        regionService.refreshCache();
        jobHistoryService.refreshCache();
        employeeService.refreshCache();
        jobService.refreshCache();
        locationService.refreshCache();
        deptService.refreshCache();

        //Collection<String> cacheNames = caffeineConfig.cacheManager().getCacheNames();
        LOG.info("\nAttempting to invalidate all caches for: {}", caffeineConfig.cacheManager().getCacheNames());
        //cacheNames.forEach(this::getAndClearCache);
    }

    // GET AND CLEAR CACHE ver2 - done by a controller instead of putting in each instance
    private void getAndClearCache(final String cacheName) {
        final Cache cache = caffeineConfig.cacheManager().getCache(cacheName);
        LOG.info("Attempting to getting the cache for: {}", cacheName);
        if (cache == null) {
            LOG.info("Cache not found...");
            throw new IllegalArgumentException("Invalid cache name: " + cacheName + "is NOT found... \n Try again");
        }
        cache.clear();
        //countryService.refreshCache();
        LOG.info("Cache invalidation complete!");
    }


    // get Caffeine to show statistics
    @GetMapping("/{cacheName}/stats")
    public Object getStats(@PathVariable String cacheName) {
        return service.showStats(cacheName);
    }

}
