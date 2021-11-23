// STEP FOUR

// MAP THE SERVICES TO THE REAL CONTROLLING

package com.springboot2.hr_app.controller;
import com.springboot2.hr_app.HrAppApplication;
import com.springboot2.hr_app.config.caffeineConfig;
import com.springboot2.hr_app.entity.countries;
import com.springboot2.hr_app.service.countryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.cache.CacheManager;

import org.springframework.web.bind.annotation.*;

//import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/countries")
public class countryController {

    @Autowired
    public countryService service;

    public countries country;

    public HrAppApplication caffeineConfig;

    private static final Logger LOG = LoggerFactory.getLogger(countryController.class);

    @PostMapping("/")
    public countries addCountry(@RequestBody countries countries) {
        return service.saveCountry(countries);
    }

    @PostMapping("/addMultiple")
    public List<countries> addMultipleCountries(@RequestBody List<countries> reg) {
        return service.saveCountries(reg);
    }

    // GET REQUESTS
    @GetMapping("/")
    public List<countries> findAllCountries() {
        return service.getAllCountries();
    }

    @GetMapping("/{id}")
    public countries findById(@PathVariable String id) {
        return service.getCountry(id).orElse(null);
    }
    /*
    @GetMapping("/country/region/{id}")
    public List<countries> findByRegion_id(@PathVariable int id) {
        return service.getCountryByRegion_id(id);
    }*/

    // UPDATE
    // "PUT" METHOD

    @PutMapping("/{id}")
    public countries updateCountry(@RequestBody countries countries) {
        return service.updateCountry(countries);
        //"Employee " + employee + " is updated!";
    }

    // DELETE
    // "DELETE" METHOD

    @DeleteMapping("/{id}")
    public String deleteCountry(@PathVariable String id) {
        return "Country" + id + " is removed. " + service.deleteCountry(id);
    }

    // REFRESH CACHE #1
    @GetMapping("/refresh/")
    public String refreshAllCache() {
        service.refreshAllCache();
        return "=======================\n" +
                "Caches are refreshed!\n" +
                "=======================";
    }

    // REFRESH BY ID #1
    @GetMapping("/refresh/{cacheName}/{key}")
    public String refreshByID(
            @PathVariable("cacheName") final String cacheName, @PathVariable("key") final String key) {
        //key = country.getCountry_id();

        LOG.info("Attempting to refresh stats for: " + cacheName + "." + key);
        service.refreshCacheById(key);

        return "==========================================================\n" +
                "Cache information from "+ service.getCountryById(key).getCountry_name() +" is refreshed!\n" +
                "==========================================================";
    }

    /*@GetMapping("/stats/")
    public String getStats() {
        LOG.info("Attempting to call function to get stats from countries..");
        return service.getStats();
    }

    @GetMapping("/stats2/")
    public String getStats2() {
        LOG.info("Trigerring getstats2() - Attempting to call function to get stats from countries..");

        return service.getStats2("countries");
    }

    @GetMapping("/stats4/")
    public String getStats4() {
        LOG.info("Attempting to call function to get stats4() - from countries..");
        return service.getStats4("countries");
    }

    @GetMapping("/stats5/")
    public String getStats5() {
        LOG.info("Attempting to call function to get stats5() - from countries..");
        return service.printStats4("countries");
    }

    @GetMapping("/stats6/")
    public String getStats6() {
        LOG.info("Attempting to call function to get stats6() - from countries..");
        return service.printStats4("countries");
    }*/

}
