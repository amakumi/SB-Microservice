// STEP TWO

package com.springboot2.hr_app.repository;

import com.springboot2.hr_app.entity.employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface employeeRepo extends JpaRepository<employees, Integer> {

    //List<employees> findByFirst_name(String name);
    employees findByemployee_id(Integer EmployeeId);

    employees findByfirst_name(String firstname);
}
