package com.kaizensoftware.visitstory.common.config;

import com.kaizensoftware.visitstory.common.util.Constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.desc}")
    private String appDescription;

    @Value("${app.maintainer.name}")
    private String appMaintainerName;

    @Value("${app.maintainer.email}")
    private String appMaintainerEmail;

    @Value("${app.maintainer.url}")
    private String appMaintainerUrl;

    private static final String AUTH_SERVER = "http://localhost:8090/oauth";

    @Bean
    public Docket placesDocket() {
        return docket("VisitStory", "/api-visit-story/.*");
    }

    private List<Parameter> dockParameters() {

        List<Parameter> parameters = new ArrayList<>();

        Parameter authorizationParameter = new ParameterBuilder()
                .name(Constants.AUTHORIZATION)
                .description("Authentication header")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        parameters.add(authorizationParameter);

        return parameters;
    }

    private Docket docket(String title, String path) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(title)

                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex(path))
                .build()

                .apiInfo(info())
                .globalOperationParameters(dockParameters())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(securityScheme()));
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder().build();
    }

    private ApiInfo info() {

        return new ApiInfoBuilder()
                .title(appName)
                .description(appDescription)
                .version(appVersion)
                .contact(new Contact(
                        this.appMaintainerName,
                        this.appMaintainerUrl,
                        this.appMaintainerEmail
                )).build();
    }

    private SecurityScheme securityScheme() {

        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(AUTH_SERVER + "/token");

        SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
                .grantTypes(Collections.singletonList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();

        return oauth;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.ant("/**"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        return Collections.singletonList(new SecurityReference("spring_oauth", scopes()));
    }

    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = {
                new AuthorizationScope("read", "for read operations"),
                new AuthorizationScope("write", "for write operations"),
                new AuthorizationScope("foo", "Access foo API") };
        return scopes;
    }

}
