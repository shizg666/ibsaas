package com.landleaf.ibsaas.client.parking.lifang.controller;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.client.parking.lifang.service.IUserinfoService;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.parking.request.UserinfoDetailQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UserinfoListQueryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userinfo")
public class UserinfoController extends BasicController {

    Logger logger = LoggerFactory.getLogger(UserinfoController.class);


    @Autowired
    private IUserinfoService userinfoService;

    @PostMapping(value = "/list")
    public Response userinfoList(@RequestBody UserinfoListQueryDTO queryDTO ){
       return returnSuccess(userinfoService.pageQueryList(queryDTO));
    }
    @PostMapping(value = "/detail")
    public Response detail(@RequestBody UserinfoDetailQueryDTO queryDTO ){
       return returnSuccess(userinfoService.queryInfo(queryDTO));
    }




    public static void main(String[] args) {
        UserinfoListQueryDTO userinfoListQueryDTO = new UserinfoListQueryDTO();
        userinfoListQueryDTO.setCarCode("沪M56795");
        userinfoListQueryDTO.setUserName("申");
        userinfoListQueryDTO.setChargeTypeCode("1001");
        userinfoListQueryDTO.setExpireStatus("未到期");
        userinfoListQueryDTO.setPage(1);
        userinfoListQueryDTO.setLimit(20);
        userinfoListQueryDTO.setExpireTime("2068-11-21 23:59:59");
        System.out.println(JSON.toJSONString(userinfoListQueryDTO));
    }
}
