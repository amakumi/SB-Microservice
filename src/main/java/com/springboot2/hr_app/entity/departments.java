// STEP ONE

package com.springboot2.hr_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//import com.springboot2.hr_app.entity.locations;
//import org.hibernate.annotations.ManyToAny;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class departments {

    /*@ManyToOne
    private locations loc;
    @OneToOne
    private employees manager;*/

    @Id
    //@GeneratedValue
    private Integer department_id;

    private String department_name;

    @JoinColumn(name = "manager_id", nullable = false)
    private Integer manager_id;

    @JoinColumn(name = "location_id", nullable = false)
    private Integer location_id;


}
