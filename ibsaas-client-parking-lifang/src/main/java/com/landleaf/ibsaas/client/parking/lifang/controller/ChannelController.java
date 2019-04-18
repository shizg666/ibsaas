package com.landleaf.ibsaas.client.parking.lifang.controller;

import com.landleaf.ibsaas.client.parking.lifang.service.IChannelService;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.parking.request.ChannelListQueryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channel")
public class ChannelController extends BasicController {

    Logger logger = LoggerFactory.getLogger(ChannelController.class);


    @Autowired
    private IChannelService channelService;

    @PostMapping(value = "/query/type")
    public Response queryChannelByType(@RequestBody ChannelListQueryDTO queryDTO ){
       return returnSuccess(channelService.queryChannelByType(queryDTO));
    }
}
