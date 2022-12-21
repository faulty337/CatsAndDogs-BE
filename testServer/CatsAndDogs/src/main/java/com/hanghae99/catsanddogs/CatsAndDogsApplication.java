package com.hanghae99.catsanddogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CatsAndDogsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatsAndDogsApplication.class, args);
	}

}
