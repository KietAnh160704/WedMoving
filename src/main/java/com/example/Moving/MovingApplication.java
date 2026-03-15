package com.example.Moving;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MovingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovingApplication.class, args);
	}

}
