package com.mediahub.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.mediahub.entities")
@SpringBootApplication
public class MediahubBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediahubBackApplication.class, args);
	}

}
