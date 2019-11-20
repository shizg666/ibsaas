package com.landleaf.ibsaas.web.web.service.buliding.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.landleaf.ibsaas.common.dao.knight.TBuildingMapper;
import com.landleaf.ibsaas.common.dao.knight.TFloorMapper;
import com.landleaf.ibsaas.common.domain.building.vo.BuildingCloneVO;
import com.landleaf.ibsaas.common.domain.building.vo.BuildingFloorVO;
import com.landleaf.ibsaas.common.domain.knight.TBuilding;
import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.common.enums.BusinessTypeEnum;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.web.web.service.buliding.IBuildingCommonService;
import com.landleaf.ibsaas.web.web.service.knight.IFloorService;
import com.landleaf.ibsaas.web.web.vo.BuildingReponseVO;
import com.landleaf.ibsaas.web.web.vo.FloorReponseVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BuildingCommonService implements IBuildingCommonService {

    @Value("${web.picUrl}")
    private String path;
    @Autowired
    private TBuildingMapper tBuildingMapper;
    @Autowired
    private TFloorMapper tFloorMapper;

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
    public void addBuildingOrUpdate(TBuilding tBuilding) {
        if (tBuilding.getId() == null || tBuilding.getId() == 0){
            this.addBuilding(tBuilding);
        }else {
            this.updateBuilding(tBuilding);
        }
    }

    public TBuilding addBuilding(TBuilding tBuilding) {
        TBuilding data = tBuildingMapper.getAllBuildingByTypeAndName(tBuilding.getType(),tBuilding.getName());
        if (data != null){
            throw new BusinessException(BusinessTypeEnum.getInstByType(tBuilding.getType()).getName()+"-楼栋名称已存在!");
        }
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

    @Override
    public List<BuildingReponseVO> getBuildingAllInfoByType(Integer type) {
        List<BuildingReponseVO> buildingReponseVOArrayList = Lists.newArrayList();
        List<TBuilding> tBuildingList =  tBuildingMapper.getAllBuildingByType(type);
        if (tBuildingList == null || tBuildingList.size() <= 0 ){
            return buildingReponseVOArrayList;
        }
        List<Long> buildingIds = Lists.newArrayList();
        tBuildingList.forEach(obj ->{
            BuildingReponseVO buildingReponseVO = new BuildingReponseVO();
            BeanUtils.copyProperties(obj,buildingReponseVO);
            buildingReponseVO.setKey("building_"+ String.valueOf(buildingReponseVO.getId()));
            buildingReponseVO.setTypeName(BusinessTypeEnum.getInstByType(type).getName());
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
            floorReponseVO.setImg(StringUtil.isBlank(obj.getImg())?"":path+obj.getImg());
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
    public Map<String, List<BuildingReponseVO>> getBuildingAllInfo() {
        List<BuildingReponseVO> buildingReponseVOArrayList = Lists.newArrayList();
        List<TBuilding> tBuildingList =  tBuildingMapper.getAllBuilding();
        if (tBuildingList == null || tBuildingList.size() <= 0 ){
            return Maps.newHashMapWithExpectedSize(1);
        }
        List<Long> buildingIds = Lists.newArrayList();
        tBuildingList.forEach(obj ->{
            BuildingReponseVO buildingReponseVO = new BuildingReponseVO();
            BeanUtils.copyProperties(obj,buildingReponseVO);
            buildingReponseVO.setKey("building_"+ String.valueOf(buildingReponseVO.getId()));
            buildingReponseVO.setTypeName(BusinessTypeEnum.getInstByType(obj.getType()).getName());
            buildingIds.add(obj.getId());
            buildingReponseVOArrayList.add(buildingReponseVO);
        });
        List<TFloor> tFloors = tFloorMapper.selectByParentIds(buildingIds);
//        if ( tFloors.size() == 0 ){
//            return buildingReponseVOArrayList;
//        }
        List<Long> floorIds = Lists.newArrayList();
        List<FloorReponseVO> floorReponseVOS = Lists.newArrayList();
        tFloors.forEach(obj ->{
            FloorReponseVO floorReponseVO = new FloorReponseVO();
            BeanUtils.copyProperties(obj,floorReponseVO);
            floorReponseVO.setKey("floor_"+ String.valueOf(floorReponseVO.getId()));
            floorIds.add(obj.getId());
            floorReponseVOS.add(floorReponseVO);
        });
        Map<Long,List<FloorReponseVO>> dataMapArea = floorReponseVOS.stream().collect(Collectors.groupingBy(FloorReponseVO::getParentId));
        buildingReponseVOArrayList.forEach(obj -> {
            obj.setList(dataMapArea.get(obj.getId()));
        });
        Map<String, List<BuildingReponseVO>> data = buildingReponseVOArrayList.stream().collect(Collectors.groupingBy(BuildingReponseVO::getTypeName));
        return data;
    }

    @Override
    public List<TBuilding> getBuildingList() {
        return null;
    }

    @Override
    @Transactional
    public List<BuildingReponseVO> cloneBuilding(BuildingCloneVO buildingCloneVO) {
        Integer type = buildingCloneVO.getType();
        List<BuildingFloorVO> buildingFloorVOS = buildingCloneVO.getBulidings();
        List<TBuilding> bulidings = Lists.newArrayList();
        List<TFloor> tFloors = Lists.newArrayList();
        buildingFloorVOS.forEach(obj->{
            TBuilding building = tBuildingMapper.selectByPrimaryKey(obj.getId());
            TBuilding data = tBuildingMapper.getAllBuildingByTypeAndName(type,building.getName());
            Long buildingId;
            if (data == null){
                building.setType(type);
//                bulidings.add(building);
                tBuildingMapper.insert(building);
                buildingId = building.getId();
            }else {
                buildingId = data.getId();
            }
            if (CollectionUtils.isNotEmpty(obj.getFloorIds())){
                List<Long> floorIds = obj.getFloorIds();
                List<TFloor> tFloors1 = tFloorMapper.selectByPrimaryKeys(floorIds);
                if (data == null){
                    tFloors1.forEach(oo -> {
                        oo.setParentId(buildingId);
                    });
                    tFloors.addAll(tFloors1);
                }else {
                    List<TFloor> tFloors2 = tFloorMapper.selectByParentId(obj.getId());
                    Map<Integer, List<TFloor>> mapNew = tFloors1.stream().collect(Collectors.groupingBy(TFloor::getFloor));
                    Map<Integer, List<TFloor>> mapOld = tFloors2.stream().collect(Collectors.groupingBy(TFloor::getFloor));
                    Set<Integer> addSet = Sets.newHashSet();
                    Set<Integer> setNew  =  mapNew.keySet();
                    Set<Integer> setOld  =  mapOld.keySet();
                    addSet.addAll(setNew);
                    addSet.addAll(setOld);
                    setNew.forEach(floor ->{
                        List<TFloor> floors1= mapNew.get(floor);
                        List<TFloor> floors2 = mapOld.get(floor);
                        //原来存在的楼层直接忽略
                        if ( CollectionUtils.isNotEmpty(floors1) && CollectionUtils.isEmpty(floors2) ){
                            TFloor tFloor = floors1.get(0);
                            tFloor.setParentId(buildingId);
                            tFloors.add(tFloor);
                        }
                    });
                }
            }
        });
        if (CollectionUtils.isNotEmpty(tFloors)){
            tFloorMapper.insertBatch(tFloors);
        }
        List<BuildingReponseVO> buildingReponseVOS = getBuildingAllInfoByType(type);
        return  buildingReponseVOS;
    }
}
