package com.landleaf.ibsaas.web;

import com.landleaf.ibsaas.web.tcp.init.TCPServer;
import io.swagger.annotations.Api;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * hvac分支
 */
@Configuration
//自动加载配置信息
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class})
@SpringBootApplication
@ComponentScan(basePackages = "com.landleaf.ibsaas.*")
@MapperScan("com.landleaf.ibsaas.**.dao")
public class IbsaasWebApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(IbsaasWebApplication.class, args);
        TCPServer tcpServer = context.getBean(TCPServer.class);
        tcpServer.run();
    }

    /**
     * 配置swagger.
     */
    @Bean
    public Docket createRestApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("ibsaas")
                .description("ibsaas管理系统")
                .version("1.0")
                .contact(new Contact("wenyilu", "", "wenyilu@landleaf-tech.com"))
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("权限管理服务")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.landleaf.ibsaas.web.web.controller.leo"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 配置swagger.
     */
    @Bean
    public Docket createParkingRestApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("ibsaas")
                .description("ibsaas管理系统")
                .version("1.0")
                .contact(new Contact("wenyilu", "", "wenyilu@landleaf-tech.com"))
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("停车业务服务")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.landleaf.ibsaas.web.web.controller.parking"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }
    /**
     * 配置swagger.
     */
    @Bean
    public Docket createKnightRestApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("ibsaas")
                .description("ibsaas管理系统")
                .version("1.0")
                .contact(new Contact("wenyilu", "", "wenyilu@landleaf-tech.com"))
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("门禁业务服务")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.landleaf.ibsaas.web.web.controller.knight"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


}
