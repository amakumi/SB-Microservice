// STEP THREE
// PUT SERVICES/FUNCTIONS HERE
// Services will talk to the Repository

package com.springboot2.hr_app.service;

import com.springboot2.hr_app.config.caffeineConfig;
import com.springboot2.hr_app.entity.countries;
import com.springboot2.hr_app.repository.countryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
//import org.springframework.cache.caffeine.CaffeineCache;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = ("countries"))
public class countryService {

    @Autowired
    public countryRepo repo;

    @Resource
    private caffeineConfig caffeineConfig;

    //@Autowired
    //private cacheStats stats;

    private static final Logger LOG = LoggerFactory.getLogger(countryService.class);

    // SAVE (add) or POST REQUESTS
    public countries saveCountry(countries countries) {
        System.out.println("\nYeay.. Country Successfully Added!\n");
        return repo.save(countries);
    }

    public List<countries> saveCountries(List<countries> regs) {
        return repo.saveAll(regs);
    }

    // GET REQUESTS
    // Retrieve all regions
    @Cacheable(cacheNames = "countries", sync = true)
    public List<countries> getAllCountries() {
        LOG.info("Tryna get the country's information:");

        return repo.findAll();
    }

    // Get by ID individually
    @Cacheable(cacheNames = "countries", sync = true, key = "#country_id")
    public Optional<countries> getCountry(String country_id) {
        LOG.info("\n Tryna get the country's information with an ID of: {} \n", country_id);


        return repo.findById(country_id);
    }

    // SCHEDULED CACHING
    /*@Scheduled(fixedRate = 30000)
    public void refreshIntervals() {
        refreshAllCache();
    }*/

    // Get by name individually w/o cache
    public countries getCountryById(String country_id) {
        return repo.findById(country_id).orElse(null);

    }

    /*public List<countries> getCountriesById() {
        return repo.findAllById();
    }*/

    // DELETE REQUESTS
    @CacheEvict
    public String deleteCountry(String country_id) {
        // repo.findById(id);
        repo.deleteById(country_id);
        return "\nCountry no " + country_id + " is unfortunately gone... Sad";
    }

    /*public List<Employee> selfDestruct() {
        return repo.deleteAll();
    }*/

    //POST REQUESTS
    //Update employee info
    public countries updateCountry(countries countries) {
        countries currentInfo = repo.findById(countries.getCountry_id()).orElse(null);
        assert currentInfo != null;
        LOG.info("Updating country info...");
        currentInfo.setCountry_id(countries.getCountry_id());
        currentInfo.setCountry_name(countries.getCountry_name());
        currentInfo.setRegion_id(countries.getRegion_id());

        System.out.println("\nCountry info updated");

        return repo.save(currentInfo);
    }

    // CLEAR CACHES PER INSTANCE

    @CacheEvict(cacheNames="countries", allEntries = true)
    public void refreshAllCache() {
        caffeineConfig.cacheManager().getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(caffeineConfig.cacheManager().getCache(cacheName)).clear());
        LOG.info("===\nAttempting to refresh cache from countries... ");
    }

    /*@CacheEvict(value = "countries", key = "#countries.country_id", allEntries = true)
    public void refreshCacheByID() {
        caffeineConfig.cacheManager().getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(caffeineConfig.cacheManager().getCache(cacheName)).clear());
        LOG.info("===\nAttempting to refresh cache from countries... ");
    }*/

    @CacheEvict(value = "countries", key = "#country_id")
    public void refreshCacheById(String country_id) {

        repo.findById(country_id);

        caffeineConfig.cacheManager().getCacheNames()
                .forEach(key -> Objects.requireNonNull(caffeineConfig.cacheManager().getCache(key)).clear());
        //.(cacheName -> Objects.requireNonNull(caffeineConfig.cacheManager().getCache(cacheName)).clear());

        LOG.info("===\nAttempting to refresh cache from country ID:  "+ country_id);
    }

    /*public String getStats() {
        //caffeineConfig.cacheManager().getCacheNames()
          //      .forEach(cacheName -> Objects.requireNonNull(caffeineConfig.cacheManager().getCache(cacheName)));

        //caffeineConfig.getStats2(caffeineConfig.cacheManager().getCache());
        CacheStats stats = caffeineConfig.cacheBuilder.build().stats();
        LOG.info("getstats(): "+stats);

        return stats.toString();
    }

    public String getStats2(String cacheName) {
        //CacheStats stats = (CaffeineCache) cacheManager().getCache(cacheName);

        CaffeineCache cache = (CaffeineCache) caffeineConfig.cacheManager().getCache(cacheName);
        assert cache != null;
        LOG.info("cache is found: i think its "+cacheName);
        CacheStats stats2 = cache.getNativeCache().stats();
        //CacheStats stats3 = cache.;
        CacheStats stats = caffeineConfig.cacheBuilder.build().stats();
        //cache = stats.;
        LOG.info("them stats are: "+ stats);
        LOG.info("while stats2 are: "+ stats2);
        LOG.info("while cache is: "+ cache+ " or "+cacheName);

        return stats.toString();
    }

    public String getStats4(String cacheName) {
        //CacheStats stats = (CaffeineCache) cacheManager().getCache(cacheName);

        CaffeineCache cache = (CaffeineCache) caffeineConfig.cacheManager().getCache(cacheName);
        //cache = cacheName;
        assert cache != null;
        CacheStats stats = caffeineConfig.cacheBuilder.build().stats();

        LOG.info("cache is found: i think its "+cacheName);
        //cache = stats.;
        LOG.info("them stats are: "+ stats);
        LOG.info("them cache name is: "+ cache);

        return stats.toString();
    }

    public void printStats(Cache cacheName) {
        CacheStats cacheStats = cacheName.stats();

        long evictionCount = cacheStats.evictionCount();
        long hitCount = cacheStats.hitCount();
        long missCount = cacheStats.missCount();

        System.out.println("-------------------------------------");
        System.out.println("\nCache Stats");
        System.out.println("\nEVICTION COUNT: "+ evictionCount);
        System.out.println("HIT COUNT: "+ hitCount);
        System.out.println("MISSED COUNT: "+ missCount);

        System.out.println("-------------------------------------");

    }

    public String printStats4(String cacheName) {
        CaffeineCache cache = (CaffeineCache) caffeineConfig.cacheManager().getCache(cacheName);
        assert cache != null;
        LOG.info("cache is found: i think its "+cacheName);

        CacheStats cacheStats2 = cache.getNativeCache().stats();
        CacheStats cacheStats = caffeineConfig.cacheBuilder.build().stats();

        long evictionCount = cacheStats.evictionCount();
        long hitCount = cacheStats.hitCount();
        long missCount = cacheStats.missCount();

        System.out.println("-------------------------------------");
        System.out.println("\nCache Stats");
        System.out.println("\nEVICTION COUNT: "+ evictionCount);
        System.out.println("HIT COUNT: "+ hitCount);
        System.out.println("MISSED COUNT: "+ missCount);

        System.out.println("-------------------------------------");

        return cacheStats.toString();
    }

    public String getStats6(Cache cacheName) {
        CaffeineCache cache = (CaffeineCache) caffeineConfig.cacheManager().getCache(String.valueOf(cacheName));
        assert cache != null;
        CacheStats stats = cache.getNativeCache().stats();
        return stats.toString();
    }*/


}
