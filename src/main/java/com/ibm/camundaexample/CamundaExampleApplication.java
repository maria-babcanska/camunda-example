package com.ibm.camundaexample;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableProcessApplication
@SpringBootApplication
public class CamundaExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamundaExampleApplication.class, args);
	}

}
