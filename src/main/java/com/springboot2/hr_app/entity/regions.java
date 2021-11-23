// STEP ONE

package com.springboot2.hr_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="regions", schema = "hr")

public class regions {

    @Id
    //@GeneratedValue
    private Integer region_id;

    private String region_name;

}
