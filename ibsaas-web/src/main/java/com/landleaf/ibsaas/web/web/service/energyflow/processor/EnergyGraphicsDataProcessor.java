package com.landleaf.ibsaas.web.web.service.energyflow.processor;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.landleaf.ibsaas.common.domain.energy.vo.ConfigSettingVO;
import com.landleaf.ibsaas.common.enums.energy.EnergyGraphicsEnum;
import com.landleaf.ibsaas.common.enums.energy.EnergyTypeEnum;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.spring.SpringManagerAware;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.web.web.redis.energy.ConfigSettingRedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 反射找到图表处理类，处理方法 根据图表Code
 */
@Component
public class EnergyGraphicsDataProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnergyGraphicsDataProcessor.class);
    @Autowired
    private ConfigSettingRedis configSettingRedis;
    /**
     * 利用反射指向具体处理类
     */
    public Object process(EnergyGraphicsEnum energyGraphicsEnum, Object requestBody) {

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
            return responseData;
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
            return null;
        }
    }

    /**
     * 根据查询类型找到相应分区或者分项字典值
     * @param queryType
     * @return
     */
    public Map<String, List<ConfigSettingVO>> getQueryTypeGroup(Integer queryType,Integer energyType ) {
        List<ConfigSettingVO> classificationList = configSettingRedis.getConfigSettingVOByType("equip_classification");
        List<ConfigSettingVO> areaList = configSettingRedis.getConfigSettingVOByType("equip_area");

        //区域未加标记
        List<ConfigSettingVO> waterAreaList = areaList.stream().filter(i -> StringUtils.equals(i.getCharacter1(), String.valueOf(EnergyTypeEnum.ENERGY_WATER.getEnergyType()))).collect(Collectors.toList());
        List<ConfigSettingVO> electricAreaList = areaList.stream().filter(i -> StringUtils.equals(i.getCharacter1(), String.valueOf(EnergyTypeEnum.ENERGY_ELECTRIC.getEnergyType()))).collect(Collectors.toList());
        List<ConfigSettingVO> waterClassificationList = classificationList.stream().filter(i -> StringUtils.equals(i.getCharacter1(), String.valueOf(EnergyTypeEnum.ENERGY_WATER.getEnergyType()))).collect(Collectors.toList());
        List<ConfigSettingVO> electricClassificationList = classificationList.stream().filter(i -> StringUtils.equals(i.getCharacter1(), String.valueOf(EnergyTypeEnum.ENERGY_ELECTRIC.getEnergyType()))).collect(Collectors.toList());

        Map<String, List<ConfigSettingVO>> waterClassificationGroup = waterClassificationList.stream().collect(Collectors.groupingBy(ConfigSettingVO::getSettingCode));
        Map<String, List<ConfigSettingVO>> electricClassificationGroup = electricClassificationList.stream().collect(Collectors.groupingBy(ConfigSettingVO::getSettingCode));
        Map<String, List<ConfigSettingVO>> waterAreaGroup = areaList.stream().collect(Collectors.groupingBy(ConfigSettingVO::getSettingCode));
        Map<String, List<ConfigSettingVO>> electricAreaGroup = areaList.stream().collect(Collectors.groupingBy(ConfigSettingVO::getSettingCode));

        Map<String, List<ConfigSettingVO>> queryTypeGroup = null;

        switch (queryType) {
            case 1:
                //分区
                if(energyType.intValue()== EnergyTypeEnum.ENERGY_WATER.getEnergyType()){
                    //水
                    queryTypeGroup = waterAreaGroup;
                }else if(energyType.intValue()== EnergyTypeEnum.ENERGY_ELECTRIC.getEnergyType()){
                    //电
                    queryTypeGroup = electricAreaGroup;
                }

                break;
            case 2:
                //分项
                if(energyType.intValue()== EnergyTypeEnum.ENERGY_WATER.getEnergyType()){
                    //水
                    queryTypeGroup = waterClassificationGroup;
                }else if(energyType.intValue()== EnergyTypeEnum.ENERGY_ELECTRIC.getEnergyType()){
                    //电
                    queryTypeGroup = electricClassificationGroup;
                }
                break;
        }
        return queryTypeGroup;
    }
}