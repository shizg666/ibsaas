package com.landleaf.ibsaas.web.web.service.hvac.impl;

import com.landleaf.ibsaas.common.dao.hvac.HvacDeviceDao;
import com.landleaf.ibsaas.common.dao.hvac.HvacNodeDao;
import com.landleaf.ibsaas.common.domain.hvac.HvacDevice;
import com.landleaf.ibsaas.common.domain.hvac.BaseDevice;
import com.landleaf.ibsaas.common.domain.hvac.NewFan;
import com.landleaf.ibsaas.common.domain.hvac.vo.HvacFieldVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.HvacNodeVO;
import com.landleaf.ibsaas.web.web.config.hvac.BacnetInfoHolder;
import com.landleaf.ibsaas.web.web.config.hvac.LocalDeviceConfig;
import com.landleaf.ibsaas.web.web.service.hvac.ICommonDeviceService;
import com.landleaf.ibsaas.web.web.util.BacnetUtil;
import com.landleaf.ibsaas.web.web.util.HvacUtil;
import com.serotonin.bacnet4j.util.PropertyValues;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/5/28 15:41
 * @description:
 */
@Service
@AllArgsConstructor
@Slf4j
public class CommonDeviceService implements ICommonDeviceService {

    private final HvacDeviceDao hvacDeviceDao;

    private final HvacNodeDao hvacNodeDao;

    @Override
    public List<? extends BaseDevice> getCurrentData(Integer deviceInstanceNumber) {
        List<BaseDevice> result = new ArrayList<>();
        PropertyValues values = BacnetUtil.readProperties(LocalDeviceConfig.getLocalDevice(), BacnetInfoHolder.REMOTE_DEVICE_MAP.get(deviceInstanceNumber), BacnetInfoHolder.OID_MAP.get(deviceInstanceNumber));
        HvacDevice hvacDevice = hvacDeviceDao.getByDeviceInstanceNumber(deviceInstanceNumber);
        List<HvacNodeVO> hvacNodeVOList = hvacNodeDao.getHvacNodeByDeviceId(hvacDevice.getId());

        hvacNodeVOList.forEach( bdn -> {
            BaseDevice baseDevice = getByDeviceId(deviceInstanceNumber);
            List<HvacFieldVO> hvacFieldVOList = bdn.getHvacFieldVOList();
            HvacUtil.assignmentByClass(values, hvacFieldVOList, baseDevice);
            result.add(baseDevice);
        });
        return result;
    }


    @Override
    public <T extends BaseDevice> T getCurrentInfo(HvacNodeVO hvacNodeVO){
        HvacDevice hvacDevice = hvacDeviceDao.selectByPrimaryKey(hvacNodeVO.getDeviceId());
        Integer deviceInstanceNumber = hvacDevice.getDeviceInstanceNumber();
        BaseDevice baseDevice = getByDeviceId(deviceInstanceNumber);
        PropertyValues values = BacnetUtil.readProperties(LocalDeviceConfig.getLocalDevice(), BacnetInfoHolder.REMOTE_DEVICE_MAP.get(deviceInstanceNumber), BacnetInfoHolder.OID_MAP.get(deviceInstanceNumber));
        List<HvacFieldVO> hvacFieldVOList = hvacNodeVO.getHvacFieldVOList();
        HvacUtil.assignmentByClass(values, hvacFieldVOList, baseDevice);
        return (T) baseDevice;
    }


    private BaseDevice getByDeviceId(Integer deviceInstanceNumber){
        switch (deviceInstanceNumber) {
            case 3002:
                return new NewFan();
            default:
                return new BaseDevice();
        }
    }
}
