package com.talk.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.talk.app.**.mapper")
public class TalkProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalkProjectApplication.class, args);
	}

}
