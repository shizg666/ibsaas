package com.landleaf.ibsaas.client.hvac.util;

import cn.hutool.core.util.ReflectUtil;
import com.landleaf.ibsaas.common.domain.hvac.BaseDevice;
import com.landleaf.ibsaas.common.domain.hvac.assist.HvacPointDetail;
import com.landleaf.ibsaas.common.domain.hvac.assist.MbRegisterDetail;
import com.landleaf.ibsaas.common.domain.hvac.vo.HvacFieldVO;
import com.landleaf.ibsaas.common.enums.hvac.BacnetObjectEnum;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.serotonin.bacnet4j.type.enumerated.ObjectType;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.util.PropertyValues;
import com.serotonin.modbus4j.BatchResults;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/5/28 16:05
 * @description: hvac模块通用类
 */
@Slf4j
public class HvacUtil {

    private static final String POINT = ".";

    /**
     * 根据抽取到的点位数据 给相应DTO转反射赋值
     * @param values
     * @param hvacFieldVOList
     * @param target
     */
    public static void assignmentByClass(PropertyValues values, List<HvacFieldVO> hvacFieldVOList, Object target){
        Field[] fields = ReflectUtil.getFields(target.getClass());
        hvacFieldVOList.forEach(hf -> {
            for (Field field : fields) {
                if(field.getName().equals(hf.getFieldName())){
                    String value = BacnetUtil.getState(values.getString(
                            new ObjectIdentifier(
                                    BacnetObjectEnum.OBJECT_TYPE_MAP.get(hf.getBacnetObjectType()),
                                    hf.getInstanceNumber())
                            , PropertyIdentifier.presentValue));
                    field.setAccessible(true);
                    try {
                        field.set(target, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        log.error("反射对象赋值时,发生错误");
                    }

                }
            }
        });
    }



    /**
     * 根据抽取到的点位数据 给相应DTO转反射赋值
     * @param values
     * @param hvacPointDetails
     * @param target
     */
    public static void assignmentByClassBacnet(Map<String, PropertyValues> values, List<HvacPointDetail> hvacPointDetails, Object target){
        Field[] fields = ReflectUtil.getFields(target.getClass());
        hvacPointDetails.forEach(hpd -> {
            for (Field field : fields) {
                if(field.getName().equals(hpd.getFieldName())){
                    ObjectType objectType = BacnetObjectEnum.OBJECT_TYPE_MAP.get(hpd.getBacnetObjectType());
                    String value = null;
                    if(values.get(hpd.getDeviceId()) != null) {
                        value = BacnetUtil.getState(values.get(hpd.getDeviceId()).getString(
                                new ObjectIdentifier(
                                        objectType,
                                        hpd.getInstanceNumber())
                                , PropertyIdentifier.presentValue));
                    }
                    field.setAccessible(true);
                    value = dealValue(value, objectType);
                    try {
                        field.set(target, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        log.error("反射对象赋值时,发生错误");
                    }

                }
            }
        });
    }

    /**
     * 小数点位数处理
     * @param value
     * @param objectType
     * @return
     */
    private static String dealValue(String value, ObjectType objectType){
        if(StringUtils.isBlank(value)){
            return value;
        }
        if("NaN".equals(value)){
            return null;
        }
        //数值形式
        if(ObjectType.analogInput.equals(objectType)
                || ObjectType.analogOutput.equals(objectType)
                || ObjectType.analogValue.equals(objectType)){
            //小数点后面两位
            if( value.indexOf(POINT)>0
                    && value.substring(value.indexOf(POINT) + 1).length() > 2) {
                BigDecimal tempBd = new BigDecimal(value).setScale(2, BigDecimal.ROUND_CEILING);
                return tempBd.toString();
            }
        }
        return value;
    }

    /**
     * modbus封装对象
     * @param results
     * @param mbRegisterDetails
     * @param target
     */
    public static void assignmentByClassModbus(Map<String, BatchResults<String>> results, List<MbRegisterDetail> mbRegisterDetails, Object target){
        Field[] fields = ReflectUtil.getFields(target.getClass());
        mbRegisterDetails.forEach(mr -> {
            for (Field field : fields) {
                if(field.getName().equals(mr.getFieldName())){
                    Object value = null;
                    if(results.get(mr.getMasterId()) != null) {
                        value = results.get(mr.getMasterId()).getValue(mr.getRegisterId());
                    }
                    field.setAccessible(true);
                    String oriVal = dealModbusValue(mr.getFieldName(), value);
                    String v = multiplyCoefficient(oriVal, mr.getCoefficient());
                    try {
                        field.set(target, v);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        log.error("反射对象赋值时,发生错误");
                    }

                }
            }
        });
    }

    /**
     * mb乘以返回系数
     * @param oriVal
     * @param coefficient
     * @return
     */
    private static String multiplyCoefficient(String oriVal, String coefficient){
        if (StringUtils.isBlank(oriVal)) {
            return null;
        }

        if(StringUtil.isBlank(coefficient) || "1".equals(coefficient)){
            //无系数或系数为1时 直接返回
            return oriVal;
        }
        BigDecimal ori = new BigDecimal(oriVal);
        BigDecimal multiply = ori.multiply(new BigDecimal(coefficient));
        return multiply.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    private static String dealModbusValue(String fieldName, Object o){
        String temp = modbusSpecialValue(fieldName, o);
        if(temp != null){
            return temp;
        }
        if(null == o){
            return null;
        }
        return String.valueOf(o);
    }

    private static String modbusSpecialValue(String fieldName, Object o){
        if("ssHcho".equals(fieldName)){
            if(o == null){
                return "0.01";
            }
            String result = String.valueOf(o);
            //modbus 甲醛数值为100倍
            return new BigDecimal(result).divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP).toString();
        }
        return null;
    }

}
