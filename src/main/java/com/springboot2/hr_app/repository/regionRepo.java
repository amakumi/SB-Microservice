// STEP TWO

package com.springboot2.hr_app.repository;

import com.springboot2.hr_app.entity.regions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import java.util.List;
@Repository
public interface regionRepo extends JpaRepository<regions, Integer> {

    // List<employees> findByFirst_name(String name);


}