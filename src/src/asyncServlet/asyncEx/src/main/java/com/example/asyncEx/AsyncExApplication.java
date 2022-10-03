package com.example.asyncEx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AsyncExApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsyncExApplication.class, args);
	}

}
