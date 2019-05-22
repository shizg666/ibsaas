package com.landleaf.ibsaas.web.web.service.knight.impl;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.dao.knight.TDoorMapper;
import com.landleaf.ibsaas.common.domain.knight.TDoor;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.web.web.service.knight.IDoorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IDoorServiceImpl implements IDoorService {
    @Autowired
    private TDoorMapper tDoorMapper;
    @Override
    public TDoor addDoorOrUpdate(TDoor tDoor) {
        if (tDoor.getId() == null || tDoor.getId() == 0){
            this.addDoor(tDoor);
        }else {
            this.updateDoor(tDoor);
        }
        return tDoor;
    }

    @Override
    @Transactional
    public List<TDoor> bacthAddOrUpdateFloor(List<TDoor> tDoors) {
        List<TDoor> resultData = Lists.newArrayList();
        tDoors.forEach(obj ->{
            TDoor tDoor = addDoorOrUpdate(obj);
            resultData.add(tDoor);
        });
        return resultData;
    }

    public TDoor addDoor(TDoor tDoor) {

        TDoor tDoor1 = tDoorMapper.selectByName(tDoor.getName());
        if (tDoor1 != null ){
            throw new BusinessException("门名称已存在！");
        }
        Integer result = tDoorMapper.insert(tDoor);
        if (result < 0 ){
            throw new BusinessException("添加门信息失败");
        }
        return tDoor;
    }

    public TDoor updateDoor(TDoor tDoor) {
        Integer result = tDoorMapper.updateByPrimaryKeySelective(tDoor);
        if (result < 0 ){
            throw new BusinessException("修改门信息失败");
        }
        return tDoor;
    }
}
