package com.landleaf.ibsaas.common.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Lokiy
 * @date 2020/2/11 0011
 * @description:
 */
@Data
public class EnergyExcelData {

    @ExcelProperty("抄表时间")
    private Date dataTime;

    @ExcelProperty("水电表名称")
    private String equipName;

    @ExcelProperty("楼层")
    private String floor;

    @ExcelProperty("分项")
    private String equipClassification;

    @ExcelProperty("抄表数值")
    private BigDecimal dataValue;

    @ExcelProperty("较上小时能耗")
    private BigDecimal dataIncreaseValue;

}
