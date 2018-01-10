package com.beidouchain.explorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StartExplorer {

	public static void main(String[] args) {
		SpringApplication.run(StartExplorer.class, args);
	}
}