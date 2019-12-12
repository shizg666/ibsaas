package com.landleaf.ibsaas.screen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Lokiy
 * @date 2019/12/10 0010
 * @description:
 */
@RestController
@RequestMapping("/web/screen")
public class LargeScreenController{


    @RequestMapping("/test")
    public Mono<String> test(){
        return Mono.just("Hello World!");
    }
}
