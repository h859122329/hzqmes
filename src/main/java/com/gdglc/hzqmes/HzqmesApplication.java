package com.gdglc.hzqmes;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.gdglc.hzqmes.dao")
public class HzqmesApplication {
	public static void main(String[] args) {
			SpringApplication.run(HzqmesApplication.class, args);
	}
}
