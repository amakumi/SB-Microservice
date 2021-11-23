// STEP TWO

package com.springboot2.hr_app.repository;

import com.springboot2.hr_app.entity.countries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface countryRepo extends JpaRepository<countries, String> {

    // List<employees> findByFirst_name(String name);
    // void deletecountriesBy(String country_id);

    //void deleteByCountry_id(String country_id);

    /*countries findByCountry_id(String country_id);

    List<countries> findByRegion_id(int region_id);*/

    //countries getCountries(final String country_id);

//    Long getByCountry_id(String id);

}
