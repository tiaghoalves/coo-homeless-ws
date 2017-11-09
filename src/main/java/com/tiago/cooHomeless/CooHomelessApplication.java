package com.tiago.cooHomeless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@SpringBootApplication
public class CooHomelessApplication {

	public static void main(String[] args) {
		SpringApplication.run(CooHomelessApplication.class, args);
	}
	
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("Index");
    }
}
