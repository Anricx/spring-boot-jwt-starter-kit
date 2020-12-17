package com.github.anricx.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.anricx.security.SecurityConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(
        title = "JWT REST API",
        version = "1.0.0",
        description = "This is a JWT authentication service. You can find out more about JWT at [https://jwt.io/](https://jwt.io/).",
        contact = @Contact(
                url = "https://github.com/Anricx",
                name = "Anricx",
                email = "joe.dengtao@gmail.com"
        ))
)
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = SecurityConstants.BearerAPIKey,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "Bearer <TOKEN>",
        description = "Bearer TOKEN"
)
@Configuration
public class SpringConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
