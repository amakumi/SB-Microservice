// STEP TWO

package com.springboot2.hr_app.repository;

import com.springboot2.hr_app.entity.locations;
import org.springframework.data.jpa.repository.JpaRepository;

// import java.util.List;

public interface locationRepo extends JpaRepository<locations, Integer> {

    // List<employees> findByFirst_name(String name);


}
