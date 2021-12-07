package com.springboot2.hr_app.DTO;

import com.springboot2.hr_app.entity.employees;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemplate {

    public employees employees;

}
