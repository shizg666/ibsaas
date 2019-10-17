package com.landleaf.ibsaas.common.domain.hvac.modbus;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;

/**
 * @author Lokiy
 * @date 2019/7/5 15:39
 * @description:
 */
@Data
@Table(name = "t_mb_field")
@ApiModel("modbus字段类")
public class MbField extends BasicEntity {

    @ApiModelProperty("modbus设备类型")
    private Integer mbType;

    @ApiModelProperty("字段名称")
    private String fieldName;

    @ApiModelProperty("字段描述")
    private String fieldDescription;

    @ApiModelProperty("字段权限")
    private Integer permission;

}
