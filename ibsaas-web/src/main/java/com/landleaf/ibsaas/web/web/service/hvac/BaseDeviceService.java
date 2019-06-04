package com.landleaf.ibsaas.web.web.service.hvac;

import cn.hutool.core.util.ReflectUtil;
import com.landleaf.ibsaas.common.dao.hvac.HvacNodeDao;
import com.landleaf.ibsaas.common.domain.hvac.BaseDevice;
import com.landleaf.ibsaas.common.domain.hvac.vo.HvacNodeFieldVO;
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
    protected void checkWritePermission(){

    }





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

                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                log.error("反射对象取值时,发生错误");
            }
        }
    }


    private boolean checkPermission(String id, String fieldName){
        HvacNodeFieldVO hvacNodeFieldVO = hvacNodeDao.getHvacNodeFieldVO(id, fieldName);
        return false;
    }

}
