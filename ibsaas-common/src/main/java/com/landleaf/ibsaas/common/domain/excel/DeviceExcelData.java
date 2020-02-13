package com.landleaf.ibsaas.common.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Lokiy
 * @date 2020/2/11 0011
 * @description:
 */
@Data
public class DeviceExcelData {

    @ExcelProperty("时间")
    private Date dataTime;

    @ExcelProperty("设备")
    private String nodeName;

    @ExcelProperty("楼层")
    private String floor;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("参数")
    private String attribute;

    @ExcelProperty("数据")
    private String dataValue;
}
