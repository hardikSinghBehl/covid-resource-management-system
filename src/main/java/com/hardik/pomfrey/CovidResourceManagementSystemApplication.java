package com.hardik.pomfrey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CovidResourceManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CovidResourceManagementSystemApplication.class, args);
	}

}
