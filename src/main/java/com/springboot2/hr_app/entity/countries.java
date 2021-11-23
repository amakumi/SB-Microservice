// STEP ONE

package com.springboot2.hr_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
//import com.springboot2.hr_app.entity.regions;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class countries {

    //public static String country_id;
    //    public static Object country_id;

    /*@ManyToOne
    private regions reg;
    @JoinColumn(name = "region_id", insertable = false, updatable = false, nullable = false)
    */

    //@GeneratedValue
    @Id
    public String country_id;

    //@OneToOne
    private String country_name;

    private Integer region_id;


}
