package org.gishoauth.spring.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.gishoauth")
@EnableAutoConfiguration
public class GuishoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuishoApplication.class, args);
	}
}
