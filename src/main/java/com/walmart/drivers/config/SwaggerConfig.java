package com.walmart.drivers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public Docket config(){
        return new Docket(DocumentationType.OAS_30)
            .apiInfo(new ApiInfoBuilder()
                .title("Driver store system")
                .description("small demo for driver and story system")
                .version("0.0.1")
                .build())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build();
    }
}
