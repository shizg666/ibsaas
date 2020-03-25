package com.landleaf.ibsaas.client.parking.lifang.mq.processor;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.client.parking.lifang.mq.domain.ParkingResponse;
import com.landleaf.ibsaas.client.parking.lifang.mq.service.IUserinfoService;
import com.landleaf.ibsaas.common.domain.parking.request.UserinfoDetailQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.request.UserinfoListQueryDTO;
import com.landleaf.ibsaas.common.domain.parking.response.UserinfoResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 车辆
 */
@Component
public class ParkingUserinfoMsgProcess extends  BaseProcess  {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingUserinfoMsgProcess.class);
    @Autowired
    private IUserinfoService userinfoService;

    /**
     * 车辆列表
     *
     * @param requestBody
     * @return
     */
    public ParkingResponse userinfoList(UserinfoListQueryDTO requestBody) {
        LOGGER.info("收到【车辆列表】请求,{}", JSON.toJSONString(requestBody));
        PageInfo pageInfo = userinfoService.pageQueryList(requestBody);
        Map<String,Object> resultMap = Maps.newHashMap();
        resultMap.put("total",pageInfo.getTotal());
        resultMap.put("list",pageInfo.getList());
        return returnSuccess(resultMap);
    }
    /**
     * 车辆详情
     *
     * @param requestBody
     * @return
     */
    public ParkingResponse userinfo(UserinfoDetailQueryDTO requestBody) {
        LOGGER.info("收到【车辆详情】请求,{}", JSON.toJSONString(requestBody));
        UserinfoResponseDTO result = userinfoService.queryInfo(requestBody);
        return returnSuccess(result);
    }


}
