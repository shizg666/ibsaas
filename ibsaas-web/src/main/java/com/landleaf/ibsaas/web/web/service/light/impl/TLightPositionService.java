package com.landleaf.ibsaas.web.web.service.light.impl;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.constant.RedisConstants;
import com.landleaf.ibsaas.common.dao.light.TLightAttributeDao;
import com.landleaf.ibsaas.common.dao.light.TLightDeviceDao;
import com.landleaf.ibsaas.common.dao.light.TLightPositionDao;
import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.common.domain.light.TLightDevice;
import com.landleaf.ibsaas.common.domain.light.TLightPosition;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import com.landleaf.ibsaas.common.domain.light.vo.LightPositionDeviceVO;
import com.landleaf.ibsaas.common.domain.light.vo.LightProductAttributeVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightPositionRequestVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightPositionResponseVO;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.service.buliding.impl.FloorCommonService;
import com.landleaf.ibsaas.web.web.service.light.ILightService;
import com.landleaf.ibsaas.web.web.service.light.ITLightPositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TLightPositionService extends AbstractBaseService<TLightPositionDao, TLightPosition> implements ITLightPositionService<TLightPosition> {

    @Autowired
    private TLightAttributeDao tLightAttributeDao;
    @Autowired
    private TLightDeviceDao tLightDeviceDao;
    @Autowired
    private FloorCommonService floorCommonService;
    @Autowired
    private RedisHandle redisHandle;
    @Autowired
    private ILightService iLightService;


    @Override
    public TLightPosition addOrUpdatePosition(TLightPositionRequestVO requestBody) {
        TLightPosition tLightPosition;
        if (requestBody.getId() == null || requestBody.getId() == 0L){
            tLightPosition = addDevicePosition(requestBody);
        }else {
            tLightPosition = updateDevicePosition(requestBody);
        }
        return tLightPosition;
    }

    @Override
    public Integer deletePositionById(Long id) {
        TLightPosition tLightPosition = this.dao.selectByid(id);
        if (tLightPosition == null){
            throw new BusinessException("找不到该位置信息id：{}",id);
        }
        try{
            TLightDevice tLightDevice = tLightDeviceDao.selectByPrimaryKey(tLightPosition.getDeviceId());
            TFloor tFloor = floorCommonService.getFloorById(tLightPosition.getFloorId());
            LightMsg lightMsg = new LightMsg();
            lightMsg.setRegion(tLightDevice.getAdress());
            lightMsg.setFloor(String.valueOf(tFloor.getFloor()));
            lightMsg.setType("2");
            lightMsg.setValue("0");
            iLightService.controlLight(lightMsg);
        }catch (Exception e){
            log.error("删除设备位置时，发送取消该设备情景消息失败：{}",e.getMessage());
        }
        Integer result = this.dao.deleteByPrimaryId(id);
        return result;
    }

    @Override
    public Integer deletePositionByDeviceId(Long id) {
        Example example = new Example(TLightPosition.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deviceId", id);
        Integer result = deleteByExample(example);
        return result;
    }

    @Override
    public List<TLightPositionResponseVO> getPositionAtrributeListByFloor(Long id) {
        Example example = new Example(TLightPosition.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("floorId", id);
        List<TLightPosition> tLightPositions = selectByExample(example);
        List<TLightPositionResponseVO> tlightPositionVOS = Lists.newArrayList();
        if (CollectionUtils.isEmpty(tLightPositions)){
            return tlightPositionVOS;
        }
        List<Long> deviceIds = Lists.newArrayList();
        tLightPositions.forEach(o->{
            TLightPositionResponseVO tLightPositionVO = new TLightPositionResponseVO();
            BeanUtils.copyProperties(o,tLightPositionVO);
            tlightPositionVOS.add(tLightPositionVO);
            deviceIds.add(o.getDeviceId());
        });

        List<TLightDevice> tLightDevices = tLightDeviceDao.getDeviceByIds(deviceIds);
        if (CollectionUtils.isEmpty(tLightDevices)){
            return tlightPositionVOS;
        }

        List<Long> productIds = tLightDevices.stream().map(TLightDevice::getProductId).collect(Collectors.toList());
//        Map<Long, Long> idMap = tLightDevices.stream().collect(Collectors.toMap(TLightDevice::getId, TLightDevice::getProductId));
        Map<Long, TLightDevice> deviceMap = tLightDevices.stream().collect(Collectors.toMap(TLightDevice::getId, o->o));

        List<LightProductAttributeVO> tLightAttributes =  tLightAttributeDao.getAttributeListByProductIds(productIds);
        Map<Long, List<LightProductAttributeVO>> data = tLightAttributes.stream().collect(Collectors.groupingBy(LightProductAttributeVO::getProductId));

//        TFloor tFloor = floorCommonService.getFloorById(id);
//        String key = "";
//        if (tFloor.getFloor() == 3){
//            key =  RedisConstants.LIGHT_DEVICE_3F;
//        }else if (tFloor.getFloor() == 4){
//            key =  RedisConstants.LIGHT_DEVICE_3F;
//        }
//        Boolean exsitKey = StringUtil.isNotEmpty(key);
//        String finalKey = key;
        tlightPositionVOS.forEach(obj ->{
            Long deviceId = obj.getDeviceId();
            Long productId = deviceMap.get(deviceId).getProductId();
            obj.setList(data.get(productId));
//            if (exsitKey){
//                String scenes = redisHandle.getMapField(finalKey,"R_"+deviceMap.get(deviceId).getAdress());
//                obj.setState(scenes);
//            }
        });


        return tlightPositionVOS;
    }

    @Override
    public List<LightPositionDeviceVO> getUnPositionDeviceList() {
        List<LightPositionDeviceVO> voList = this.dao.getUnPositionDeviceList();
        return voList;
    }

    @Override
    public List<LightPositionDeviceVO> getPositionDeviceList(Long id) {
        List<LightPositionDeviceVO> voList = this.dao.getPositionDeviceList(id);
        return  voList;
    }

    private TLightPosition updateDevicePosition(TLightPositionRequestVO requestBody) {
        TLightPosition tLightPosition = new TLightPosition();
        BeanUtils.copyProperties(requestBody,tLightPosition);
        Integer result = updateByPrimaryKeySelective(tLightPosition);
        if (result < 0 ){
            throw new BusinessException("灯光设备位置修改失败");
        }
        return tLightPosition;
    }

    private TLightPosition addDevicePosition(TLightPositionRequestVO requestBody)  {
        TLightPosition tLightPosition = new TLightPosition();
        BeanUtils.copyProperties(requestBody,tLightPosition);
        Date date = new Date();
        tLightPosition.setCtime(date);
        tLightPosition.setUtime(date);
        Integer result = saveSelective(tLightPosition);
        if (result < 0 ){
            throw new BusinessException("灯光设备位置添加失败");
        }
        try{
            TLightDevice tLightDevice = tLightDeviceDao.selectByPrimaryKey(requestBody.getDeviceId());
            TFloor tFloor = floorCommonService.getFloorById(requestBody.getFloorId());
            //查询现有的状态
            LightMsg lightMsg = new LightMsg();
            lightMsg.setRegion(tLightDevice.getAdress());
            lightMsg.setAdress(tLightDevice.getAdress());
            lightMsg.setFloor(String.valueOf(tFloor.getFloor()));
            lightMsg.setType("3");
            iLightService.controlLight(lightMsg);
            //状态改变监听
            LightMsg lightMsg2 = new LightMsg();
            lightMsg2.setRegion(tLightDevice.getAdress());
            lightMsg2.setFloor(String.valueOf(tFloor.getFloor()));
            lightMsg2.setType("2");
            lightMsg2.setValue("1");
            iLightService.controlLight(lightMsg2);
        }catch (Exception e){
            log.error("添加设备位置时，发送拉取该设备情景消息失败：{}",e.getMessage());
        }
      return tLightPosition;
    }

}
