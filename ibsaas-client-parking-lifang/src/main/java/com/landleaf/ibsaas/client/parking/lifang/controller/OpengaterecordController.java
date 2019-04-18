package com.landleaf.ibsaas.client.parking.lifang.controller;

import com.landleaf.ibsaas.client.parking.lifang.service.IOpengaterecordService;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.parking.request.OpengaterecordListQueryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/opengaterecord")
public class OpengaterecordController extends BasicController {

    Logger logger = LoggerFactory.getLogger(OpengaterecordController.class);


    @Autowired
    private IOpengaterecordService opengaterecordService;

    @PostMapping(value = "/list")
    public Response userinfoList(@RequestBody OpengaterecordListQueryDTO queryDTO) {
        return null;
    }


    public static void main(String[] args) {
    }
}
