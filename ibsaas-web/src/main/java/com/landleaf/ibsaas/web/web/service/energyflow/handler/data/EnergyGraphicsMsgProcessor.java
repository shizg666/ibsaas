package com.landleaf.ibsaas.web.web.service.energyflow.handler.data;

import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportResponseVO;
import com.landleaf.ibsaas.common.enums.energy.EnergyGraphicsEnum;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.spring.SpringManagerAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 反射找到图表处理类，处理方法 根据图表Code
 */
@Component
public class EnergyGraphicsMsgProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnergyGraphicsMsgProcessor.class);

    /**
     * 利用反射指向具体处理类
     */
    public List<EnergyReportResponseVO> process(EnergyGraphicsEnum energyGraphicsEnum, Object requestBody) {

        try {
            Object bean = null;
            Method method = null;
            try {
                bean = SpringManagerAware.getApplicationContext().getBean(energyGraphicsEnum.getBeanName());
            } catch (Exception e) {
                LOGGER.error(String.format("%s,%s", "未找到具体处理类", e.getMessage()), e);
                throw new BusinessException("未找到具体处理类");
            }
            Object params = null;
            try {
                params = JSON.parseObject(JSON.toJSONString(requestBody), energyGraphicsEnum.getParamName());
            } catch (Exception e) {
                LOGGER.error(String.format("%s,%s", "入参转换错误", e.getMessage()), e);
                throw new BusinessException("入参转换错误");
            }
            method = bean.getClass().getMethod(energyGraphicsEnum.getMethodName(), new Class[]{energyGraphicsEnum.getParamName()});
            Object responseData = ReflectionUtils.invokeMethod(method, bean, params);
            return (List<EnergyReportResponseVO>) responseData;
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
            return null;
        }
    }
}