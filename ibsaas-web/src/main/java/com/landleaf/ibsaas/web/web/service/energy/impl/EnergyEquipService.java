package com.landleaf.ibsaas.web.web.service.energy.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.constant.HvacConstant;
import com.landleaf.ibsaas.common.constant.IbsaasConstant;
import com.landleaf.ibsaas.common.dao.energy.EnergyEquipDao;
import com.landleaf.ibsaas.common.dao.energy.EnergyEquipNodeDao;
import com.landleaf.ibsaas.common.dao.energy.EnergyEquipVerifyDao;
import com.landleaf.ibsaas.common.dao.hvac.HvacNodeDao;
import com.landleaf.ibsaas.common.domain.energy.EnergyEquip;
import com.landleaf.ibsaas.common.domain.energy.EnergyEquipNode;
import com.landleaf.ibsaas.common.domain.energy.EnergyEquipVerify;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyEquipDTO;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyEquipSearchDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyEquipSearchVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyEquipVO;
import com.landleaf.ibsaas.common.domain.energy.vo.NodeChoiceVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.HvacNodeVO;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyEquipNodeService;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyEquipService;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyEquipVerifyService;
import com.landleaf.ibsaas.web.web.util.WebDaoAdapter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private EnergyEquipVerifyDao energyEquipVerifyDao;

    @Autowired
    private IEnergyEquipNodeService iEnergyEquipNodeService;
    @Autowired
    private EnergyEquipNodeDao energyEquipNodeDao;

    @Autowired
    private HvacNodeDao hvacNodeDao;

    @Autowired
    private WebDaoAdapter<EnergyEquip> webDaoAdapter;
    @Autowired
    private WebDaoAdapter<EnergyEquipNode> webDaoAdapterNode;
    @Autowired
    private WebDaoAdapter<EnergyEquipVerify> webDaoAdapterVerify;

    @Override
    public EnergyEquipVO getEnergyEquipById(String id) {
        return energyEquipDao.getEnergyEquipVO(id);
    }

    @Override
    public PageInfo<EnergyEquipSearchVO> list(EnergyEquipSearchDTO energyEquipSearchDTO) {
        PageHelper.startPage(energyEquipSearchDTO.getPage(), energyEquipSearchDTO.getLimit());
        List<EnergyEquipSearchVO> energyEquipSearchVOList = energyEquipDao.getEnergyEquipSearchVO(energyEquipSearchDTO);
        return new PageInfo<>(energyEquipSearchVOList);
    }

    @Override
    public PageInfo<EnergyEquipSearchVO> dataList(EnergyEquipSearchDTO energyEquipSearchDTO) {
        PageHelper.startPage(energyEquipSearchDTO.getPage(), energyEquipSearchDTO.getLimit());
        List<EnergyEquipSearchVO> energyEquipSearchVOList = energyEquipDao.getDataEnergyEquipSearchVO(energyEquipSearchDTO);
        return new PageInfo<>(energyEquipSearchVOList);
    }

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
        EnergyEquipVerify newEnergyEquipVerify = getNewEnergyEquipVerify(energyEquipDTO, energyEquip.getId());
        iEnergyEquipVerifyService.save(newEnergyEquipVerify);

        //获取设备
        return getEnergyEquipById(energyEquip.getId());
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public EnergyEquipVO updateEnergyEquipById(EnergyEquipDTO energyEquipDTO) {
        Date now = new Date();
        EnergyEquip energyEquip = new EnergyEquip();
        BeanUtils.copyProperties(energyEquipDTO, energyEquip);
        webDaoAdapter.consummateUpdateOperation(energyEquip);
        updateByPrimaryKeySelective(energyEquip);

        EnergyEquipVO old = getEnergyEquipById(energyEquipDTO.getId());
        if(!(old.getVerifyTime().equals(energyEquipDTO.getVerifyTime())
                && old.getVerifyValue().compareTo(energyEquipDTO.getVerifyValue())==0)){
            //更新的校验时间和原来的校验时间不相等,则更新校验时间
            //更改之前校验状态为不可用
            int updates = energyEquipVerifyDao.updateUnEnableByEquipId(energyEquipDTO.getId(), webDaoAdapter.getUserCode(), now);
            //插入新的校验状态
            EnergyEquipVerify newEnergyEquipVerify = getNewEnergyEquipVerify(energyEquipDTO, energyEquipDTO.getId());
            iEnergyEquipVerifyService.save(newEnergyEquipVerify);
        }
        List<String> newNodeIds = energyEquipDTO.getNodeIds();
        List<String> oldNodeIds = old.getNodes().stream().map(HvacNodeVO::getNodeId).collect(Collectors.toList());
        if(!CollectionUtils.containsAll(newNodeIds, oldNodeIds)){
            //如果绑定的节点信息有所变化时
            //删除老的绑定节点数据
            int updates = energyEquipNodeDao.updateUnActiveByEquipId(energyEquipDTO.getId(), webDaoAdapter.getUserCode(), now);
            //更新新的节点数据
            //存储设备和节点的绑定关系
            newNodeIds.forEach( nodeId -> {
                EnergyEquipNode energyEquipNode = new EnergyEquipNode();
                energyEquipNode.setEquipId(energyEquip.getId());
                energyEquipNode.setNodeId(nodeId);
                webDaoAdapterNode.consummateAddOperation(energyEquipNode);
                iEnergyEquipNodeService.save(energyEquipNode);
            });
        }
        return getEnergyEquipById(energyEquip.getId());
    }

    @Override
    public NodeChoiceVO nodes() {
        NodeChoiceVO result = new NodeChoiceVO();
        result.setElectricNodes(hvacNodeDao.getHvacNodeByInstanceNumber(HvacConstant.ELECTRIC_METER_PORT));
        result.setWaterNodes(hvacNodeDao.getHvacNodeByInstanceNumber(HvacConstant.WATER_METER_PORT));
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateEnergyEquipVerifyById(EnergyEquipDTO energyEquipDTO) {
        Date now = new Date();
        EnergyEquipVO old = getEnergyEquipById(energyEquipDTO.getId());
        if(!old.getVerifyTime().equals(energyEquipDTO.getVerifyTime())
                || !old.getVerifyValue().equals(energyEquipDTO.getVerifyValue())){
            energyEquipVerifyDao.updateUnEnableByEquipId(energyEquipDTO.getId(), webDaoAdapter.getUserCode(), now);
            EnergyEquipVerify newEnergyEquipVerify = getNewEnergyEquipVerify(energyEquipDTO, energyEquipDTO.getId());
            iEnergyEquipVerifyService.save(newEnergyEquipVerify);
        }
        return false;
    }




    private EnergyEquipVerify  getNewEnergyEquipVerify(EnergyEquipDTO energyEquipDTO, String equipId) {
        EnergyEquipVerify energyEquipVerify = new EnergyEquipVerify();
        energyEquipVerify.setEquipId(equipId);
        energyEquipVerify.setVerifyTime(energyEquipDTO.getVerifyTime() == null ? new Date():energyEquipDTO.getVerifyTime());
        energyEquipVerify.setVerifyValue(energyEquipDTO.getVerifyValue() == null? BigDecimal.ZERO:energyEquipDTO.getVerifyValue());
        energyEquipVerify.setEnableFlag(IbsaasConstant.ENABLE_FLAG);
        energyEquipVerify.setVerifyComment(energyEquipDTO.getVerifyComment());
        webDaoAdapterVerify.consummateAddOperation(energyEquipVerify);
        return energyEquipVerify;

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
