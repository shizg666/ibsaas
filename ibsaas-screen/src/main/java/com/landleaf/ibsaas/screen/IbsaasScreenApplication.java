package com.landleaf.ibsaas.screen;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.landleaf.ibsaas.*",exclude= {DataSourceAutoConfiguration.class})
public class IbsaasScreenApplication {

    public static void main(String[] args) {
        SpringApplication.run(IbsaasScreenApplication.class, args);
    }

}
