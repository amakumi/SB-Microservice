// STEP TWO

package com.springboot2.hr_app.repository;

import com.springboot2.hr_app.entity.jobHistory;
import org.springframework.data.jpa.repository.JpaRepository;

// import java.util.List;

public interface jobHistoryRepo extends JpaRepository<jobHistory, Integer> {

    // List<employees> findByFirst_name(String name);


}
