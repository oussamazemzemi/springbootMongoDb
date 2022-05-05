package com.student.information.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * @author OZ
 */
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Students API", version = "2.0", description = "Students Information"))
public class StudentInformationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentInformationSystemApplication.class, args);
	}

}
