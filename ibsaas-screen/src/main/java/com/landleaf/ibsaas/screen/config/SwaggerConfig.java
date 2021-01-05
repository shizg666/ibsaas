//package com.landleaf.ibsaas.screen.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;
//
///**
// * @author Lokiy
// * @date 2019/12/17 11:49
// * @description:
// */
//@Configuration
//@EnableSwagger2WebFlux
//public class SwaggerConfig {
//
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(new ApiInfoBuilder()
//                        .description("ibsaas-screen swagger2 document")
//                        .title("ibsaas-screen")
//                        .version("1.0.0")
//                        .build())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.landleaf.ibsaas.screen.controller"))
//                .paths(PathSelectors.any())
//                .build();
//
//    }
//}