// STEP THREE
// PUT SERVICES/FUNCTIONS HERE
// Services will talk to the Repository

package com.springboot2.hr_app.service;

import com.springboot2.hr_app.HrAppApplication;
import com.springboot2.hr_app.entity.jobHistory;
import com.springboot2.hr_app.repository.jobHistoryRepo;
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
@CacheConfig(cacheNames = ("jobhistory"))
public class jobHistoryService {
    @Autowired
    private jobHistoryRepo repo;

    @Autowired
    private caffeineConfig caffeineConfig;

    private static final Logger LOG = LoggerFactory.getLogger(jobHistoryService.class);

    // SAVE (add) or POST REQUESTS
    public jobHistory saveJobHistory(jobHistory job) {
        System.out.println("\nYeay.. Job History Successfully Added!\n");
        return repo.save(job);
    }

    public List<jobHistory> saveJobHistories(List<jobHistory> jobs) {
        return repo.saveAll(jobs);
    }

    // GET REQUESTS
    // Retrieve all
    @Cacheable
    public List<jobHistory> getAllJobHistories() {
        LOG.info("\n Tryna get all job histories...\n");
        return repo.findAll();
    }

    // Get by ID individually
    @Cacheable
    public jobHistory getJobHistoryById(int id) {
        LOG.info("\n Tryna get the job history's information with an ID of: {} \n", id);
        return repo.findById(id).orElse(null);
    }
    // Get by name individually
    /* List<employees> getEmployeeByName(String name) {
        return repo.findByName(name);
    }*/

    // DELETE REQUESTS
    @CacheEvict
    public String deleteJobHistory(int id) {
        // repo.findById(id);
        repo.deleteById(id);
        return "\nJob History no " + id + " is unfortunately gone... Sad";
    }

    //POST REQUESTS
    //Update info
    public jobHistory updateJobHistory(jobHistory job) {
        jobHistory currentInfo = repo.findById(job.getEmployee_id()).orElse(null);
        assert currentInfo != null;

        currentInfo.setJob_id(job.getJob_id());
        currentInfo.setEmployee_id(job.getEmployee_id());
        currentInfo.setStart_date(job.getStart_date());
        currentInfo.setEnd_date(job.getEnd_date());
        currentInfo.setDepartment_id(job.getDepartment_id());

        System.out.println("\nJob History info updated");

        return repo.save(currentInfo);
    }

    // CLEAR CACHES PER INSTANCE

    @CacheEvict(value = "jobhistory", allEntries = true)
    public void refreshCache() {
        caffeineConfig.cacheManager().getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(caffeineConfig.cacheManager().getCache(cacheName)).clear());
        LOG.info("===\nAttempting to refresh cache from job histories... ");
    }

    // CLEAR CACHE PER VALUE

    @CacheEvict(value = "jobhistory", key = "#job_id")
    public void refreshCacheById(String cacheName, int job_id) {

        repo.findById(job_id);
        caffeineConfig.cacheManager().getCache(cacheName);

        (Objects.requireNonNull(caffeineConfig.cacheManager().getCache(cacheName))).evict(job_id);

        LOG.info("===\nAttempting to refresh cache from Job history of ID:  "+ job_id);
    }


}
