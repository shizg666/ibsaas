package com.landleaf.ibsaas.common.dao.hvac;

import com.landleaf.ibsaas.common.domain.excel.DeviceExcelData;
import com.landleaf.ibsaas.common.domain.excel.EnergyExcelData;
import com.landleaf.ibsaas.common.domain.excel.ExcelDataDTO;
import com.landleaf.ibsaas.common.domain.hvac.EqpData;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;

import java.util.List;


/**
 * @author Lokiy
 * @date 2019/5/27 15:42
 * @description:  此类用于excel导入导出数据查询
 */
public interface EqpDataDao extends BaseDao<EqpData> {

    /**
     * 获取水电表数据
     * @param dto
     * @return
     */
    List<EnergyExcelData> getEnergyExcelData(ExcelDataDTO dto);

    /**
     * 获取设备数据
     * @param dto
     * @return
     */
    List<DeviceExcelData> getDeviceExcelData(ExcelDataDTO dto);
}
