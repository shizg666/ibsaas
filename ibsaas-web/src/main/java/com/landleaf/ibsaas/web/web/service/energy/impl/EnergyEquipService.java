package com.landleaf.ibsaas.web.web.service.energy.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.constant.HvacConstant;
import com.landleaf.ibsaas.common.constant.IbsaasConstant;
import com.landleaf.ibsaas.common.dao.energy.EnergyEquipDao;
import com.landleaf.ibsaas.common.dao.energy.EnergyEquipVerifyDao;
import com.landleaf.ibsaas.common.dao.hvac.HvacNodeDao;
import com.landleaf.ibsaas.common.domain.energy.EnergyEquip;
import com.landleaf.ibsaas.common.domain.energy.EnergyEquipVerify;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyEquipDTO;
import com.landleaf.ibsaas.common.domain.energy.dto.EnergyEquipSearchDTO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyEquipSearchVO;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyEquipVO;
import com.landleaf.ibsaas.common.domain.energy.vo.NodeChoiceVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.ElectricMeterVO;
import com.landleaf.ibsaas.common.domain.hvac.vo.WaterMeterVO;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.redis.RedisHandle;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyEquipService;
import com.landleaf.ibsaas.web.web.service.energy.IEnergyEquipVerifyService;
import com.landleaf.ibsaas.web.web.util.WebDaoAdapter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    private HvacNodeDao hvacNodeDao;

    @Autowired
    private WebDaoAdapter<EnergyEquip> webDaoAdapter;
    @Autowired
    private WebDaoAdapter<EnergyEquipVerify> webDaoAdapterVerify;

    @Autowired
    private RedisHandle redisHandle;

    @Value("${bacnet.place.id}")
    private String placeId;

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

    @Override
    public PageInfo<EnergyEquipSearchVO> currentDataList(EnergyEquipSearchDTO energyEquipSearchDTO) {
        //查询分页设备
        PageHelper.startPage(energyEquipSearchDTO.getPage(), energyEquipSearchDTO.getLimit());
        List<EnergyEquipSearchVO> energyEquipSearchVOList = energyEquipDao.getEnergyEquipSearchVO(energyEquipSearchDTO);
        //从redis获取电水表数据
        List<WaterMeterVO> waterMeterVOList = redisHandle.getMapField(placeId, String.valueOf(HvacConstant.WATER_METER_PORT));
        List<ElectricMeterVO> electricMeterVOList = redisHandle.getMapField(placeId, String.valueOf(HvacConstant.ELECTRIC_METER_PORT));
        Map<String, BigDecimal> map = waterMeterVOList.stream().collect(Collectors.toMap(WaterMeterVO::getId, wm -> new BigDecimal(wm.getWmReading())));
        Map<String, BigDecimal> temp = electricMeterVOList.stream().collect(Collectors.toMap(ElectricMeterVO::getId, em -> new BigDecimal(em.getEmReading())));
        map.putAll(temp);
        //计算赋值
        energyEquipSearchVOList.forEach( e -> {
            BigDecimal currentDataValue = map.get(e.getNodeId());
            e.setCurrentDataValue(currentDataValue);
            e.setActualDataValue(currentDataValue.subtract(e.getVerifyValue()));
        });
        return new PageInfo<>(energyEquipSearchVOList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public EnergyEquipVO addEnergyEquip(EnergyEquipDTO energyEquipDTO) {
        //存储基础设备
        EnergyEquip energyEquip = new EnergyEquip();
        BeanUtils.copyProperties(energyEquipDTO, energyEquip);
        //校验是否绑定水电表 和 重复绑定
        checkBindNodeId(energyEquip);

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
        //校验是否绑定水电表 和 重复绑定
        checkBindNodeId(energyEquip);
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
        return getEnergyEquipById(energyEquip.getId());
    }

    @Override
    public NodeChoiceVO nodes() {
        NodeChoiceVO result = new NodeChoiceVO();
        result.setElectricNodes(hvacNodeDao.getHvacNodeByInstanceNumberWithoutEquip(HvacConstant.ELECTRIC_METER_PORT));
        result.setWaterNodes(hvacNodeDao.getHvacNodeByInstanceNumberWithoutEquip(HvacConstant.WATER_METER_PORT));
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
            iEnergyEquipVerifyService.saveSelective(newEnergyEquipVerify);
            return true;
        }
        throw new BusinessException("校验时间和校验值相同");
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

    private void checkBindNodeId(EnergyEquip energyEquip){
        if(energyEquip.getNodeId() == null){
            throw new BusinessException("未选择具体水电表节点");
        }
        List<EnergyEquip> energyEquips = energyEquipDao.getEnergyEquipByNodeId(energyEquip.getNodeId(), energyEquip.getId());
        if(CollectionUtils.isNotEmpty(energyEquips)){
            throw new BusinessException("该水电表节点已绑定设备,请选择其他水电表节点");
        }
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
