// STEP TWO

package com.springboot2.hr_app.repository;

import com.springboot2.hr_app.entity.jobs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface jobRepo extends JpaRepository<jobs, Integer> {

    // List<employees> findByFirst_name(String name);


}
