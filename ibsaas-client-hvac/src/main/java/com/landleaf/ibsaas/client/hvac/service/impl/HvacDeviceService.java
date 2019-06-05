package com.landleaf.ibsaas.client.hvac.service.impl;

import com.landleaf.ibsaas.client.hvac.config.BacnetInfoHolder;
import com.landleaf.ibsaas.client.hvac.service.IHvacDeviceService;
import com.landleaf.ibsaas.client.hvac.util.DaoAdapter;
import com.landleaf.ibsaas.common.dao.hvac.HvacDeviceDao;
import com.landleaf.ibsaas.common.domain.hvac.HvacDevice;
import com.landleaf.ibsaas.common.domain.hvac.dto.HvacDeviceDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/5/27 15:56
 * @description:
 */
@Service
@AllArgsConstructor
@Slf4j
public class HvacDeviceService extends AbstractBaseService<HvacDeviceDao, HvacDevice> implements IHvacDeviceService {

    private final HvacDeviceDao hvacDeviceDao;

    private final DaoAdapter<HvacDevice> daoAdapter;



    @Override
    public HvacDevice addHvacDevice(HvacDeviceDTO hvacDeviceDTO) {
        HvacDevice hvacDevice = new HvacDevice();
        BeanUtils.copyProperties(hvacDeviceDTO, hvacDevice);
        daoAdapter.consummateAddOperation(hvacDevice);
        save(hvacDevice);
        return hvacDevice;
    }

    @Override
    public void deleteById(String id) {
        HvacDevice hvacDevice = selectByPrimaryKey(id);
        daoAdapter.consummateDeleteOperation(hvacDevice);
        updateByPrimaryKeySelective(hvacDevice);
    }

    @Override
    public HvacDevice updateById(HvacDeviceDTO hvacDeviceDTO) {
        HvacDevice hvacDevice = new HvacDevice();
        BeanUtils.copyProperties(hvacDeviceDTO, hvacDevice);
        daoAdapter.consummateUpdateOperation(hvacDevice);
        updateByPrimaryKeySelective(hvacDevice);
        return hvacDevice;
    }

    @Override
    public HvacDevice getById(String id) {
        return selectByPrimaryKey(id);
    }

    @Override
    public List<HvacDevice> all() {
        return hvacDeviceDao.all();
    }


}
