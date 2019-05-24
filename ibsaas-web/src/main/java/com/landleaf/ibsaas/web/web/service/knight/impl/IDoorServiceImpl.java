package com.landleaf.ibsaas.web.web.service.knight.impl;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.dao.knight.TBuildingMapper;
import com.landleaf.ibsaas.common.dao.knight.TDoorMapper;
import com.landleaf.ibsaas.common.dao.knight.TFloorMapper;
import com.landleaf.ibsaas.common.domain.knight.TBuilding;
import com.landleaf.ibsaas.common.domain.knight.TDoor;
import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.web.web.service.knight.IDoorService;
import com.landleaf.ibsaas.web.web.vo.BuildingReponseVO;
import com.landleaf.ibsaas.web.web.vo.DoorReponseVO;
import com.landleaf.ibsaas.web.web.vo.FloorReponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IDoorServiceImpl implements IDoorService {
    @Autowired
    private TDoorMapper tDoorMapper;
    @Autowired
    private TFloorMapper tFloorMapper;
    @Autowired
    private TBuildingMapper tBuildingMapper;
    @Value("${web.picUrl}")
    private String path;


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

    @Override
    @Transactional
    public Integer bindingDoorControl(Long id, Long controId) {
        TDoor tDoor = tDoorMapper.selectByContrloId(controId);
        if (tDoor != null){
//            throw new BusinessException("跟门禁已绑定");
            //把之前绑定的清除
            tDoorMapper.bindingDoorControl(tDoor.getId(), 0L);
        }
        Integer result = tDoorMapper.bindingDoorControl(id, controId);
        if (result < 0 ){
            throw new BusinessException("门禁绑定失败");
        }
        return result;
    }

    @Override
    public Integer deleteDoor(Long id) {
        Integer result = tDoorMapper.deleteByPrimaryKey(id);
        if (result < 0 ){
            throw new BusinessException("门删除失败");
        }
        return result;
    }

    @Override
    public BuildingReponseVO getDoorAllInfobyControlId(Long controlId) {
        BuildingReponseVO buildingReponseVO = new BuildingReponseVO();
        FloorReponseVO floorReponseVO = new FloorReponseVO();
        DoorReponseVO doorReponseVO = new DoorReponseVO();
        TDoor tDoor = tDoorMapper.selectByContrloId(controlId);
        if (tDoor == null){
            throw new BusinessException("查询不到门信息！");
        }
        BeanUtils.copyProperties(tDoor,doorReponseVO);
        List<DoorReponseVO> doorReponseVOS = Lists.newArrayList();
        doorReponseVOS.add(doorReponseVO);
        floorReponseVO.setList(doorReponseVOS);
        TFloor tFloor = tFloorMapper.selectByPrimaryKey(tDoor.getParentId());
        if (tFloor == null){
            throw new BusinessException("查询不到楼栋信息！");
        }
        tFloor.setImg(StringUtil.isBlank(tFloor.getImg())?"":path+tFloor.getImg());
        BeanUtils.copyProperties(tFloor,floorReponseVO);
        List<FloorReponseVO> floorReponseVOS = Lists.newArrayList();
        floorReponseVOS.add(floorReponseVO);
        buildingReponseVO.setList(floorReponseVOS);
        TBuilding tBuilding = tBuildingMapper.selectByPrimaryKey(tFloor.getParentId());
        if (tBuilding == null){
            throw new BusinessException("查询不到楼栋信息！");
        }
        BeanUtils.copyProperties(tBuilding,buildingReponseVO);
        return buildingReponseVO;
    }

    @Override
    public List<TDoor> getDoorControlList() {
        List<TDoor> tDoors = tDoorMapper.getDoorListOrderByfloor();
        return tDoors;
    }

    @Override
    public List<TDoor> getDoorInfoByControlIds(List<Long> controlIds) {
        List<TDoor> tDoors = tDoorMapper.getDoorInfoByControlIds(controlIds);
        return tDoors;
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
