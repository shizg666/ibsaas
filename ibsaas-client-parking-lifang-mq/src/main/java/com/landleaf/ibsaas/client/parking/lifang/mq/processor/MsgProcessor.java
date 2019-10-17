package com.landleaf.ibsaas.client.parking.lifang.mq.processor;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.client.parking.lifang.mq.domain.ParkingMessage;
import com.landleaf.ibsaas.client.parking.lifang.mq.domain.Response;
import com.landleaf.ibsaas.client.parking.lifang.mq.enums.ParkingSubMsgTypeEnum;
import com.landleaf.ibsaas.client.parking.lifang.mq.exception.BusinessException;
import com.landleaf.ibsaas.client.parking.lifang.mq.spring.SpringManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 处理消息路由  根据SubMsgName反射
 */
@Component
public class MsgProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgProcessor.class);


    /**
     * 利用反射指向具体处理类
     */
    public ParkingMessage process(String msgName, String subMsgName, ParkingMessage parkingMessage) {
        LOGGER.info("处理消息[msgName]{},[subMsgName]{},[data]{}", msgName, subMsgName, JSON.toJSONString(parkingMessage));

        ParkingMessage result = new ParkingMessage();
        Response response = new Response();
        response.setSuccess(true);
        response.setHasBusinessException(false);
        try {
            Object bean = null;
            Method method = null;
            ParkingSubMsgTypeEnum subMsgTypeEnum = null;
            try {
                subMsgTypeEnum = ParkingSubMsgTypeEnum.getByName(subMsgName);
                bean = SpringManager.getApplicationContext().getBean(subMsgTypeEnum.getBeanName());
            } catch (Exception e) {
                LOGGER.error(String.format("%s,%s", "未找到具体处理类", e.getMessage()), e);
                throw new BusinessException("未找到具体处理类");
            }
            Object requestBody = parkingMessage.getRequestBody();
            try {
                switch (subMsgTypeEnum.getParamType()) {
                    case 1://object
                        requestBody = JSON.parseObject(JSON.toJSONString(parkingMessage.getRequestBody()), subMsgTypeEnum.getParamName());
                        method = bean.getClass().getMethod(subMsgTypeEnum.getMethodName(), new Class[]{subMsgTypeEnum.getParamName()});
                        break;
                    case 2://集合
                        requestBody = JSON.parseArray(JSON.toJSONString(parkingMessage.getRequestBody()), subMsgTypeEnum.getParamName());
                        method = bean.getClass().getMethod(subMsgTypeEnum.getMethodName(), new Class[]{List.class});
                        break;
                }
            } catch (Exception e) {
                LOGGER.error(String.format("%s,%s", "入参转换错误", e.getMessage()), e);
                throw new BusinessException("入参转换错误");
            }

            Object responseData = ReflectionUtils.invokeMethod(method, bean, requestBody);
            response.setResult(responseData);
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
            response.setSuccess(false);
            if (exception instanceof BusinessException) {    //业务异常
                response.setHasBusinessException(true);
                response.setMessage(exception.getMessage());
                response.setErrorCode(Response.ERROR_CODE_BUSINESS_EXCEPTION);
            } else { //未捕获异常
                response.setHasBusinessException(false);
                response.setMessage("系统异常");
                response.setErrorCode(Response.ERROR_CODE_UNHANDLED_EXCEPTION);
            }
        }
        result.setMsgName(parkingMessage.getMsgName());
        result.setSubMsgName(parkingMessage.getSubMsgName());
        result.setFrom("ibsaas_parking_lifang_lgc_1921681010");
        result.setTo("web");
        result.setMsgId(parkingMessage.getMsgId());
        result.setResponse(response);
        return result;
    }


}