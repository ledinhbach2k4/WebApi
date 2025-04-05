package com.example.techtopstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.techtopstore")
public class TechTopStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechTopStoreApplication.class, args);
	}

}

