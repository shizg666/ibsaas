package com.landleaf.ibsaas.client.light.controller;

import com.landleaf.ibsaas.client.light.handle.light.AbstractLightHandler;
import com.landleaf.ibsaas.client.light.service.LightService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ligth")
//@Api(value = "/ligth", description = "灯光控制业务相关操作")
public class LightController {
@Autowired
private LightService lightService;


    @Autowired
    private Map<String, AbstractLightHandler> map;
    @GetMapping("/v1/ligth/controlLight")
    @ApiOperation(value = "获取楼栋所有信息", notes = "获取楼栋所有信息")
    public String controlLight(String code) {
        map.forEach((k,v)->{
            System.out.println(k+":"+v);
        });
//        lightService.controlLight2(code);
        return "good";
    }
}
