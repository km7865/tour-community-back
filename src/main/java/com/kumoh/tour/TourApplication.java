package com.kumoh.tour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TourApplication extends SpringBootServletInitializer {

	// 생성된 war 구동시 필요
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(TourApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(TourApplication.class, args);
	}

}