// STEP THREE
// PUT SERVICES/FUNCTIONS HERE
// Services will talk to the Repository

package com.springboot2.hr_app.service;

import com.springboot2.hr_app.config.caffeineConfig;
import com.springboot2.hr_app.HrAppApplication;
import com.springboot2.hr_app.entity.employees;
import com.springboot2.hr_app.repository.employeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@CacheConfig(cacheNames = ("employee"))
public class employeeService {
    @Autowired
    private employeeRepo repo;

    @Autowired
    private caffeineConfig caffeineConfig;

    private static final Logger LOG = LoggerFactory.getLogger(employeeService.class);

    // SAVE (add) or POST REQUESTS
    public employees saveEmployee(employees employee) {
        System.out.println("\nYeay.. Employee Successfully Added!\n");
        return repo.save(employee);
    }

    public List<employees> saveEmployees(List<employees> employees) {
        return repo.saveAll(employees);
    }

    // GET REQUESTS
    // Retrieve all employees
    @Cacheable
    public List<employees> getAllEmployees() {
        LOG.info("\n Tryna get the all employee's information..!\n");
        return repo.findAll();
    }

    // Get by ID individually
    @Cacheable
    public employees getEmployeeById(Integer id) {
        LOG.info("\n Tryna get the employee's information with an ID of: {} \n", id);
        return repo.findById(id).orElse(null);
    }
    // Get by name individually
    /* List<employees> getEmployeeByName(String name) {
        return repo.findByName(name);
    }*/

    // DELETE REQUESTS
    @CacheEvict
    public String deleteEmployee(Integer id) {
        // repo.findById(id);
        repo.deleteById(id);
        return "\nEmployee no " + id + " is unfortunately fired... Sad";
    }

    /*public List<Employee> selfDestruct() {
        return repo.deleteAll();
    }*/

    //POST REQUESTS
    //Update employee info
    public employees updateEmployee(employees employee) {
        employees existingEmployee = repo.findById(employee.getEmployee_id()).orElse(null);
        assert existingEmployee != null;
        existingEmployee.setEmployee_id(employee.getEmployee_id());
        existingEmployee.setFirst_name(employee.getFirst_name());
        existingEmployee.setLast_name(employee.getLast_name());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setPhone_number(employee.getPhone_number());
        existingEmployee.setHire_date(employee.getHire_date());
        existingEmployee.setJob_id(employee.getJob_id());
        existingEmployee.setSalary(employee.getSalary());
        existingEmployee.setCommission_pct(employee.getCommission_pct());
        existingEmployee.setManager_id(employee.getManager_id());
        existingEmployee.setDepartment_id(employee.getDepartment_id());

        System.out.println("\nEmployee info updated");

        return repo.save(existingEmployee);
    }

    // CLEAR CACHES PER INSTANCE

    @CacheEvict(value = "employee", allEntries = true)
    public void refreshCache() {
        caffeineConfig.cacheManager().getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(caffeineConfig.cacheManager().getCache(cacheName)).clear());
        LOG.info("===\nAttempting to refresh cache from employees... ");
    }

    // CLEAR CACHE PER VALUE

    @CacheEvict(value = "employee", key = "#employee_id")
    public void refreshCacheById(String cacheName, Integer employee_id) {

        repo.findById(employee_id);
        caffeineConfig.cacheManager().getCache(cacheName);

        (Objects.requireNonNull(caffeineConfig.cacheManager().getCache(cacheName))).evict(employee_id);

        LOG.info("===\nAttempting to refresh cache from Employee ID:  "+ employee_id);
    }


}
