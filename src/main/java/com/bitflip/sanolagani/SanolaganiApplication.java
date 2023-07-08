package com.bitflip.sanolagani;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages = "com.bitflip.sanolagani")
@SpringBootApplication
public class SanolaganiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SanolaganiApplication.class, args);
	
	}

 
}
