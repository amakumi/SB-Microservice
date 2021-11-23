// STEP THREE
// PUT SERVICES/FUNCTIONS HERE
// Services will talk to the Repository

package com.springboot2.hr_app.service;

import com.springboot2.hr_app.HrAppApplication;
//import com.springboot2.hr_app.config.caffeineConfig;
import com.springboot2.hr_app.entity.departments;
import com.springboot2.hr_app.repository.departmentRepo;
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
@CacheConfig(cacheNames = ("dept"))
public class departmentService {

    @Autowired
    private departmentRepo repo;

    @Autowired
    private caffeineConfig caffeineConfig;

    private static final Logger LOG = LoggerFactory.getLogger(departmentService.class);

    // SAVE (add) or POST REQUESTS
    public departments saveDept(departments dept) {
        System.out.println("\nYeay.. Dept Successfully Added!\n");
        return repo.save(dept);
    }

    public List<departments> saveDepts(List<departments> dept) {
        return repo.saveAll(dept);
    }

    // GET REQUESTS
    // Retrieve all locs
    @Cacheable
    public List<departments> getAllDepts() {
        return repo.findAll();
    }

    // Get by ID individually
    @Cacheable
    public departments getDeptById(int id) {
        LOG.info("\n Tryna get the dept's information with an ID of: {} \n", id);
        return repo.findById(id).orElse(null);
    }
    // Get by name individually
    /* List<employees> getEmployeeByName(String name) {
        return repo.findByName(name);
    }*/

    // DELETE REQUESTS
    @CacheEvict
    public String deleteDept(int id) {
        // repo.findById(id);
        repo.deleteById(id);
        return "\nDept no " + id + " is unfortunately gone... Sad";
    }

    /*public List<Employee> selfDestruct() {
        return repo.deleteAll();
    }*/

    //POST REQUESTS
    //Update info
    public departments updateDept(departments dept) {
        departments currentInfo = repo.findById(dept.getLocation_id()).orElse(null);
        assert currentInfo != null;
        // currentInfo.setLocation_id(loc.getLocation_id());
        currentInfo.setDepartment_id(dept.getDepartment_id());
        currentInfo.setDepartment_name(dept.getDepartment_name());
        currentInfo.setManager_id(dept.getManager_id());
        currentInfo.setLocation_id(dept.getLocation_id());

        System.out.println("\nDept info updated");

        return repo.save(currentInfo);
    }

    // CLEAR CACHES PER INSTANCE

    @CacheEvict(value = "dept", allEntries = true)
    public void refreshCache() {
        caffeineConfig.cacheManager().getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(caffeineConfig.cacheManager().getCache(cacheName)).clear());
        LOG.info("===\nAttempting to refresh cache from departments... ");
    }

    // CLEAR CACHE PER VALUE

    @CacheEvict(value = "dept", key = "#dept_id")
    public void refreshCacheById(int dept_id) {

        repo.findById(dept_id);

        caffeineConfig.cacheManager().getCacheNames()
                .forEach(key -> Objects.requireNonNull(caffeineConfig.cacheManager().getCache(key)).clear());
        //.(cacheName -> Objects.requireNonNull(caffeineConfig.cacheManager().getCache(cacheName)).clear());

        LOG.info("===\nAttempting to refresh cache from Dept ID:  "+ dept_id);
    }

}
