package com.landleaf.ibsaas.web.web.service.energy.impl;

import com.landleaf.ibsaas.common.dao.energy.EnergyEquipDao;
import com.landleaf.ibsaas.common.domain.energy.EnergyEquip;
import com.landleaf.ibsaas.common.domain.energy.EnergyEquipNode;
import com.landleaf.ibsaas.common.domain.energy.EnergyEquipVerify;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyEquipDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyEquipVO;
import com.landleaf.ibsaas.common.domain.energy.vo.NodeChoiceVO;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyEquipNodeService;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyEquipService;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyEquipVerifyService;
import com.landleaf.ibsaas.web.web.util.WebDaoAdapter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Lokiy
 * @date 2019/6/12 14:52
 * @description:
 */
@Service
@Slf4j
public class EnergyEquipService extends AbstractBaseService<EnergyEquipDao, EnergyEquip> implements IEnergyEquipService {

    private static final String ZERO = "0";
    private static final String ZERO_ZERO = "00";

    @Autowired
    private EnergyEquipDao energyEquipDao;

    @Autowired
    private IEnergyEquipVerifyService iEnergyEquipVerifyService;

    @Autowired
    private IEnergyEquipNodeService iEnergyEquipNodeService;


    @Autowired
    private WebDaoAdapter<EnergyEquip> webDaoAdapter;
    @Autowired
    private WebDaoAdapter<EnergyEquipNode> webDaoAdapterNode;
    @Autowired
    private WebDaoAdapter<EnergyEquipVerify> webDaoAdapterVerify;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public EnergyEquipVO addEnergyEquip(EnergyEquipDTO energyEquipDTO) {
        //存储基础设备
        EnergyEquip energyEquip = new EnergyEquip();
        BeanUtils.copyProperties(energyEquipDTO, energyEquip);
        webDaoAdapter.consummateAddOperation(energyEquip);
        synchronized (this) {
            if (StringUtils.isBlank(energyEquip.getEquipNo())) {
                energyEquip.setEquipNo(getEquipNo());
            }
            if (!checkUnique(energyEquip.getEquipName(), energyEquip.getEquipNo())) {
                throw new BusinessException("设备名称或设备编号已存在");
            }
            save(energyEquip);
        }
        //存储设备和节点的绑定关系
        energyEquipDTO.getNodeIds().forEach( nodeId -> {
            EnergyEquipNode energyEquipNode = new EnergyEquipNode();
            energyEquipNode.setEquipId(energyEquip.getId());
            energyEquipNode.setNodeId(nodeId);
            webDaoAdapterNode.consummateAddOperation(energyEquipNode);
            iEnergyEquipNodeService.save(energyEquipNode);
        });
        //存储设备校验值
        EnergyEquipVerify energyEquipVerify = new EnergyEquipVerify();
        energyEquipVerify.setEquipId(energyEquip.getId());
        energyEquipVerify.setVerifyTime(energyEquipDTO.getVerifyTime() == null ? new Date():energyEquipDTO.getVerifyTime());
        energyEquipVerify.setVerifyValue(energyEquipDTO.getVerifyValue() == null? BigDecimal.ZERO:energyEquipDTO.getVerifyValue());
        energyEquipVerify.setVerifyComment(energyEquipDTO.getVerifyComment());
        webDaoAdapterVerify.consummateAddOperation(energyEquipVerify);
        iEnergyEquipVerifyService.save(energyEquipVerify);

        //获取设备
        return getEnergyEquipById(energyEquip.getId());
    }

    @Override
    public EnergyEquipVO getEnergyEquipById(String id) {
        return energyEquipDao.getenergyEquipVO(id);
    }

    @Override
    public EnergyEquipVO updateEnergyEquipById(EnergyEquipDTO energyEquipDTO) {


        return null;
    }

    @Override
    public NodeChoiceVO nodes() {
        return null;
    }


    private boolean checkUnique(String equipName, String equipNo){
        EnergyEquip energyEquip = energyEquipDao.selectUnique(equipName, equipNo);
        return energyEquip == null;
    }

    /**
     * 获取设备编号
     * @return
     */
    private String getEquipNo(){
        int count = energyEquipDao.count();
        String temp = String.valueOf(count+ 1);
        if(temp.length() == 1){
            return ZERO_ZERO + temp;
        }
        if(temp.length() == 2){
            return ZERO + temp;
        }
        return temp;
    }








}
