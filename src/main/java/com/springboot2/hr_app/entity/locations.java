// STEP ONE

package com.springboot2.hr_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class locations {
    /*@OneToOne
    private countries countries;

    @OneToOne
    private locations loc;
    */

    @Id
    @GeneratedValue
    private Integer location_id;
    @JoinColumn(name = "location_id", nullable = false)


    private String street_address;

    private String postal_code;

    private String city;

    private String state_province;

    @JoinColumn(name = "country_id", nullable = false)
    private String country_id;

    //private countries countries;
}
