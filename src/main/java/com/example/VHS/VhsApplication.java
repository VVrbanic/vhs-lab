package com.example.VHS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VhsApplication {
	private static final Logger logger = LoggerFactory.getLogger(VhsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(VhsApplication.class, args);
		logger.info("VhsApplication started successfully.");
	}

}
