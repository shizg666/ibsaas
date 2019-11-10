package com.landleaf.ibsaas.client.parking.lifang;

import com.landleaf.ibsaas.client.parking.lifang.tcp.init.TCPClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableScheduling
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
        RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class})
@ComponentScan(basePackages = "com.landleaf.ibsaas.*")
@MapperScan("com.landleaf.ibsaas.**.dao")
public class LifangParkingApplication implements WebMvcConfigurer {
    @Autowired
    Environment environment;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(LifangParkingApplication.class, args);
        TCPClient tcpClient = context.getBean(TCPClient.class);
        tcpClient.run();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //添加静态资源文件
//		registry.addResourceHandler("/static/**").addResourceLocations("classpath:META-INF/resources/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    }
}
