package com.lining.workflow.activitiworkflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
		org.activiti.spring.boot.SecurityAutoConfiguration.class
})
public class ActivitiWorkflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitiWorkflowApplication.class, args);
	}
}
