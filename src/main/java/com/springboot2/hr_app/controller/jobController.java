// STEP FOUR

// MAP THE SERVICES TO THE REAL CONTROLLING

package com.springboot2.hr_app.controller;

import com.springboot2.hr_app.entity.jobs;
import com.springboot2.hr_app.service.jobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class jobController {

    @Autowired
    private jobService service;

    private static final Logger LOG = LoggerFactory.getLogger(countryController.class);


    @PostMapping("/")
    public jobs addJob(@RequestBody jobs job) {
        return service.saveJob(job);
    }

    @PostMapping("/addMultiple")
    public List<jobs> addMultipleJobs(@RequestBody List<jobs> jobs) {
        return service.saveJobs(jobs);
    }

    // GET REQUESTS
    @GetMapping("/all")
    public List<jobs> findAllJobs() {
        return service.getAllJobs();
    }

    @GetMapping("/{id}")
    public jobs findById(@PathVariable int id) {
        return service.getJobById(id);
    }

    /*@GetMapping("/employee/{name}")
    public employees findByName(@PathVariable String name) {
        return (employees) service.getEmployeeByName(name);
    }*/

    // UPDATE
    // "PUT" METHOD

    @PutMapping("/{id}")
    public jobs updateJob(@RequestBody jobs job) {
        return service.updateJob(job);
        //"Employee " + employee + " is updated!";
    }

    // DELETE
    // "DELETE" METHOD

    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable int id) {
        return "Job" + id + " is removed. " + service.deleteJob(id);
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

        return "===================================================================\n" +
                "Cache information from "+ service.getJobById(key).getJob_title() +" is refreshed!\n" +
                "with job title of: "+ service.getJobById(key).getJob_title()+
                "==================================================================";
    }

}
