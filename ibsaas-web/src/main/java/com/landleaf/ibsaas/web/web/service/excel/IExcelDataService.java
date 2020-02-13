package com.landleaf.ibsaas.web.web.service.excel;

import com.landleaf.ibsaas.common.domain.CascadeVO;
import com.landleaf.ibsaas.common.domain.excel.DeviceExcelData;
import com.landleaf.ibsaas.common.domain.excel.EnergyExcelData;
import com.landleaf.ibsaas.common.domain.excel.ExcelDataDTO;

import java.util.List;

/**
 * @author Lokiy
 * @date 2020/2/11 0011
 * @description:
 */
public interface IExcelDataService {

    List<EnergyExcelData> energyExcelData( ExcelDataDTO dataDTO);

    List<DeviceExcelData> deviceExcelData( ExcelDataDTO dataDTO);

    List<CascadeVO> dataSelect();

}
