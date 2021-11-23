// STEP TWO

package com.springboot2.hr_app.repository;

import com.springboot2.hr_app.entity.departments;
import org.springframework.data.jpa.repository.JpaRepository;

// import java.util.List;

public interface departmentRepo extends JpaRepository<departments, Integer> {

    // List<employees> findByFirst_name(String name);


}
