package com.hardik.pomfrey.configuration;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import com.hardik.pomfrey.configuration.properties.OpenApiConfigurationProperties;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import lombok.AllArgsConstructor;

@Configuration
@EnableConfigurationProperties(OpenApiConfigurationProperties.class)
@AllArgsConstructor
public class OpenApiConfiguration {

	private final OpenApiConfigurationProperties openApiConfigurationProperties;

	@Bean
	public OperationCustomizer customJwtHeader() {
		final var header = openApiConfigurationProperties.getProperties().getHeader();
		return (Operation operation, HandlerMethod handlerMethod) -> {
			Parameter jwtHeader = new Parameter().in(ParameterIn.HEADER.toString()).schema(new StringSchema())
					.name(header.getName()).description(header.getDescription()).required(false);

			operation.addParametersItem(jwtHeader);
			return operation;
		};
	}

	@Bean
	public OpenAPI customOpenAPI() {
		final var properties = openApiConfigurationProperties.getProperties();
		final var contact = properties.getContact();
		return new OpenAPI().info(new Info().title(properties.getTitle()).version(properties.getApiVersion())
				.description(properties.getDescription())
				.contact(new Contact().email(contact.getEmail()).name(contact.getName()).url(contact.getUrl())));
	}

}
