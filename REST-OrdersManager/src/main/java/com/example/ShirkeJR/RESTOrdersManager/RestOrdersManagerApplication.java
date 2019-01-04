package com.example.ShirkeJR.RESTOrdersManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class RestOrdersManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestOrdersManagerApplication.class, args);
	}

}

