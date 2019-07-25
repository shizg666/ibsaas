package com.landleaf.ibsaas.common.domain.hvac;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;

/**
 * @author Lokiy
 * @date 2019/5/28 8:43 暖通设备字段
 * @description:
 */
@Data
@Table(name = "t_hvac_field")
@ApiModel("设备字段")
public class HvacField extends BasicEntity {


    /**
     * 设备id
     */
    @ApiModelProperty("设备类型")
    private Integer deviceType;

    /**
     * 字段名称
     */
    @ApiModelProperty("字段名称")
    private String fieldName;

    /**
     * 字段描述
     */
    @ApiModelProperty("字段描述")
    private String fieldDescription;

    /**
     * 读写权限
     */
    @ApiModelProperty("读写权限")
    private Integer permission;
}
