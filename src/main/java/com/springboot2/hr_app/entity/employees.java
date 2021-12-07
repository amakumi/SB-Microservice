// STEP ONE

package com.springboot2.hr_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class employees {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer employee_id;

    private String first_name;

    private String last_name;

    private String email;

    private String phone_number;

    private String hire_date;

    //@JoinColumn(name = "job_id", nullable = false)
    private String job_id;

    private Long salary;

    private Long commission_pct;

    //@JoinColumn(name = "department_id")
    private Integer department_id;

    //@JoinColumn(name = "manager_id")
    private Integer manager_id;

}
