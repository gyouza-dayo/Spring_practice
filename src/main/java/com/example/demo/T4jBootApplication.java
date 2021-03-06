package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling		//SpringBootのスケジュール機能を使えるようにするアノテーション
@SpringBootApplication
public class T4jBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(T4jBootApplication.class, args);
	}
}
