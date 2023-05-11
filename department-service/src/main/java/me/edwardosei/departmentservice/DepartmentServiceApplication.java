package me.edwardosei.departmentservice;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(
		info = @Info(
				title = "Department Service REST APIs",
				description = "Department Service REST APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Edward",
						email = "edward.osei.nyarko@gmail.com",
						url = "https://github.com/nana-yaw"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://github.com/nana-yaw"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Department-Service Docs",
				url = "https://github.com/nana-yaw/springboot-microservices/tree/main/department-service"
		)
)
@SpringBootApplication
public class DepartmentServiceApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(DepartmentServiceApplication.class, args);
	}

}
