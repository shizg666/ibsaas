package com.landleaf.ibsaas.client.light.init;

import com.landleaf.ibsaas.client.light.service.LightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 启动完成后加载资源
 */
@Component
@Slf4j
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private LightService lightService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
