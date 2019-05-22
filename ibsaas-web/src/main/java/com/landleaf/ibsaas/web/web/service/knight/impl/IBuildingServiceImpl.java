package com.landleaf.ibsaas.web.web.service.knight.impl;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.dao.knight.TBuildingMapper;
import com.landleaf.ibsaas.common.dao.knight.TDoorMapper;
import com.landleaf.ibsaas.common.dao.knight.TFloorMapper;
import com.landleaf.ibsaas.common.domain.knight.TBuilding;
import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.web.web.service.knight.IBuildingService;
import com.landleaf.ibsaas.web.web.vo.BuildingReponseVO;
import com.landleaf.ibsaas.web.web.vo.FloorReponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IBuildingServiceImpl implements IBuildingService {

    @Autowired
    private TBuildingMapper tBuildingMapper;
    @Autowired
    private TFloorMapper tFloorMapper;
    @Autowired
    private TDoorMapper tDoorMapper;


    @Override
    public TBuilding addBuildingOrUpdate(TBuilding tBuilding) {
        if (tBuilding.getId() == null || tBuilding.getId() == 0){
            this.addBuilding(tBuilding);
        }else {
            this.updateBuilding(tBuilding);
        }
        return tBuilding;
    }

    @Override
    public List<BuildingReponseVO> getBuildingAllInfo() {
        List<BuildingReponseVO> buildingReponseVOArrayList = Lists.newArrayList();
        List<TBuilding> tBuildingList =  tBuildingMapper.getAllBuilding();
        if (tBuildingList == null || tBuildingList.size() <= 0 ){
            return buildingReponseVOArrayList;
    }
        List<Long> buildingIds = Lists.newArrayList();
        tBuildingList.forEach(obj ->{
            BuildingReponseVO buildingReponseVO = new BuildingReponseVO();
            BeanUtils.copyProperties(obj,buildingReponseVO);
            buildingIds.add(obj.getId());
            buildingReponseVOArrayList.add(buildingReponseVO);
        });
        List<TFloor> tFloors = tFloorMapper.selectByParentIds(buildingIds);
        if ( tFloors.size() == 0 ){
            return buildingReponseVOArrayList;
        }
        List<Long> floorIds = Lists.newArrayList();
        List<FloorReponseVO> floorReponseVOS = Lists.newArrayList();
        tFloors.forEach(obj ->{
            FloorReponseVO floorReponseVO = new FloorReponseVO();
            BeanUtils.copyProperties(obj,floorReponseVO);
            floorIds.add(obj.getId());
            floorReponseVOS.add(floorReponseVO);
        });
        Map<Long,List<FloorReponseVO>> dataMapArea=floorReponseVOS.stream().collect(Collectors.groupingBy(FloorReponseVO::getParentId));
        buildingReponseVOArrayList.forEach(obj->{
            obj.setList(dataMapArea.get(obj.getId()));
        });
        return buildingReponseVOArrayList;
    }

    public TBuilding addBuilding(TBuilding tBuilding) {
        tBuildingMapper.insert(tBuilding);
        return tBuilding;
    }

    public TBuilding updateBuilding(TBuilding tBuilding) {
        tBuildingMapper.updateByPrimaryKeySelective(tBuilding);
        return tBuilding;
    }
}
