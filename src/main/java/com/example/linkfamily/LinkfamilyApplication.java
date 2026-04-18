package com.example.linkfamily;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.linkfamily.Mapper")
public class LinkfamilyApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkfamilyApplication.class, args);
	}

}
