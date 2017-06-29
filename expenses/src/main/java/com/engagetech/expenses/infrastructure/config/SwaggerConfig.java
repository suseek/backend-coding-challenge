package com.engagetech.expenses.infrastructure.config;

import com.engagetech.expenses.web.ApiParameters;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Map;

import static springfox.documentation.builders.PathSelectors.regex;

@Setter
@Getter
@Configuration
@EnableSwagger2
@Profile("!prd")
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfig {

	private Map<String, String> info;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage(ApiParameters.class.getPackage().getName()))
				.paths(regex(ApiParameters.API_V1 + "/.*"))
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact(info.get("contactName"), info.get("url"), info.get("e-mail"));
		return new ApiInfo(
				info.get("title"),
				info.get("description"),
				info.get("version"),
				"Terms of service",
				contact,
				"Licence of API",
				"https://www.engagetech.com/");
	}
}
