// STEP THREE
// PUT SERVICES/FUNCTIONS HERE
// Services will talk to the Repository

package com.springboot2.hr_app.service;

import com.springboot2.hr_app.HrAppApplication;
import com.springboot2.hr_app.entity.jobs;
import com.springboot2.hr_app.repository.jobRepo;
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
@CacheConfig(cacheNames = ("job"))
public class jobService {
    @Autowired
    private jobRepo repo;

    @Autowired
    private caffeineConfig caffeineConfig;

    private static final Logger LOG = LoggerFactory.getLogger(jobService.class);

    // SAVE (add) or POST REQUESTS
    public jobs saveJob(jobs job) {
        System.out.println("\nYeay.. Job Successfully Added!\n");
        return repo.save(job);
    }

    public List<jobs> saveJobs(List<jobs> job) {
        return repo.saveAll(job);
    }

    // GET REQUESTS
    // Retrieve all
    @Cacheable
    public List<jobs> getAllJobs() {
        LOG.info("\n Tryna get the all job's information\n");
        return repo.findAll();
    }

    // Get by ID individually
    @Cacheable
    public jobs getJobById(int id) {
        LOG.info("\n Tryna get the job's information with an ID of: {} \n", id);
        return repo.findById(id).orElse(null);
    }
    // Get by name individually
    /* List<employees> getEmployeeByName(String name) {
        return repo.findByName(name);
    }*/

    // DELETE REQUESTS
    @CacheEvict
    public String deleteJob(int id) {
        // repo.findById(id);
        repo.deleteById(id);
        return "\nJob no " + id + " is unfortunately gone... Sad";
    }

    //POST REQUESTS
    //Update info
    public jobs updateJob(jobs job) {
        jobs currentInfo = repo.findById(job.getJob_id()).orElse(null);
        assert currentInfo != null;
        // currentInfo.setLocation_id(loc.getLocation_id());
        currentInfo.setJob_id(job.getJob_id());
        currentInfo.setJob_title(job.getJob_title());
        currentInfo.setMax_salary(job.getMax_salary());
        currentInfo.setMin_salary(job.getMin_salary());

        System.out.println("\nJob info updated");

        return repo.save(currentInfo);
    }

    // CLEAR CACHES PER INSTANCE

    @CacheEvict(value = "job", allEntries = true)
    public void refreshCache() {
        caffeineConfig.cacheManager().getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(caffeineConfig.cacheManager().getCache(cacheName)).clear());
        LOG.info("===\nAttempting to refresh cache from jobs... ");
    }

    // CLEAR CACHE PER VALUE

    @CacheEvict(value = "jobs", key = "#job_id")
    public void refreshCacheById(String cacheName, int job_id) {

        repo.findById(job_id);
        caffeineConfig.cacheManager().getCache(cacheName);

        (Objects.requireNonNull(caffeineConfig.cacheManager().getCache(cacheName))).evict(job_id);

        LOG.info("===\nAttempting to refresh cache from Job ID:  "+ job_id);
    }



}
