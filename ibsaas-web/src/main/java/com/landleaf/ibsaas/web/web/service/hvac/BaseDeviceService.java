package com.landleaf.ibsaas.web.web.service.hvac;

import cn.hutool.core.util.ReflectUtil;
import com.landleaf.ibsaas.common.dao.hvac.HvacNodeDao;
import com.landleaf.ibsaas.common.domain.hvac.BaseDevice;
import com.landleaf.ibsaas.common.domain.hvac.vo.HvacNodeFieldVO;
import com.landleaf.ibsaas.common.enums.hvac.BacnetPremissionEnum;
import com.landleaf.ibsaas.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

/**
 * @author Lokiy
 * @date 2019/6/4 17:14
 * @description:
 */
@Slf4j
@Service
public class BaseDeviceService {

    @Autowired
    private HvacNodeDao hvacNodeDao;

    /**
     * 根据传入的修改值 判定是否具有写权限
     */
    protected <T extends BaseDevice> void checkWritePermission(T t){
        writeDevice(t);
    }


    /**
     * 查看所需更改的字段
     * @param t
     * @param <T>
     */
    private <T extends BaseDevice> void writeDevice(T t){
        //主键id
        String id = t.getId();
        Field[] fields = ReflectUtil.getFields(t.getClass());
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String value = (String) field.get(t);
                if(value != null && !"".equals(value)){
                    //不为空
                    String name = field.getName();
                    if("id".equals(field.getName())){
                        continue;
                    }
                    boolean b = checkPermission(id, name);
                    if(!b){
                        throw new BusinessException("该属性不含有更改权限！");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                log.error("反射对象取值时,发生错误");
            }
        }
    }

    /**
     * 判断修改项是否具有更改权限
     * @param id
     * @param fieldName
     * @return
     */
    private boolean checkPermission(String id, String fieldName){
        HvacNodeFieldVO hvacNodeFieldVO = hvacNodeDao.getHvacNodeFieldVO(id, fieldName);
        if(BacnetPremissionEnum.READ_AND_WRITE.getPermission().equals(hvacNodeFieldVO.getPermission())){
            return true;
        }
        return false;
    }

}
