package com.ypb.designpattern;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan("com.ypb.designpattern.*")
public class ApplicationDesignPatternMain {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationDesignPatternMain.class, args);
	}
}
