package com.landleaf.ibsaas.web.web.service.parking;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.internal.LinkedTreeMap;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.parking.ParkingMessage;
import com.landleaf.ibsaas.common.domain.parking.request.*;
import com.landleaf.ibsaas.common.domain.parking.response.ChannelResponseDTO;
import com.landleaf.ibsaas.common.domain.parking.response.ChargeruleResponseDTO;
import com.landleaf.ibsaas.common.domain.parking.response.UserinfoResponseDTO;
import com.landleaf.ibsaas.common.enums.parking.MsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.ParkingSubMsgTypeEnum;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.utils.MessageUtil;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.web.asyn.IFutureService;
import com.landleaf.ibsaas.web.rocketmq.WebMqProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ParkingService implements IParkingServeice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingService.class);

    @Value("${rocketmq.producer.client.parking.topic}")
    private String CLIENT_TOPIC;
    @Value("${rocketmq.producer.client.parking.response-time-out}")
    private  Long TIME_OUT;

    @Autowired
    private WebMqProducer webMqProducer;
    @Autowired
    private IFutureService futureService;
    @Autowired
    private IParkingRealCountInitService parkingRealCountInitService;

    @Override
    public Response getChannelList(ChannelListQueryDTO sendRequest) {
        List<Map<String, String>> results = Lists.newArrayList();
        //获取基础数据
        Response response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.PARKING.getName(),
                ParkingSubMsgTypeEnum.CHANNEL_LIST.getName());
        if (response != null && response.getResult() != null) {
            String jsonString = JSON.toJSONString(response.getResult());
            List<ChannelResponseDTO> channelResponseDTOS = JSON.parseArray(jsonString, ChannelResponseDTO.class);
            if (!CollectionUtils.isEmpty(channelResponseDTOS)) {
                channelResponseDTOS.forEach(item -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("value", item.getChannelCode());
                    map.put("displayName", item.getChannelName());
                    results.add(map);
                });
            }
        }
        response.setResult(results);
        return response;
    }

    @Override
    public Response queryAllChargerule() {
        List<Map<String, String>> results = Lists.newArrayList();
        //获取基础数据
        Response response = sendMsgWaitForResult(new BaseQueryDTO(),
                MsgTypeEnum.PARKING.getName(),
                ParkingSubMsgTypeEnum.CHARGE_RULE_LIST.getName());
        if (response != null && response.getResult() != null) {
            String jsonString = JSON.toJSONString(response.getResult());
            List<ChargeruleResponseDTO> chargeruleResponseDTOS = JSON.parseArray(jsonString, ChargeruleResponseDTO.class);
            if (!CollectionUtils.isEmpty(chargeruleResponseDTOS)) {
                chargeruleResponseDTOS.forEach(item -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("displayName", item.getChargeTypeName());
                    map.put("value", item.getChargeTypeCode());
                    results.add(map);
                });
            }
        }
        response.setResult(results);
        return response;
    }

    @Override
    public Response realCount(UsercrdtmRealCountQueryDTO sendRequest) {
        UsercrdtmRealCountQueryDTO initParams = parkingRealCountInitService.queryRealCountInitParams(sendRequest.getClientId());
        if (initParams == null) {
            throw new BusinessException("车位初始化配置不存在,请联系管理员配置");
        }
        BeanUtils.copyProperties(initParams, sendRequest);
        //获取基础数据
        Response response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.PARKING.getName(),
                ParkingSubMsgTypeEnum.REAL_COUNT_HOUR.getName());
        return response;
    }

    @Override
    public Map<String, Object> realCountFHour(UsercrdtmRealCountQueryByHourDTO sendRequest) {
        UsercrdtmRealCountQueryDTO initParams = parkingRealCountInitService.queryRealCountInitParams(sendRequest.getClientId());
        if (initParams == null) {
            throw new BusinessException("车位初始化配置不存在,请联系管理员配置");
        }
        BeanUtils.copyProperties(initParams, sendRequest);
        //获取基础数据
        Response response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.PARKING.getName(),
                ParkingSubMsgTypeEnum.REAL_COUNT_F_HOUR.getName());
        Map<String, Object> map = null;
        if (response != null && response.getResult() != null) {
            String jsonString = JSON.toJSONString(response.getResult());
            List<UsercrdtmRealCountQueryByHourDTO> list = JSON.parseArray(jsonString, UsercrdtmRealCountQueryByHourDTO.class);
            if (!CollectionUtils.isEmpty(list)) {
                List<String> xList = list.stream().map(i -> i.getCurrentHour()).collect(Collectors.toList());
                List<Integer> yList = list.stream().map(i -> i.getOccupyCount()).collect(Collectors.toList());
                map = Maps.newHashMap();
                map.put("x", xList);
                map.put("y", yList);
            }
        }
        return map;
    }

    @Override
    public Object usercrdtmList(UsercrdtmListQueryDTO sendRequest) {
        //获取基础数据
        Response response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.PARKING.getName(),
                ParkingSubMsgTypeEnum.USER_CRDTM_LIST.getName());
        if (response != null && response.getResult() != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public Object userinfoList(UserinfoListQueryDTO sendRequest) {
        //获取基础数据
        Response response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.PARKING.getName(),
                ParkingSubMsgTypeEnum.USERI_NFO_LIST.getName());
        if (response != null && response.getResult() != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public UserinfoResponseDTO userinfoDetail(UserinfoDetailQueryDTO sendRequest) {
        //获取基础数据
        Response response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.PARKING.getName(),
                ParkingSubMsgTypeEnum.USERI_NFO.getName());
        if (response != null && response.getResult() != null) {
            return JSON.parseObject(JSON.toJSONString(response.getResult()),UserinfoResponseDTO.class);
        }
        return null;
    }

    @Override
    public List<UsercrdtmInHistoryQueryDTO> trafficFlow(UsercrdtmInHistoryQueryDTO sendRequest) {
        //获取基础数据
        Response response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.PARKING.getName(),
                ParkingSubMsgTypeEnum.TRAFFIC_FLOW.getName());
        if (response != null && response.getResult() != null) {
            String jsonString = JSON.toJSONString(response.getResult());
            return JSON.parseArray(jsonString, UsercrdtmInHistoryQueryDTO.class);
        }
        return Lists.newArrayList();
    }

    /**
     * 获取停车业务响应数据
     *
     * @param requestBody 请求参数
     * @param msgName     请求消息类型
     * @param subMsgName  请求消息子类型
     * @return
     */
    private Response sendMsgWaitForResult(Object requestBody, String msgName, String subMsgName) {


        Response response = new Response();
        ParkingMessage request = new ParkingMessage();
        String msgId = MessageUtil.generateId(20);
        request.setMsgId(msgId);
        request.setSubMsgName(subMsgName);
        request.setMsgName(msgName);
        request.setRequestBody(requestBody);
        webMqProducer.sendMessage(JSON.toJSONString(request), CLIENT_TOPIC, TagConstants.TAGS_DEFAULT);
        Future<ParkingMessage> cacheFuture = futureService.getParkingCacheFuture(msgId, TIME_OUT);
        ParkingMessage result = new ParkingMessage();
        try {
            result = cacheFuture.get(TIME_OUT, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        if (result != null && result.getResponse() != null) {
            Object tmpResult = result.getResponse().getResult();
            if (tmpResult != null) {

                LinkedTreeMap knightResponse = (LinkedTreeMap) result.getResponse().getResult();
                response.setMessage((String) knightResponse.get("resultInfo"));
                response.setResult(knightResponse.get("obj"));
                String resultCode = (String) knightResponse.get("result");
                if (StringUtil.isEquals("200", resultCode)) {
                    response.setSuccess(true);
                } else {
                    throw new BusinessException((String) knightResponse.get("resultInfo"));
                }
            }
            return response;
        }
        throw new BusinessException("未获取到数据,稍后再试");
    }

    ;
}
