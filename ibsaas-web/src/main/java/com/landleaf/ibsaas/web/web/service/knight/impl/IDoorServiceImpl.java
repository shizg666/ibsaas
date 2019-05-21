package com.landleaf.ibsaas.web.web.service.knight.impl;

import com.landleaf.ibsaas.common.dao.knight.TBuildingMapper;
import com.landleaf.ibsaas.common.dao.knight.TDoorMapper;
import com.landleaf.ibsaas.common.domain.knight.TBuilding;
import com.landleaf.ibsaas.common.domain.knight.TDoor;
import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.web.web.service.knight.IDoorService;
import org.springframework.beans.factory.annotation.Autowired;

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
    public TDoor addDoor(TDoor tDoor) {
        tDoorMapper.insert(tDoor);
        return tDoor;
    }

    public TDoor updateDoor(TDoor tDoor) {
        tDoorMapper.updateByPrimaryKeySelective(tDoor);
        return tDoor;
    }
}
