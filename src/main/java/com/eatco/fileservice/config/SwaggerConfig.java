package com.eatco.fileservice.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eatco.fileservice.utils.SwaggerConstants;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${app.client.id}")
	private String clientId;

	@Value("${jwt.secret}")
	private String clientSecret;

	@Value("${host.full.dns.auth.link}")
	private String authLink;

	@Value("${app.swagger.module}")
	private String module;

	@Value("${app.swagger.termofurl}")
	private String termOfUrl;

	@Value("${app.swagger.contact.name}")
	private String contactName;

	@Value("${app.swagger.contact.url}")
	private String contactUrl;

	@Value("${app.swagger.contact.email}")
	private String contactEmail;

	@Value("${app.swagger.license.type}")
	private String licenseType;

	@Value("${app.swagger.license.url}")
	private String licenseUrl;

	@Value("${app.swagger.version}")
	private String swaggerVersion;

	/**
	 * A builder which is intended to be the primary interface into the
	 * swagger-springmvc framework. Provides sensible defaults and convenience
	 * methods for configuration.
	 */
	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.securityContexts(Arrays.asList(securityContext())).securitySchemes(Arrays.asList(apiKey())).select()
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
	}

	/**
	 * A class to represent a default set of authorizations to apply to each api
	 * operation To customize which request mappings the list of authorizations are
	 * applied to Specify the custom includePatterns or requestMethods
	 */
	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	/**
	 * Processes an {@link SecurityReference} request.
	 *
	 *
	 */

	private List<SecurityReference> defaultAuth() {

		AuthorizationScope authorizationScope = new AuthorizationScope(SwaggerConstants.SWAGGER_SCOPE_TYPE,
				SwaggerConstants.SWAGGER_SCOPE_VALUE);
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[4];
		authorizationScopes[0] = new AuthorizationScope(SwaggerConstants.SWAGGER_READ,
				SwaggerConstants.SWAGGER_READ_ALL);
		authorizationScopes[1] = new AuthorizationScope(SwaggerConstants.SWAGGER_TRUST,
				SwaggerConstants.SWAGGER_TRUST_ALL);
		authorizationScopes[2] = new AuthorizationScope(SwaggerConstants.SWAGGER_WRITE,
				SwaggerConstants.SWAGGER_WRITE_ALL);
		authorizationScopes[3] = authorizationScope;
		return Arrays.asList(new SecurityReference(SwaggerConstants.SWAGGER_SECURITY_REFRENCE, authorizationScopes));
	}

	/**
	 * Processes an {@link SecurityConfiguration} request.
	 *
	 *
	 */
	@Bean
	public SecurityConfiguration securityInfo() {
		return new SecurityConfiguration(clientId, clientSecret, "", "", "", ApiKeyVehicle.HEADER, "", " ");
	}

	/**
	 * Processes an {@link ApiInfo} request.
	 *
	 *
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(module).description("").termsOfServiceUrl(termOfUrl)
				.contact(new Contact(contactName, contactUrl, contactEmail)).license(licenseType).licenseUrl(licenseUrl)
				.version(swaggerVersion).build();
	}

	/**
	 * Processes an {@link ApiKey} request.
	 *
	 *
	 */
	private ApiKey apiKey() {
		return new ApiKey(SwaggerConstants.SWAGGER_SECURITY_REFRENCE, SwaggerConstants.SWAGGER_AUTH,
				SwaggerConstants.SWAGGER_HEADER);
	}
}