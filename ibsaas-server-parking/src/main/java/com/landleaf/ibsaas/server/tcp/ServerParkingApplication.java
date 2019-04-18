package com.landleaf.ibsaas.server.tcp;

import com.landleaf.ibsaas.server.tcp.init.TCPServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableScheduling
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = "com.landleaf.ibsaas.*")
@MapperScan("com.landleaf.ibsaas.**.dao")
public class ServerParkingApplication implements WebMvcConfigurer {
    @Autowired
    Environment environment;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ServerParkingApplication.class, args);
        TCPServer tcpServer = context.getBean(TCPServer.class);
        tcpServer.run();
    }
}
