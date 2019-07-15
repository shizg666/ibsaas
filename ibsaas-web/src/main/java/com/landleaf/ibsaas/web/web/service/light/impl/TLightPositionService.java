package com.landleaf.ibsaas.web.web.service.light.impl;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.dao.light.*;
import com.landleaf.ibsaas.common.domain.light.*;
import com.landleaf.ibsaas.common.domain.light.vo.LightProductAttributeVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightPositionVO;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.service.light.ITLightPositionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TLightPositionService extends AbstractBaseService<TLightPositionDao, TLightPosition> implements ITLightPositionService<TLightPosition> {

    @Autowired
    private TLightAttributeDao tLightAttributeDao;
    @Autowired
    private TLightProductDao tLightProductDao;
    @Autowired
    private TLightDeviceDao tLightDeviceDao;


    @Override
    public TLightPosition addOrUpdatePosition(TLightPositionVO requestBody) {
        TLightPosition tLightPosition;
        if (requestBody.getId() == null || requestBody.getId() == 0L){
            tLightPosition = addDevice(requestBody);
        }else {
            tLightPosition = updateDevice(requestBody);
        }
        return tLightPosition;
    }

    @Override
    public Integer deletePosition(Long id) {
        Integer result = deleteByPrimaryKey(id);
        deleteByPrimaryKey(id);
        return result;
    }

    @Override
    public List<TLightPositionVO> getPositionAtrributeListByFloor(Long id) {
        Example example = new Example(TLightPosition.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("floorId =", id);
        List<TLightPosition> tLightPositions = selectByExample(example);
        List<TLightPositionVO> tlightPositionVOS = Lists.newArrayList();
        List<Long> deviceIds = Lists.newArrayList();
        tLightPositions.forEach(o->{
            TLightPositionVO tLightPositionVO = new TLightPositionVO();
            BeanUtils.copyProperties(tLightPositionVO,o);
            tlightPositionVOS.add(tLightPositionVO);
            deviceIds.add(o.getDeviceId());
        });

        List<TLightDevice> tLightDevices = tLightDeviceDao.getDeviceProductIdsByIds(deviceIds);
        List<Long> productIds = tLightDevices.stream().map(TLightDevice::getProductId).collect(Collectors.toList());
        Map<Long, Long> idMap = tLightDevices.stream().collect(Collectors.toMap(TLightDevice::getId, TLightDevice::getProductId));

        List<LightProductAttributeVO> tLightAttributes =  tLightAttributeDao.getAttributeListByProductIds(productIds);
        Map<Long, List<LightProductAttributeVO>> data = tLightAttributes.stream().collect(Collectors.groupingBy(LightProductAttributeVO::getProductId));
        tlightPositionVOS.forEach(obj ->{
            Long productId = idMap.get(obj.getDeviceId());
            obj.setList(data.get(productId));
        });
        return tlightPositionVOS;
    }

    private TLightPosition updateDevice(TLightPositionVO requestBody) {
        TLightPosition tLightPosition = new TLightPosition();
        BeanUtils.copyProperties(requestBody,tLightPosition);
        Integer result = updateByPrimaryKeySelective(tLightPosition);
        if (result < 0 ){
            throw new BusinessException("灯光设备位置修改失败");
        }
        return tLightPosition;
    }

    private TLightPosition addDevice(TLightPositionVO requestBody)  {
        TLightPosition tLightPosition = new TLightPosition();
        BeanUtils.copyProperties(requestBody,tLightPosition);
        Date date = new Date();
        tLightPosition.setCtime(date);
        tLightPosition.setUtime(date);
        Integer result = saveSelective(tLightPosition);
        if (result < 0 ){
            throw new BusinessException("灯光设备位置添加失败");
        }
      return tLightPosition;
    }

}
