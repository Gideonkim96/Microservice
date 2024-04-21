package com.kim.loans;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.kim.loans.controller") })
@EnableJpaRepositories("com.kim.loans.repository")
@EntityScan("com.kim.loans.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Loans microservice REST API Documentation",
				description = "KimBank Loans microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Rono Giddy",
						email = "tutor@kim.com",
						url = "https://www.kim.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.kim.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "KimBank Loans microservice REST API Documentation",
				url = "https://www.kim.com/swagger-ui.html"
		)
)
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}
}
