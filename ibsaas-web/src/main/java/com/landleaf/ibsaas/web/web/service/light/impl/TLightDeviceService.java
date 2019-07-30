package com.landleaf.ibsaas.web.web.service.light.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.dao.light.TLightDeviceDao;
import com.landleaf.ibsaas.common.domain.light.TLightDevice;
import com.landleaf.ibsaas.common.domain.light.vo.LightDeviceResponseVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightDeviceQueryVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightDeviceRequestVO;
import com.landleaf.ibsaas.common.enums.light.LightProcotolEnum;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.service.light.ITLightDeviceService;
import com.landleaf.ibsaas.web.web.service.light.ITLightPositionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class TLightDeviceService extends AbstractBaseService<TLightDeviceDao, TLightDevice> implements ITLightDeviceService<TLightDevice> {

    @Autowired
    private ITLightPositionService itLightPositionService;

    @Override
    @Transactional
    public TLightDevice addOrUpdateDevice(TLightDeviceRequestVO tLightDeviceRequestVO) {
        TLightDevice tLightDevice;
        if (tLightDeviceRequestVO.getId() == null || tLightDeviceRequestVO.getId() == 0L){
            tLightDevice = addDevice(tLightDeviceRequestVO);
        }else {
            tLightDevice = updateDevice(tLightDeviceRequestVO);
        }
        return tLightDevice;
    }

    @Override
    @Transactional
    public Integer deleteDevice(Long id) {
        Integer result = deleteByPrimaryKey(id);
        itLightPositionService.deletePositionByDeviceId(id);
        return result;
    }

    @Override
    public PageInfo<LightDeviceResponseVO> getDeviceRecordByCondition(TLightDeviceQueryVO requestBody) {
        PageHelper.startPage(requestBody.getPage(), requestBody.getLimit(), true);
        List<LightDeviceResponseVO> lightProducts = dao.getDeviceRecordByCondition(requestBody);
        if (CollectionUtils.isEmpty(lightProducts)) {
            lightProducts = Lists.newArrayList();
        }
        lightProducts.forEach(obj->{
            obj.setProtocol(LightProcotolEnum.getInstByType(obj.getProtocol()).getName());
        });
        return new PageInfo<>(lightProducts);
    }

    @Override
    public LightDeviceResponseVO getDeviceById(Long id) {
        LightDeviceResponseVO result = this.dao.selectDeviceById(id );
        result.setProtocolId(result.getProtocol());
        result.setProtocol(LightProcotolEnum.getInstByType(result.getProtocol()).getName());
        return result;
    }

    private TLightDevice updateDevice(TLightDeviceRequestVO tLightDeviceRequestVO) {
        TLightDevice tLightDevice = new TLightDevice();
        BeanUtils.copyProperties(tLightDeviceRequestVO,tLightDevice);
        Integer result = updateByPrimaryKeySelective(tLightDevice);
        if (result < 0 ){
            throw new BusinessException("灯光设备修改失败");
        }
//        //删除老数据
//        Example example = new Example(TLightProductDevice.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andCondition("deviceId =", tLightDevice.getId());
//        tLightProductDeviceService.deleteByExample(example);
//        //修改关联表
//        TLightProductDevice tLightProductDevice = new TLightProductDevice();
//        tLightProductDevice.setDeviceId(tLightDevice.getId());
//        tLightProductDevice.setProductId(tLightDeviceRequestVO.getProductId());
//        Example example = new Example(TLightProductDevice.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("deviceId", tLightDevice.getId());
        return tLightDevice;
    }


    private TLightDevice addDevice(TLightDeviceRequestVO tLightDeviceRequestVO) {
        TLightDevice tLightDevice = new TLightDevice();
        //Detects duplicates in source code
        BeanUtils.copyProperties(tLightDeviceRequestVO,tLightDevice);
        Date date = new Date();
        tLightDevice.setCtime(date);
        tLightDevice.setUtime(date);
        Integer result = saveSelective(tLightDevice);
        if (result < 0 ){
            throw new BusinessException("灯光设备添加失败");
        }

        return tLightDevice;
    }
}
