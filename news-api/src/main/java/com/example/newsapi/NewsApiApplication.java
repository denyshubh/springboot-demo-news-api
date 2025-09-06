package com.example.newsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class NewsApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(NewsApiApplication.class, args);
	}
}