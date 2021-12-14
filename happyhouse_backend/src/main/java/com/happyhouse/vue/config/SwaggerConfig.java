package com.happyhouse.vue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// http://localhost:9999/vue/swagger-ui.html

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("happyHouse").apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.happyhouse.vue.controller"))
                .paths(PathSelectors.ant("/api/**")).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Happy House API").description("Happy House API입니다.").licenseUrl("yjkim012279@gmail.com").version("1.0").build();
    }
}
