package com.landleaf.ibsaas.common.domain.excel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Lokiy
 * @date 2020/2/11 0011
 * @description:
 */
@Data
@ApiModel("表格入参")
public class ExcelDataDTO {

    @ApiModelProperty("设备类型")
    private Integer deviceType;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;
}
