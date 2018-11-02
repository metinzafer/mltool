package com.gnsmind.springBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class SpringBootWorkShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWorkShopApplication.class, args);
	}
}
