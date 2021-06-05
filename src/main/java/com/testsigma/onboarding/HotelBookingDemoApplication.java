package com.testsigma.onboarding;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelBookingDemoApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(HotelBookingDemoApplication.class);
		app.run(args);
		System.out.println("Running Spring App...");
	}
}