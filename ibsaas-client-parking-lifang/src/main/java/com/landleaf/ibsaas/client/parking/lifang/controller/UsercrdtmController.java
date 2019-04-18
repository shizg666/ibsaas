package com.landleaf.ibsaas.client.parking.lifang.controller;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.client.parking.lifang.service.IUsercrdtmService;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmListQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmRealCountQueryByHourDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmRealCountQueryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usercrdtm")
public class UsercrdtmController extends BasicController {

    Logger logger = LoggerFactory.getLogger(UsercrdtmController.class);


    @Autowired
    private IUsercrdtmService usercrdtmService;

    @PostMapping(value = "/list")
    public Response usercrdtmList(@RequestBody UsercrdtmListQueryDTO queryDTO ){
       return returnSuccess(usercrdtmService.pageQueryList(queryDTO));
    }

    /**
     * 车位实时数量
     * @param queryDTO
     * @return
     */
    @PostMapping(value = "/real-count")
    public Response realCount(@RequestBody UsercrdtmRealCountQueryDTO queryDTO ){
       return returnSuccess(usercrdtmService.realCount(queryDTO));
    }
    /**
     * 车位实时数量--时间段
     * @param queryDTO
     * @return
     */
    @PostMapping(value = "/real-count/hour")
    public Response realCountFHour(@RequestBody UsercrdtmRealCountQueryByHourDTO queryDTO ){
       return returnSuccess(usercrdtmService.realCountFHour(queryDTO));
    }

    public static void main(String[] args) {
//        UsercrdtmListQueryDTO usercrdtmListQueryDTO = new UsercrdtmListQueryDTO();
//        usercrdtmListQueryDTO.setCarCode("沪M56795");
//        usercrdtmListQueryDTO.setChargeTypeCode("1001");
//        usercrdtmListQueryDTO.setPageNum(1);
//        usercrdtmListQueryDTO.setPageSize(20);
//        usercrdtmListQueryDTO.setStartTime("2068-11-21 23:59:59");
//        usercrdtmListQueryDTO.setEndTime("2068-11-21 23:59:59");
//        usercrdtmListQueryDTO.setChannelCode("1");
//        usercrdtmListQueryDTO.setChannelType("进");
//        System.out.println(JSON.toJSONString(usercrdtmListQueryDTO));
        UsercrdtmRealCountQueryDTO usercrdtmRealCountQueryDTO = new UsercrdtmRealCountQueryDTO();
        usercrdtmRealCountQueryDTO.setTotal(19);
        usercrdtmRealCountQueryDTO.setResetTime("2068-11-21 23:59:59");
        System.out.println(JSON.toJSONString(usercrdtmRealCountQueryDTO));
    }




}
