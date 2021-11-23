// STEP FOUR

// MAP THE SERVICES TO THE REAL CONTROLLING

package com.springboot2.hr_app.controller;

import com.springboot2.hr_app.entity.locations;
import com.springboot2.hr_app.service.locationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class locationController {

    @Autowired
    private locationService service;

    private static final Logger LOG = LoggerFactory.getLogger(countryController.class);


    @PostMapping("/")
    public locations addLoc(@RequestBody locations loc) {
        return service.saveLoc(loc);
    }

    @PostMapping("/addMultiple")
    public List<locations> add(@RequestBody List<locations> locs) {
        return service.saveLocs(locs);
    }

    // GET REQUESTS
    @GetMapping("/all")
    public List<locations> findAllLocations() {
        return service.getAllLocs();
    }

    @GetMapping("/{id}")
    public locations findById(@PathVariable int id) {
        return service.getLocById(id);
    }

    /*@GetMapping("/employee/{name}")
    public employees findByName(@PathVariable String name) {
        return (employees) service.getEmployeeByName(name);
    }*/

    // UPDATE
    // "PUT" METHOD

    @PutMapping("/{id}")
    public locations updateLoc(@RequestBody locations loc) {
        return service.updateLoc(loc);
        //"Employee " + employee + " is updated!";
    }

    // DELETE
    // "DELETE" METHOD

    @DeleteMapping("/{id}")
    public String deleteLoc(@PathVariable int id) {
        return "Location" + id + " is removed. " + service.deleteLoc(id);
    }

    // REFRESH CACHE #1
    @GetMapping("/refresh/")
    public String refreshCache() {
        service.refreshCache();;
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
        service.refreshCacheById(key);

        return "==========================================================================================\n" +
                "Cache information from Location ID of: "+ service.getLocById(key).getLocation_id() +
                "\nwith a full address of: "+ service.getLocById(key).getStreet_address() +
                "\nis refreshed!\n"+
                "==========================================================================================";
    }
}
