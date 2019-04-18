package com.landleaf.ibsaas.client.parking.lifang.controller;

import com.landleaf.ibsaas.client.parking.lifang.service.IChannelService;
import com.landleaf.ibsaas.client.parking.lifang.service.IChargeruleService;
import com.landleaf.ibsaas.common.domain.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chargerule")
public class ChargeruleController extends BasicController {

    Logger logger = LoggerFactory.getLogger(ChargeruleController.class);


    @Autowired
    private IChargeruleService chargeruleService;

    @PostMapping(value = "/all")
    public Response queryAllChargerule(){
       return returnSuccess(chargeruleService.queryAllChargerule());
    }
}
