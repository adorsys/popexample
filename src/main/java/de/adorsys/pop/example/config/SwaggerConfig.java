package de.adorsys.pop.example.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * fpo
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket accountApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(UserResource.class)).paths(PathSelectors.any()).build()
				.apiInfo(new ApiInfo("PoP Example",
						"This is a simple PoP Endpoint. ",
						"1.0", "urn:tos", new Contact("adorsys GmbH & Co. KG", null, "fpo@adorsys.de"),
						"Apache License, Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0.html"))
				.securitySchemes(securitySchemes(new ApiKey("Authorization", "BearerToken", "header")));
	}

	private List<? extends SecurityScheme> securitySchemes(ApiKey apiKey) {
		ArrayList<ApiKey> arrayList = new ArrayList<>();
		arrayList.add(apiKey);
		return arrayList;
	}
}