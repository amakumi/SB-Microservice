// STEP FOUR

// MAP THE SERVICES TO THE REAL CONTROLLING

package com.springboot2.hr_app.controller;

import com.springboot2.hr_app.entity.employees;
import com.springboot2.hr_app.service.employeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class employeeController {

    @Autowired
    private employeeService service;

    private static final Logger LOG = LoggerFactory.getLogger(countryController.class);


    @PostMapping("/")
    public employees addEmployee(@RequestBody employees employee) {
        return service.saveEmployee(employee);
    }

    @PostMapping("/addMultiple")
    public List<employees> addMultipleEmployees(@RequestBody List<employees> employees) {
        return service.saveEmployees(employees);
    }

    // GET REQUESTS
    @GetMapping("/")
    public List<employees> findAllEmployees() {
        return service.getAllEmployees();
    }

    @GetMapping("/{id}")
    public employees findById(@PathVariable int id) {
        return service.getEmployeeById(id);
    }

    /*@GetMapping("/employee/{name}")
    public employees findByName(@PathVariable String name) {
        return (employees) service.getEmployeeByName(name);
    }*/

    // UPDATE EMPLOYEE
    // "PUT" METHOD

    @PutMapping("/{id}")
    public employees updateEmployee(@RequestBody employees employee) {
        return service.updateEmployee(employee);
        //"Employee " + employee + " is updated!";
    }

    // DELETE EMPLOYEE
    // "DELETE" METHOD

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id) {
        return "Employee " + id + " is fired. " + service.deleteEmployee(id);
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
        service.refreshCacheById(key);

        return "==================================================================\n" +
                "Cache information from Employee ID of: "+ service.getEmployeeById(key).getEmployee_id() +
                "\nwith employee name of: " + service.getEmployeeById(key).getFirst_name() + service.getEmployeeById(key).getLast_name()+
                "\nis refreshed!" +
                "==================================================================";
    }

    /*
    public String destroyAll()

    */


}
