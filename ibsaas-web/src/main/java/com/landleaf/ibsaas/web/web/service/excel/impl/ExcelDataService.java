package com.landleaf.ibsaas.web.web.service.excel.impl;

import com.landleaf.ibsaas.common.dao.hvac.EqpDataDao;
import com.landleaf.ibsaas.common.domain.CascadeVO;
import com.landleaf.ibsaas.common.domain.excel.DeviceExcelData;
import com.landleaf.ibsaas.common.domain.excel.EnergyExcelData;
import com.landleaf.ibsaas.common.domain.excel.ExcelDataDTO;
import com.landleaf.ibsaas.common.enums.energy.EnergyTypeEnum;
import com.landleaf.ibsaas.common.enums.hvac.BacnetDeviceTypeEnum;
import com.landleaf.ibsaas.common.enums.hvac.ModbusDeviceTypeEnum;
import com.landleaf.ibsaas.web.web.service.excel.IExcelDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lokiy
 * @date 2020/2/11 0011
 * @description:
 */
@Service
public class ExcelDataService implements IExcelDataService {

    @Autowired
    private EqpDataDao eqpDataDao;

    @Override
    public List<EnergyExcelData> energyExcelData( ExcelDataDTO dataDTO) {
        //转换数据查询类别
        if(BacnetDeviceTypeEnum.WATER_METER.getDeviceType().equals(dataDTO.getDeviceType())){
            dataDTO.setDeviceType(EnergyTypeEnum.ENERGY_WATER.getEnergyType());
        }else {
            dataDTO.setDeviceType(EnergyTypeEnum.ENERGY_ELECTRIC.getEnergyType());
        }
        return eqpDataDao.getEnergyExcelData(dataDTO);
    }

    @Override
    public List<DeviceExcelData> deviceExcelData( ExcelDataDTO dataDTO) {
        return eqpDataDao.getDeviceExcelData(dataDTO);
    }

    @Override
    public List<CascadeVO> dataSelect() {
        List<CascadeVO> result = new ArrayList<>();
        for (BacnetDeviceTypeEnum bdte: BacnetDeviceTypeEnum.values()) {
            result.add(new CascadeVO(bdte.getDeviceDescription(), bdte.getDeviceType().longValue()));
        }
        for (ModbusDeviceTypeEnum mdte: ModbusDeviceTypeEnum.values()) {
            result.add(new CascadeVO(mdte.getDeviceDescription(), mdte.getDeviceType().longValue()));
        }

        return result.stream()
                .sorted(Comparator.comparing(CascadeVO::getValue))
                .collect(Collectors.toList());
    }
}
