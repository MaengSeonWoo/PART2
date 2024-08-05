package com.talk.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.talk.app.**.mapper"})
public class TalkProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalkProjectApplication.class, args);
	}

}
