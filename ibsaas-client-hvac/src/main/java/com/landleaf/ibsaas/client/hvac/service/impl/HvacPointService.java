package com.landleaf.ibsaas.client.hvac.service.impl;

import com.landleaf.ibsaas.client.hvac.service.IHvacPointService;
import com.landleaf.ibsaas.client.hvac.util.DaoAdapter;
import com.landleaf.ibsaas.common.dao.hvac.HvacPointDao;
import com.landleaf.ibsaas.common.domain.hvac.HvacPoint;
import com.landleaf.ibsaas.common.domain.hvac.dto.HvacPointDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author Lokiy
 * @date 2019/5/27 15:56
 * @description:
 */
@Service
@AllArgsConstructor
@Slf4j
public class HvacPointService extends AbstractBaseService<HvacPointDao, HvacPoint> implements IHvacPointService {

    private final HvacPointDao hvacPointDao;

    private final DaoAdapter<HvacPoint> daoAdapter;

    @Override
    public HvacPoint addHvacPoint(HvacPointDTO hvacPointDTO) {
        HvacPoint hvacPoint = new HvacPoint();
        BeanUtils.copyProperties(hvacPointDTO, hvacPoint);
        daoAdapter.consummateAddOperation(hvacPoint);
        save(hvacPoint);
        return hvacPoint;
    }

    @Override
    public void deleteById(String id) {
        HvacPoint hvacPoint = selectByPrimaryKey(id);
        daoAdapter.consummateDeleteOperation(hvacPoint);
        updateByPrimaryKeySelective(hvacPoint);
    }

    @Override
    public HvacPoint updateById(HvacPointDTO hvacPointDTO) {
        HvacPoint hvacPoint = new HvacPoint();
        BeanUtils.copyProperties(hvacPointDTO, hvacPoint);
        daoAdapter.consummateUpdateOperation(hvacPoint);
        updateByPrimaryKeySelective(hvacPoint);
        return hvacPoint;
    }

    @Override
    public HvacPoint getById(String id) {
        return selectByPrimaryKey(id);
    }
}
