package com.landleaf.ibsaas.web.web.service.hvac.impl;

import com.landleaf.ibsaas.common.dao.hvac.HvacNodeDao;
import com.landleaf.ibsaas.common.domain.hvac.HvacNode;
import com.landleaf.ibsaas.common.domain.hvac.dto.HvacNodeDTO;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.service.hvac.IHvacNodeService;
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
public class HvacNodeService extends AbstractBaseService<HvacNodeDao, HvacNode> implements IHvacNodeService {

    private final HvacNodeDao hvacNodeDao;

    private final DaoAdapter<HvacNode> daoAdapter;

    @Override
    public HvacNode addHvacNode(HvacNodeDTO hvacNodeDTO) {
        HvacNode hvacNode = new HvacNode();
        BeanUtils.copyProperties(hvacNodeDTO, hvacNode);
        daoAdapter.consummateAddOperation(hvacNode);
        save(hvacNode);
        return hvacNode;
    }

    @Override
    public void deleteById(String id) {
        HvacNode hvacNode = selectByPrimaryKey(id);
        daoAdapter.consummateDeleteOperation(hvacNode);
        updateByPrimaryKeySelective(hvacNode);
    }

    @Override
    public HvacNode updateById(HvacNodeDTO hvacNodeDTO) {
        HvacNode hvacNode = new HvacNode();
        BeanUtils.copyProperties(hvacNodeDTO, hvacNode);
        daoAdapter.consummateUpdateOperation(hvacNode);
        updateByPrimaryKeySelective(hvacNode);
        return hvacNode;
    }

    @Override
    public HvacNode getById(String id) {
        return selectByPrimaryKey(id);
    }
}
