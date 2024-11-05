package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.config.CloudinaryConfig;
@EnableScheduling
@SpringBootApplication
public class RentalProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentalProjectApplication.class, args);
	}


    @Bean
     CloudinaryConfig cloudinaryConfig() {
        return new CloudinaryConfig();
    }

}
