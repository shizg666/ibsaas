package com.landleaf.ibsaas.web.web.util;

import cn.hutool.core.util.ReflectUtil;
import com.landleaf.ibsaas.common.domain.hvac.vo.HvacFieldVO;
import com.landleaf.ibsaas.common.enums.hvac.BacnetObjectEnum;
import com.serotonin.bacnet4j.type.enumerated.PropertyIdentifier;
import com.serotonin.bacnet4j.type.primitive.ObjectIdentifier;
import com.serotonin.bacnet4j.util.PropertyValues;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/5/28 16:05
 * @description: hvac模块通用类
 */
@Slf4j
public class HvacUtil {

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
}
