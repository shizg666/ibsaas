package com.landleaf.ibsaas.client.light.controller;

import com.landleaf.ibsaas.client.light.service.LightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/light")
public class LightController {
@Autowired
private LightService lightService;

    @GetMapping("/v1/controlLight")
    public String controlLight(@RequestParam("code") String code) {
        lightService.controlLight2(code);
        return "good";
    }
}
