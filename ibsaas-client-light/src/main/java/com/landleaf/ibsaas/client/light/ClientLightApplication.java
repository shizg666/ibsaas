package com.landleaf.ibsaas.client.light;


import com.landleaf.ibsaas.client.light.client.NettyPoolClient;
import io.swagger.annotations.Api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = "com.landleaf.ibsaas.*")
public class ClientLightApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ClientLightApplication.class, args);
        NettyPoolClient nettyPoolClient = context.getBean(NettyPoolClient.class);
        nettyPoolClient.build();
//        NettyTcpClient nettyTcpClient = context.getBean(NettyTcpClient.class);
//        nettyTcpClient.run();
//        final SimpleChannelPool pool = nettyPoolClient.poolMap.get(nettyPoolClient.addr2);
//        Future<Channel> f = pool.acquire();
//        f.addListener((FutureListener<Channel>) f1 -> {
//            if (f1.isSuccess()) {
//                Channel ch = f1.getNow();
//                HexData.stringToBytes("02 52 31 53 30 21 03");
//                ch.writeAndFlush(HexData.stringToBytes("02 52 31 53 31 21 03"));
//                // Release back to pool
//                pool.release(ch);
//            }
//        });
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
