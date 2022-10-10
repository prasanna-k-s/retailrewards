package com.task.retail.rewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class RetailRewardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetailRewardsApplication.class, args);
	}

}
