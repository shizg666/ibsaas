package com.landleaf.ibsaas.web.web.service.knight.impl;

import com.landleaf.ibsaas.common.dao.knight.TFloorMapper;
import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.web.web.service.knight.IFloorService;
import com.landleaf.ibsaas.web.web.vo.DoorReponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IFloorServiceImpl implements IFloorService {

    @Autowired
    private TFloorMapper tFloorMapper;


    @Override
    public TFloor addFloorOrUpdate(TFloor tFloor) {
        if (tFloor.getId() == null || tFloor.getId() == 0){
            this.addFloor(tFloor);
        }else {
            this.updateFloor(tFloor);
        }
        return tFloor;
    }

    @Override
    public List<DoorReponseVO> getByFloorId(Long id) {
        tFloorMapper.selectByParentId(id);
        return null;
    }

    public TFloor addFloor(TFloor tFloor) {
       tFloorMapper.insert(tFloor);
        return tFloor;
    }

    public TFloor updateFloor(TFloor tFloor) {
        tFloorMapper.updateByPrimaryKeySelective(tFloor);
        return tFloor;
    }

}
