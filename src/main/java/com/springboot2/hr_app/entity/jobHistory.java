// STEP ONE

package com.springboot2.hr_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// import com.springboot2.hr_app.entity.employees;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="job_history", schema = "hr")

public class jobHistory {

    /*@ManyToOne
    private departments dept;
    @OneToOne
    private employees emp;
    @OneToOne
    private jobs jobs;*/

    @Id
    //@GeneratedValue
    @JoinColumn(name = "employee_id", insertable = false, updatable = false, nullable = false)
    private Integer employee_id;

    private String start_date;

    private String end_date;

    @JoinColumn(name = "job_id", nullable = false)
    private String job_id;

    @JoinColumn(name = "department_id", nullable = false)
    private Integer department_id;


    //private int e;
}
