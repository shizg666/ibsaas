package com.landleaf.ibsaas.client.hvac;

import io.swagger.annotations.Api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.landleaf.ibsaas.*")
public class IbsaasClientHvacApplication {

    public static void main(String[] args) {
        SpringApplication.run(IbsaasClientHvacApplication.class, args);
    }


    /**
     * 配置swagger.
     */
    @Bean
    public Docket createHvacRestApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("ibsaas")
                .description("ibsaas管理系统")
                .version("1.0")
                .contact(new Contact("lujingyao", "", "lujingyao@landleaf-tech.com"))
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("暖通业务服务")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.landleaf.ibsaas.client.hvac.controller"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }
}
