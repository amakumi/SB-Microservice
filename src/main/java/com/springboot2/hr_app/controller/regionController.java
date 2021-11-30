// STEP FOUR

// MAP THE SERVICES TO THE REAL CONTROLLING

package com.springboot2.hr_app.controller;

import com.springboot2.hr_app.entity.regions;
import com.springboot2.hr_app.service.regionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class regionController {

    @Autowired
    private regionService service;

    private static final Logger LOG = LoggerFactory.getLogger(countryController.class);


    @PostMapping("/")
    public regions addRegion(@RequestBody regions reg) {
        return service.saveRegion(reg);
    }

    @PostMapping("/addMultiple")
    public List<regions> addRegions(@RequestBody List<regions> reg) {
        return service.saveRegions(reg);
    }

    // GET REQUESTS
    @GetMapping("/all")
    public List<regions> findAllRegions() {
        return service.getAllRegions();
    }

    @GetMapping("/{id}")
    public regions findById(@PathVariable int id) {
        return service.getRegionById(id);
    }

    /*@GetMapping("/employee/{name}")
    public employees findByName(@PathVariable String name) {
        return (employees) service.getEmployeeByName(name);
    }*/

    // UPDATE EMPLOYEE
    // "PUT" METHOD

    @PutMapping("/{id}")
    public regions updateRegion(@RequestBody regions reg) {
        return service.updateRegion(reg);
        //"Employee " + employee + " is updated!";
    }

    // DELETE EMPLOYEE
    // "DELETE" METHOD

    @DeleteMapping("/{id}")
    public String deleteRegion(@PathVariable int id) {
        return "Region " + id + " is removed. " + service.deleteRegion(id);
    }

    // REFRESH CACHE #1
    @GetMapping("/refresh/")
    public String refreshCache() {
        service.refreshCache();
        return "=======================\n" +
                "Caches are refreshed!\n" +
                "=======================";
    }

    // REFRESH BY ID #1
    @GetMapping("/refresh/{cacheName}/{key}")
    public String refreshByID(
            @PathVariable("cacheName") final String cacheName, @PathVariable("key") final int key) {
        //key = country.getCountry_id();

        LOG.info("Attempting to refresh stats for: " + cacheName + "." + key);
        service.refreshCacheById(cacheName, key);

        return "===========================================================\n" +
                "Cache information from region name of: "+ service.getRegionById(key).getRegion_name() +" is refreshed!\n" +
                "==========================================================";
    }
    /*

    public String destroyAll()

    */


}
