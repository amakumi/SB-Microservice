// STEP FOUR

// MAP THE SERVICES TO THE REAL CONTROLLING

package com.springboot2.hr_app.controller;

import com.springboot2.hr_app.entity.departments;
import com.springboot2.hr_app.service.departmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/dept")
public class departmentController {

    @Autowired
    private departmentService service;

    private static final Logger LOG = LoggerFactory.getLogger(countryController.class);

    @PostMapping("/")
    public departments addDepartment(@RequestBody departments dept) {
        return service.saveDept(dept);
    }

    @PostMapping("/addMultiple")
    public List<departments> addMultipleDepartments(@RequestBody List<departments> depts) {
        return service.saveDepts(depts);
    }

    // GET REQUESTS
    @GetMapping("/all")
    public List<departments> findAllDepartments() {
        return service.getAllDepts();
    }

    @GetMapping("/{id}")
    public departments findById(@PathVariable int id) {
        return service.getDeptById(id);
    }

    // UPDATE
    // "PUT" METHOD

    @PutMapping("/{id}")
    public departments updateDepartment(@RequestBody departments dept) {
        return service.updateDept(dept);
        //"Employee " + employee + " is updated!";
    }

    // DELETE
    // "DELETE" METHOD

    @DeleteMapping("/{id}")
    public String deleteDepartment(@PathVariable int id) {
        return "Dept" + id + " is removed. " + service.deleteDept(id);
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

        return "=======================================\n" +
                "Cache information from "+ service.getDeptById(key).getDepartment_name() +" is refreshed!\n" +
                "======================================";
    }

}
