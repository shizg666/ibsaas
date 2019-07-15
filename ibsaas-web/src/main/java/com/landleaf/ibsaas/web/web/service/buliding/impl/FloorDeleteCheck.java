package com.landleaf.ibsaas.web.web.service.buliding.impl;

import com.landleaf.ibsaas.common.dao.knight.TDoorMapper;
import com.landleaf.ibsaas.common.domain.knight.TDoor;
import com.landleaf.ibsaas.common.enums.BusinessTypeEnum;
import com.landleaf.ibsaas.common.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FloorDeleteCheck {

    @Autowired
    private TDoorMapper tDoorMapper;

    public void check(Long id ,Integer type){
      if (BusinessTypeEnum.BUSINESS_KNIGHT.getType() == type){
          checkKnight(id);
      }else if (BusinessTypeEnum.BUSINESS_Light.getType() == type){
          checkLight(id);
      }
    }

    private void checkKnight(Long id) {
        List<TDoor> tDoors = tDoorMapper.selectByParentId(id);
        if (CollectionUtils.isNotEmpty(tDoors)){
            throw new BusinessException("请先删除其下的门信息");
        }
    }

    private void checkLight(Long id) {

    }

}
