package com.landleaf.ibsaas.web.web.service.knight.impl;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.dao.knight.TDoorMapper;
import com.landleaf.ibsaas.common.dao.knight.TFloorMapper;
import com.landleaf.ibsaas.common.domain.knight.TDoor;
import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.common.domain.knight.role.MjRoleResource;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.web.web.service.knight.IFloorService;
import com.landleaf.ibsaas.web.web.service.knight.IMjRoleResourceService;
import com.landleaf.ibsaas.web.web.vo.DoorReponseVO;
import com.landleaf.ibsaas.web.web.vo.FloorReponseVO;
import com.landleaf.ibsaas.web.web.vo.RoleDoorsReponseVO;
import com.landleaf.ibsaas.web.web.vo.RoleFloorDoorsReponseVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class IFloorServiceImpl implements IFloorService {

    @Autowired
    private TFloorMapper tFloorMapper;
    @Autowired
    private TDoorMapper tDoorMapper;
    @Autowired
    private IMjRoleResourceService mjRoleResourceService;

    @Value("${web.picUrl}")
    private String path;


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
        tFloor.setImg(StringUtil.isBlank(tFloor.getImg())?"":path+tFloor.getImg());
        BeanUtils.copyProperties(tFloor,floorReponseVO);
        List<TDoor> tDoors = tDoorMapper.selectByParentId(tFloor.getId());
        List<DoorReponseVO> doorReponseVOS = Lists.newArrayList();
        tDoors.forEach(obj ->{
            DoorReponseVO doorReponseVO = new DoorReponseVO();
            BeanUtils.copyProperties(obj,doorReponseVO);
            doorReponseVOS.add(doorReponseVO);
        });
        floorReponseVO.setList(doorReponseVOS);
        return floorReponseVO;
    }

    @Override
    public Integer deleteFloor(Long id) {
        List<TDoor> tDoors = tDoorMapper.selectByParentId(id);
        if (CollectionUtils.isNotEmpty(tDoors)){
            throw new BusinessException("请先删除其下的门信息");
        }
        Integer result = tFloorMapper.deleteByPrimaryKey(id);
        if (result < 0 ){
            throw new BusinessException("楼层删除失败");
        }
        return result;
    }

    @Override
    public List<TFloor> getFloorListByParentId(Long buildingId) {
        List<TFloor> tFloors = tFloorMapper.selectByParentId(buildingId);
        return tFloors;
    }

    @Override
    public RoleFloorDoorsReponseVO getfloorControlDoorByRoleId(Long floorId, String roleId) {
        RoleFloorDoorsReponseVO roleFloorDoorsReponseVO = new RoleFloorDoorsReponseVO();
        List<MjRoleResource> mjRoleResourceList = mjRoleResourceService.findRoleResourceByRoleId(roleId);
        List<Integer> doorIds=mjRoleResourceList.stream().map(obj -> obj.getMjDoorId()).collect(Collectors.toList());
        TFloor tFloor = tFloorMapper.selectByPrimaryKey(floorId);
        roleFloorDoorsReponseVO.setImg(StringUtil.isBlank(tFloor.getImg())?"":path+tFloor.getImg());
        roleFloorDoorsReponseVO.setRoleId(roleId);
        List<TDoor> tDoorList = tDoorMapper.selectControlDoorByParentId(floorId);
        List<RoleDoorsReponseVO> roleDoorsReponseVOS = new ArrayList<>(tDoorList.size());
        tDoorList.forEach(item -> {
            RoleDoorsReponseVO roleDoorsReponseVO = new RoleDoorsReponseVO();
            BeanUtils.copyProperties(item,roleDoorsReponseVO);
            if (!doorIds.contains(item.getControlId())){
                roleDoorsReponseVO.setAcessflag(false);
            }else {
                roleDoorsReponseVO.setAcessflag(true);
            }
            roleDoorsReponseVOS.add(roleDoorsReponseVO);
        });
        roleFloorDoorsReponseVO.setList(roleDoorsReponseVOS);
        return roleFloorDoorsReponseVO;
    }

    public TFloor addFloor(TFloor tFloor) {
        if (StringUtil.isNotEmpty(tFloor.getImg())){
            String imgUrl = tFloor.getImg();
            String[] s = imgUrl.split(path);
            if (s.length ==2){
                tFloor.setImg(s[1]);
            }
        }
        Integer result = tFloorMapper.insert(tFloor);
        if (result < 0 ){
            throw new BusinessException("楼层添加失败");
        }
        return tFloor;
    }

    public TFloor updateFloor(TFloor tFloor) {
        if (StringUtil.isNotEmpty(tFloor.getImg())){
            String imgUrl = tFloor.getImg();
            String[] s = imgUrl.split(path);
            if (s.length ==2){
                tFloor.setImg(s[1]);
            }
        }
        Integer result = tFloorMapper.updateByPrimaryKeySelective(tFloor);
        if (result < 0 ){
            throw new BusinessException("楼层修改失败");
        }
        return tFloor;
    }

}
