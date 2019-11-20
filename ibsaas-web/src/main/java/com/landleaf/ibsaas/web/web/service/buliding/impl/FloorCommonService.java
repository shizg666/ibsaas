package com.landleaf.ibsaas.web.web.service.buliding.impl;

import com.landleaf.ibsaas.common.dao.knight.TBuildingMapper;
import com.landleaf.ibsaas.common.dao.knight.TFloorMapper;
import com.landleaf.ibsaas.common.domain.knight.TBuilding;
import com.landleaf.ibsaas.common.domain.knight.TDoor;
import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.common.enums.BusinessTypeEnum;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.web.web.service.buliding.IFloorCommonService;
import com.landleaf.ibsaas.web.web.service.knight.IFloorService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FloorCommonService implements IFloorCommonService {

    @Value("${web.picUrl}")
    private String path;
    @Autowired
    private TBuildingMapper tBuildingMapper;
    @Autowired
    private TFloorMapper tFloorMapper;
    @Autowired
    private IFloorService iFloorService;
    @Autowired
    private FloorDeleteCheck floorDeleteCheck;


    @Override
    public void addFloorOrUpdate(TFloor tFloor) {
        if (tFloor.getId() == null || tFloor.getId() == 0){
            this.addFloor(tFloor);
        }else {
            this.updateFloor(tFloor);
        }
    }

    @Override
    public TFloor getFloorById(Long id) {
        TFloor floor = tFloorMapper.selectByPrimaryKey(id);
        if (floor == null){
            return new TFloor();
        }
        return floor;
    }

    public TFloor addFloor(TFloor tFloor) {
        TFloor tFloor1 = tFloorMapper.selectByFloor(tFloor.getFloor(),tFloor.getParentId());
        if (tFloor1 != null){
            throw new BusinessException("楼层已存在不可重复!");
        }
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


    @Override
    public Integer deleteFloor(Long id) {
        TFloor tFloor = tFloorMapper.selectByPrimaryKey(id);
        if (tFloor == null){
            throw new BusinessException("根据id查询不到楼层信息");
        }
        TBuilding building = tBuildingMapper.selectByPrimaryKey(tFloor.getParentId());
        if (building == null){
            throw new BusinessException("根据id查询不到楼栋信息");
        }
        floorDeleteCheck.check(id,building.getType());
        Integer result = tFloorMapper.deleteByPrimaryKey(id);
        if (result < 0 ){
            throw new BusinessException("楼层删除失败");
        }
        return result;
    }

//    @Override
//    public Integer deleteFloor(Long id) {
//        Integer result = tFloorMapper.deleteByPrimaryKey(id);
//        if (result < 0 ){
//            throw new BusinessException("楼层删除失败");
//        }
//        return result;
//    }


    @Override
    public List<TFloor> getFloorListByParentId(Long buildingId) {
        List<TFloor> tFloors = tFloorMapper.selectByParentId(buildingId);
        return tFloors;
    }
}
