// STEP THREE
// PUT SERVICES/FUNCTIONS HERE
// Services will talk to the Repository

package com.springboot2.hr_app.service;

import com.springboot2.hr_app.entity.locations;
import com.springboot2.hr_app.repository.locationRepo;

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
@CacheConfig(cacheNames = ("location"))
public class locationService {
    @Autowired
    private locationRepo repo;

    @Autowired
    private caffeineConfig caffeineConfig;

    private static final Logger LOG = LoggerFactory.getLogger(locationService.class);

    // SAVE (add) or POST REQUESTS
    public locations saveLoc(locations reg) {
        System.out.println("\nYeay.. Location Successfully Added!\n");
        return repo.save(reg);
    }

    public List<locations> saveLocs(List<locations> locs) {
        return repo.saveAll(locs);
    }

    // GET REQUESTS
    // Retrieve all locs
    @Cacheable
    public List<locations> getAllLocs() {
        LOG.info("\n Tryna get the location's information \n");
        return repo.findAll();
    }

    // Get by ID individually
    @Cacheable
    public locations getLocById(int id) {
        LOG.info("\n Tryna access the location's information with an ID of: {} \n", id);
        return repo.findById(id).orElse(null);
    }

    // non-cache GET
    public locations getLocById2(int id) {
        LOG.info("\n Tryna get the location's information with an ID of: {} \n", id);
        return repo.findById(id).orElse(null);
    }
    // Get by name individually
    /* List<employees> getEmployeeByName(String name) {
        return repo.findByName(name);
    }*/

    // DELETE REQUESTS
    @CacheEvict
    public String deleteLoc(int id) {
        // repo.findById(id);
        repo.deleteById(id);
        return "\nLoc no " + id + " is unfortunately gone... Sad";
    }

    /*public List<Employee> selfDestruct() {
        return repo.deleteAll();
    }*/

    //POST REQUESTS
    //Update info
    public locations updateLoc(locations loc) {
        locations currentInfo = repo.findById(loc.getLocation_id()).orElse(null);
        assert currentInfo != null;
        // currentInfo.setLocation_id(loc.getLocation_id());
        if(currentInfo.getCountry_id() != null)
        {
            currentInfo.setStreet_address(loc.getStreet_address());
            currentInfo.setPostal_code(loc.getPostal_code());
            currentInfo.setCity(loc.getCity());
            currentInfo.setState_province(loc.getState_province());
            currentInfo.setCountry_id(loc.getCountry_id());
        }
        else {
            System.out.println("\nNo country found. Make sure the country is listed!");
            return null;
        }

        System.out.println("\nLocation info updated");

        return repo.save(currentInfo);
    }

    // CLEAR CACHES PER INSTANCE

    @CacheEvict(value = "location", allEntries = true)
    public void refreshCache() {
        caffeineConfig.cacheManager().getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(caffeineConfig.cacheManager().getCache(cacheName)).clear());
        LOG.info("===\nAttempting to refresh cache from the list of locations table... ");
    }

    // CLEAR CACHE PER VALUE

    @CacheEvict(value = "location", key = "#location_id")
    public void refreshCacheById(int location_id) {

        repo.findById(location_id);

        caffeineConfig.cacheManager().getCacheNames()
                .forEach(key -> Objects.requireNonNull(caffeineConfig.cacheManager().getCache(key)).clear());
        //.(cacheName -> Objects.requireNonNull(caffeineConfig.cacheManager().getCache(cacheName)).clear());

        LOG.info("===\nAttempting to refresh cache from location ID of:  "+ location_id);
    }

}
