// STEP THREE
// PUT SERVICES/FUNCTIONS HERE
// Services will talk to the Repository

package com.springboot2.hr_app.service;

import com.springboot2.hr_app.HrAppApplication;
import com.springboot2.hr_app.entity.regions;

import com.springboot2.hr_app.repository.regionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.springboot2.hr_app.config.caffeineConfig;
import java.util.List;
import java.util.Objects;

@Service
@CacheConfig(cacheNames = ("region"))
public class regionService {
    @Autowired
    private regionRepo repo;

    @Autowired
    private caffeineConfig caffeineConfig;

    private static final Logger LOG = LoggerFactory.getLogger(regionService.class);

    // SAVE (add) or POST REQUESTS
    public regions saveRegion(regions reg) {
        System.out.println("\nYeay.. Region Successfully Added!\n");
        return repo.save(reg);
    }

    public List<regions> saveRegions(List<regions> regs) {
        return repo.saveAll(regs);
    }

    // GET REQUESTS
    // Retrieve all regions
    @Cacheable
    public List<regions> getAllRegions() {
        LOG.info("\n Tryna get the all region's info\n");
        return repo.findAll();
    }

    // Get by ID individually
    @Cacheable
    public regions getRegionById(int id) {
        LOG.info("\n Tryna get the region's information with an ID of: {} \n", id);
        return repo.findById(id).orElse(null);
    }
    // Get by name individually
    /* List<employees> getEmployeeByName(String name) {
        return repo.findByName(name);
    }*/

    // DELETE REQUESTS
    @CacheEvict
    public String deleteRegion(int id) {
        // repo.findById(id);
        repo.deleteById(id);
        return "\nRegion no " + id + " is unfortunately gone... Sad";
    }

    /*public List<Employee> selfDestruct() {
        return repo.deleteAll();
    }*/

    //POST REQUESTS
    //Update employee info
    public regions updateRegion(regions reg) {
        regions currentReg = repo.findById(reg.getRegion_id()).orElse(null);
        assert currentReg != null;
        currentReg.setRegion_id(reg.getRegion_id());
        currentReg.setRegion_name(reg.getRegion_name());

        System.out.println("\nRegion info updated");

        return repo.save(currentReg);
    }

    // CLEAR CACHES PER INSTANCE

    @CacheEvict(value = "region", allEntries = true)
    public void refreshCache() {
        LOG.info("===\nAttempting to refresh cache from region... ");
        caffeineConfig.cacheManager().getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(caffeineConfig.cacheManager().getCache(cacheName)).clear());

    }

    // CLEAR CACHE PER VALUE

    @CacheEvict(value = "region", key = "#region_id")
    public void refreshCacheById(int region_id) {

        repo.findById(region_id);

        caffeineConfig.cacheManager().getCacheNames()
                .forEach(key -> Objects.requireNonNull(caffeineConfig.cacheManager().getCache(key)).clear());
        //.(cacheName -> Objects.requireNonNull(caffeineConfig.cacheManager().getCache(cacheName)).clear());

        LOG.info("===\nAttempting to refresh cache from region ID:  "+ region_id);
    }


}
