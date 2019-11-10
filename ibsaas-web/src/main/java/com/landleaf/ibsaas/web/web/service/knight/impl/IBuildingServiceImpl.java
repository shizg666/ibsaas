package com.landleaf.ibsaas.web.web.service.knight.impl;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.dao.knight.TBuildingMapper;
import com.landleaf.ibsaas.common.dao.knight.TFloorMapper;
import com.landleaf.ibsaas.common.domain.knight.TBuilding;
import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.common.enums.BusinessTypeEnum;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.web.web.service.knight.IBuildingService;
import com.landleaf.ibsaas.web.web.service.knight.IFloorService;
import com.landleaf.ibsaas.web.web.vo.BuildingReponseVO;
import com.landleaf.ibsaas.web.web.vo.FloorReponseVO;
import com.landleaf.ibsaas.web.web.vo.RoleFloorDoorsReponseVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IBuildingServiceImpl implements IBuildingService {

    @Value("${web.picUrl}")
    private String path;
    @Autowired
    private TBuildingMapper tBuildingMapper;
    @Autowired
    private TFloorMapper tFloorMapper;
    @Autowired
    private IFloorService iFloorService;



    @Override
    public Integer deleteBuilding(Long id) {
        List<TFloor> tFloors = tFloorMapper.selectByParentId(id);
        if (CollectionUtils.isNotEmpty(tFloors)){
            throw new BusinessException("请先删除其下的楼层信息");
        }
        Integer result = tBuildingMapper.deleteByPrimaryKey(id);
        if (result < 0 ){
            throw new BusinessException("楼栋删除失败");
        }
        return result;
    }

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
        List<TBuilding> tBuildingList =  tBuildingMapper.getAllBuildingByType(BusinessTypeEnum.BUSINESS_KNIGHT.getType());
        if (tBuildingList == null || tBuildingList.size() <= 0 ){
            return buildingReponseVOArrayList;
    }
        List<Long> buildingIds = Lists.newArrayList();
        tBuildingList.forEach(obj ->{
            BuildingReponseVO buildingReponseVO = new BuildingReponseVO();
            BeanUtils.copyProperties(obj,buildingReponseVO);
            buildingReponseVO.setKey("building_"+ String.valueOf(buildingReponseVO.getId()));
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
            floorReponseVO.setKey("floor_"+ String.valueOf(floorReponseVO.getId()));
            floorIds.add(obj.getId());
            floorReponseVOS.add(floorReponseVO);
        });
        Map<Long,List<FloorReponseVO>> dataMapArea=floorReponseVOS.stream().collect(Collectors.groupingBy(FloorReponseVO::getParentId));
        buildingReponseVOArrayList.forEach(obj->{
            obj.setList(dataMapArea.get(obj.getId()));
        });
        return buildingReponseVOArrayList;
    }

    @Override
    public List<BuildingReponseVO> getBuildingAllInfoByRoleId(String roleId) {
        List<BuildingReponseVO> buildingReponseVOArrayList = Lists.newArrayList();
        List<TBuilding> tBuildingList =  tBuildingMapper.getAllBuildingByType(BusinessTypeEnum.BUSINESS_KNIGHT.getType());
        if (tBuildingList == null || tBuildingList.size() <= 0 ){
            return buildingReponseVOArrayList;
        }
        List<Long> buildingIds = Lists.newArrayList();
        tBuildingList.forEach(obj ->{
            BuildingReponseVO buildingReponseVO = new BuildingReponseVO();
            BeanUtils.copyProperties(obj,buildingReponseVO);
            buildingReponseVO.setKey("building_"+ String.valueOf(buildingReponseVO.getId()));
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
            floorReponseVO.setKey("floor_"+ String.valueOf(floorReponseVO.getId()));
            floorReponseVO.setImg(StringUtil.isBlank(floorReponseVO.getImg())?"":path+floorReponseVO.getImg());
            floorIds.add(obj.getId());
            floorReponseVOS.add(floorReponseVO);
        });
        Map<Long,List<FloorReponseVO>> dataMapArea=floorReponseVOS.stream().collect(Collectors.groupingBy(FloorReponseVO::getParentId));

        for (Map.Entry<Long, List<FloorReponseVO>> entry : dataMapArea.entrySet()) {
//            RoleFloorDoorsReponseVO roleFloorDoorsReponseVO = new RoleFloorDoorsReponseVO();
            List<FloorReponseVO> floors = entry.getValue();
            floors.forEach(obj->{
                RoleFloorDoorsReponseVO roleFloorDoorsReponseVO = iFloorService.getfloorControlDoorByRoleId(obj.getId(),roleId);
                obj.setDataList(roleFloorDoorsReponseVO.getList());
            });
        }
        buildingReponseVOArrayList.forEach(obj->{
            obj.setList(dataMapArea.get(obj.getId()));
        });
        return buildingReponseVOArrayList;
    }

    @Override
    public List<TBuilding> getBuildingList() {
        List<TBuilding> buildings = tBuildingMapper.getAllBuildingByType(BusinessTypeEnum.BUSINESS_KNIGHT.getType());
        return buildings;
    }

    public TBuilding addBuilding(TBuilding tBuilding) {
        Integer result = tBuildingMapper.insert(tBuilding);
        if (result < 0 ){
            throw new BusinessException("楼栋添加失败");
        }
        return tBuilding;
    }

    public TBuilding updateBuilding(TBuilding tBuilding) {
        Integer result = tBuildingMapper.updateByPrimaryKeySelective(tBuilding);
        if (result < 0 ){
            throw new BusinessException("楼栋修改失败");
        }
        return tBuilding;
    }
}
