package com.landleaf.ibsaas.web.web.service.light.impl;


import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.constant.LightConstants;
import com.landleaf.ibsaas.common.dao.light.TLightAreaDeviceDao;
import com.landleaf.ibsaas.common.dao.light.TLightAttributeDao;
import com.landleaf.ibsaas.common.dao.light.TLightDeviceDao;
import com.landleaf.ibsaas.common.domain.light.TLightAreaDevice;
import com.landleaf.ibsaas.common.domain.light.TLightDevice;
import com.landleaf.ibsaas.common.domain.light.message.LightMsg;
import com.landleaf.ibsaas.common.domain.light.vo.LightProductAttributeVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightAreaResponseVO;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.service.light.ILightService;
import com.landleaf.ibsaas.web.web.service.light.ITLightAreaDeviceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TLightAreaDeviceService extends AbstractBaseService<TLightAreaDeviceDao, TLightAreaDevice> implements ITLightAreaDeviceService<TLightAreaDevice> {

    @Autowired
    private TLightDeviceDao tLightDeviceDao;
    @Autowired
    private TLightAttributeDao tLightAttributeDao;
    @Autowired
    private RedisHandle redisHandle;
    @Autowired
    private ILightService iLightService;

    @Override
    public List<TLightAreaResponseVO> getPositionAtrributeListByFloor(Long id) {

        Example example = new Example(TLightAreaDevice.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("floor", id);
//        List<TLightAreaDevice> tLightAreaDevices = selectByExample(example);
        List<TLightAreaDevice> tLightAreaDevices = this.dao.getLightList(id);
        if (CollectionUtils.isEmpty(tLightAreaDevices)){
            return Lists.newArrayListWithCapacity(0);
        }
        List<TLightAreaResponseVO> tLightAreaResponseVOS = Lists.newArrayList();
        List<Long> deviceIds = Lists.newArrayList();
        tLightAreaDevices.forEach(obj->{
            TLightAreaResponseVO tLightAreaResponseVO = new TLightAreaResponseVO();
            BeanUtils.copyProperties(obj,tLightAreaResponseVO);
            tLightAreaResponseVOS.add(tLightAreaResponseVO);
            deviceIds.add(obj.getDeviceId());
        });
        List<TLightDevice> tLightDevices = tLightDeviceDao.getDeviceByIds(deviceIds);
        if (CollectionUtils.isEmpty(tLightDevices)){
            return tLightAreaResponseVOS;
        }
        List<Long> productIds = tLightDevices.stream().map(TLightDevice::getProductId).collect(Collectors.toList());
        Map<Long, TLightDevice> deviceMap = tLightDevices.stream().collect(Collectors.toMap(TLightDevice::getId, o->o));

        List<LightProductAttributeVO> tLightAttributes =  tLightAttributeDao.getAttributeListByProductIds(productIds);
        Map<Long, List<LightProductAttributeVO>> data = tLightAttributes.stream().collect(Collectors.groupingBy(LightProductAttributeVO::getProductId));
        String key = "";
        if (id == 3L){
            key =  LightConstants.LIGHT_DEVICE_3F;
        }else if (id == 4L){
            key =  LightConstants.LIGHT_DEVICE_4F;
        }
        Boolean exsitKey = StringUtil.isNotEmpty(key);
        String finalKey = key;
        List<Integer> list = Lists.newArrayList();
        tLightAreaResponseVOS.forEach(obj ->{
            Long deviceId = obj.getDeviceId();
            Long productId = deviceMap.get(deviceId).getProductId();
            String adress = deviceMap.get(deviceId).getAdress();
            obj.setAdress(adress);
            List<LightProductAttributeVO> voList = data.get(productId);
            voList.sort((a, b) -> Integer.valueOf(a.getCode()) - Integer.valueOf(b.getCode()));
            obj.setList(data.get(productId));
            if (exsitKey){
                String scenes = redisHandle.getMapField(finalKey,deviceMap.get(deviceId).getAdress());
                if (StringUtil.isBlank(scenes)){
                    //如果是空手动拉取一下
                    LightMsg lightMsg = new LightMsg();
                    lightMsg.setAdress(deviceMap.get(deviceId).getAdress());
                    lightMsg.setFloor(String.valueOf(id));
                    lightMsg.setType("3");
                    iLightService.controlLight(lightMsg);
                    //
                    list.add(1);
//                    scenes = iLightService.getTryLightState(finalKey,deviceMap.get(deviceId).getAdress(),2000L);
            }else {
                    obj.setState(scenes);
                }
        }
        });

        if (list.size()>0){
            tLightAreaResponseVOS.forEach(obj->{
                if (StringUtil.isBlank(obj.getState())){
                    Long deviceId = obj.getDeviceId();
                    String scenes2 = redisHandle.getMapField(finalKey,deviceMap.get(deviceId).getAdress());
                    if(StringUtil.isBlank(scenes2)){
                        scenes2 = iLightService.getTryLightState(finalKey,deviceMap.get(deviceId).getAdress(),2000L);
                    }
                    obj.setState(scenes2);
                }
            });
        }
        return tLightAreaResponseVOS;
    }
}