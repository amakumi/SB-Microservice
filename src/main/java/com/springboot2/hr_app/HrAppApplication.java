package com.springboot2.hr_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

@SpringBootApplication
//@EnableJpaRepositories("com.springboot2.hr_app.repository")
//@EnableEurekaClient
//@Configuration
@EnableCaching
public class HrAppApplication {

    @LoadBalanced
    public static void main(String[] args) {
        //Logger.INFO(refreshCache.stats());
        SpringApplication.run(HrAppApplication.class, args);
    }


}
