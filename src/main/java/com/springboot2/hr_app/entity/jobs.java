// STEP ONE

package com.springboot2.hr_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class jobs {

    @Id
    //@GeneratedValue
    private Integer job_id;

    private String job_title;

    private Long min_salary;

    private Long max_salary;

}
