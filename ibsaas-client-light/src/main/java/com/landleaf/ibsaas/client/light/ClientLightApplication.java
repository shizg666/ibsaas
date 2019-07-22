package com.landleaf.ibsaas.client.light;


import com.landleaf.ibsaas.client.light.client.NettyPoolClient;
import io.swagger.annotations.Api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = "com.landleaf.ibsaas.*")
@EnableSwagger2
@EnableAsync
public class ClientLightApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ClientLightApplication.class, args);
//        NettyPoolClient nettyPoolClient = context.getBean(NettyPoolClient.class);
//        nettyPoolClient.build();
    }

    /**
     * 配置swagger.
     */
    @Bean
    public Docket createLightApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("ibsaas")
                .description("ibsaas管理系统")
                .version("1.0")
                .contact(new Contact("shizengguang", "", "shizengguang@landleaf-tech.com"))
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("灯光业务服务")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.landleaf.ibsaas.client.light.controller"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }
}
