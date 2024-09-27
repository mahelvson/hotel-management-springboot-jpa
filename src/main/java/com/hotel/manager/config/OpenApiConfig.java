package com.hotel.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
public class OpenApiConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("API de Gerenciamento de Reservas de Hotel")
						.version("1.0")
						.description("Esta API serve para gerenciamento de reservas em quartos de hotéis de uma mesma rede")
						.contact(new Contact()
								.name("Mahelvson B. Chaves")
								.email("mahelvson@gmail.com")
								.url("https://www.mahelvson.com")))
				.addTagsItem(new io.swagger.v3.oas.models.tags.Tag().name("Hotel").description("Operations related to hotels"))
				.addTagsItem(new io.swagger.v3.oas.models.tags.Tag().name("Room").description("Operations related to rooms"))
				.addTagsItem(new io.swagger.v3.oas.models.tags.Tag().name("Manager").description("Operations related to the manager"))
				.addTagsItem(new io.swagger.v3.oas.models.tags.Tag().name("Receptionist").description("Operations related to receptionists"))
				.addTagsItem(new io.swagger.v3.oas.models.tags.Tag().name("Client").description("Operations related to clients"))
				.addTagsItem(new io.swagger.v3.oas.models.tags.Tag().name("Booking").description("Operations related to bookings"))
				.addTagsItem(new io.swagger.v3.oas.models.tags.Tag().name("Payment").description("Operations related to payments"))
				.addTagsItem(new io.swagger.v3.oas.models.tags.Tag().name("Report").description("Operations related to report generation"))
				.addTagsItem(new io.swagger.v3.oas.models.tags.Tag().name("Login").description("Operations related to authentication"))
				.addSecurityItem(new SecurityRequirement());		
						
	}
}
