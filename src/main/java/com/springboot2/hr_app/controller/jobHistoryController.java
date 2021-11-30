// STEP FOUR

// MAP THE SERVICES TO THE REAL CONTROLLING

package com.springboot2.hr_app.controller;

import com.springboot2.hr_app.entity.jobHistory;
import com.springboot2.hr_app.service.jobHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/jobhistory")
public class jobHistoryController {

    @Autowired
    private jobHistoryService service;

    private static final Logger LOG = LoggerFactory.getLogger(countryController.class);


    @PostMapping("/")
    public jobHistory addJobHistory(@RequestBody jobHistory job) {
        return service.saveJobHistory(job);
    }

    @PostMapping("/addMutiple")
    public List<jobHistory> addMultipleJobHistories(@RequestBody List<jobHistory> jobs) {
        return service.saveJobHistories(jobs);
    }

    // GET REQUESTS
    @GetMapping("/all")
    public List<jobHistory> findAllJobHistories() {
        return service.getAllJobHistories();
    }

    @GetMapping("/{id}")
    public jobHistory findById(@PathVariable int id) {
        return service.getJobHistoryById(id);
    }

    /*@GetMapping("/employee/{name}")
    public employees findByName(@PathVariable String name) {
        return (employees) service.getEmployeeByName(name);
    }*/

    // UPDATE
    // "PUT" METHOD

    @PutMapping("/{id}")
    public jobHistory updateJobHistory(@RequestBody jobHistory job) {
        return service.updateJobHistory(job);
        //"Employee " + employee + " is updated!";
    }

    // DELETE
    // "DELETE" METHOD

    @DeleteMapping("/{id}")
    public String deleteJobHistory(@PathVariable int id) {
        return "Job History" + id + " is removed. " + service.deleteJobHistory(id);
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
                "Cache information from employee ID:"+ service.getJobHistoryById(key).getEmployee_id() +" is refreshed!\n" +
                "with job ID of: "+ service.getJobHistoryById(key).getJob_id()+
                "==========================================================";
    }


}
