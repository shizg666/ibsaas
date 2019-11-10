package com.landleaf.ibsaas.datasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = "com.landleaf.ibsaas.*")
@MapperScan("com.landleaf.**.dao")
public class IbsaasDatasourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbsaasDatasourceApplication.class, args);
	}
}
