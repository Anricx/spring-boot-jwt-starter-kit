package com.github.anricx.config;

import com.github.anricx.security.SecurityConstants;
import com.google.common.base.Predicates;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Configuration
@ConfigurationProperties(prefix = "swagger")
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)//
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title(apiInfo.getTitle())
                        .description(apiInfo.getDescription())
                        .version(apiInfo.getVersion())
                        .license(apiInfo.getLicense())
                        .licenseUrl(apiInfo.getLicenseUrl())
                        .contact(new Contact(apiInfo.getContact().getName(), apiInfo.getContact().getUrl(), apiInfo.getContact().getEmail()))
                        .build())
                .useDefaultResponseMessages(false)
                .securitySchemes(Arrays.asList(new ApiKey(SecurityConstants.BearerAPIKey, SecurityConstants.TOKEN_HEADER, In.HEADER.name())))
//                .tags(new Tag("users", "Operations about users"))//
//                .tags(new Tag("ping", "Just a ping"))//
                .genericModelSubstitutes(Optional.class);
    }

    @Getter
    private final Metadata apiInfo = new Metadata();

    @Setter
    @Getter
    private class Metadata {

        private String title = "JWT REST API";
        private String description = "This is a JWT authentication service. You can find out more about JWT at [https://jwt.io/](https://jwt.io/).";
        private String version = "1.0.0";
        private String license;
        private String licenseUrl;
        private final MetadataContact contact = new MetadataContact();

    }

    @Setter
    @Getter
    private class MetadataContact {

        private String name = "D.T.";
        private String url = "https://github.com/Anricx";
        private String email = "joe.dengtao@gmail.com";
    }
}
