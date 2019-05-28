package com.landleaf.ibsaas.web.web.service.hvac.impl;

import com.landleaf.ibsaas.common.dao.hvac.HvacFieldDao;
import com.landleaf.ibsaas.common.domain.hvac.HvacField;
import com.landleaf.ibsaas.common.domain.hvac.dto.HvacFieldDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.service.hvac.IHvacFieldService;
import com.landleaf.ibsaas.web.web.util.DaoAdapter;
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
public class HvacFieldService extends AbstractBaseService<HvacFieldDao, HvacField> implements IHvacFieldService {

    private final HvacFieldDao hvacFieldDao;

    private final DaoAdapter<HvacField> daoAdapter;

    @Override
    public HvacField addHvacField(HvacFieldDTO hvacFieldDTO) {
        HvacField hvacField = new HvacField();
        BeanUtils.copyProperties(hvacFieldDTO, hvacField);
        daoAdapter.consummateAddOperation(hvacField);
        save(hvacField);
        return hvacField;
    }

    @Override
    public void deleteById(String id) {
        HvacField hvacField = selectByPrimaryKey(id);
        daoAdapter.consummateDeleteOperation(hvacField);
        updateByPrimaryKeySelective(hvacField);
    }

    @Override
    public HvacField updateById(HvacFieldDTO hvacFieldDTO) {
        HvacField hvacField = new HvacField();
        BeanUtils.copyProperties(hvacFieldDTO, hvacField);
        daoAdapter.consummateUpdateOperation(hvacField);
        updateByPrimaryKeySelective(hvacField);
        return hvacField;
    }

    @Override
    public HvacField getById(String id) {
        return selectByPrimaryKey(id);
    }
}
