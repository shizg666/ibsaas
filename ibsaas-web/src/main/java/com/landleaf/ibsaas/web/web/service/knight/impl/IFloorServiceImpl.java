package com.landleaf.ibsaas.web.web.service.knight.impl;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.dao.knight.TDoorMapper;
import com.landleaf.ibsaas.common.dao.knight.TFloorMapper;
import com.landleaf.ibsaas.common.domain.knight.TDoor;
import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.web.web.service.knight.IFloorService;
import com.landleaf.ibsaas.web.web.vo.DoorReponseVO;
import com.landleaf.ibsaas.web.web.vo.FloorReponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IFloorServiceImpl implements IFloorService {

    @Autowired
    private TFloorMapper tFloorMapper;
    @Autowired
    private TDoorMapper tDoorMapper;


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
    public FloorReponseVO getFloorAllById(Long id) {
        FloorReponseVO floorReponseVO = new FloorReponseVO();
        TFloor tFloor  = tFloorMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(tFloor,floorReponseVO);
        List<TDoor> tDoors = tDoorMapper.selectParentId(tFloor.getId());
        List<DoorReponseVO> doorReponseVOS = Lists.newArrayList();
        tDoors.forEach(obj ->{
            DoorReponseVO doorReponseVO = new DoorReponseVO();
            BeanUtils.copyProperties(obj,doorReponseVO);
            doorReponseVOS.add(doorReponseVO);
        });
        floorReponseVO.setList(doorReponseVOS);
        return floorReponseVO;
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
