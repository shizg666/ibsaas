package com.landleaf.ibsaas.common.domain.energy;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * @author Lokiy
 * @date 2019/6/12 11:00
 * @description:
 */
@Data
@Table(name = "t_config_setting")
@ApiModel("配置表对象")
public class ConfigSetting extends BasicEntity {

    @ApiModelProperty("配置表类型")
    @NotBlank(message = "配置表类型不能为空")
    private String settingType;

    @ApiModelProperty("配置表类型描述")
    @NotBlank(message = "配置表类型描述不能为空")
    private String settingTypeName;

    @ApiModelProperty("配置表编码")
    @NotBlank(message = "配置表编码不能为空")
    private String settingCode;

    @ApiModelProperty("配置表编码名")
    @NotBlank(message = "配置表编码名不能为空")
    private String settingCodeName;

    @ApiModelProperty("type和code对应的唯一值")
    @NotBlank(message = "type和code对应的唯一值不能为空")
    private String settingValue;

    @ApiModelProperty("排序")
    private Integer sorting;

    @ApiModelProperty("备用字段1")
    private String character1;

    @ApiModelProperty("备用字段2")
    private String character2;

    @ApiModelProperty("备用字段3")
    private String character3;

    @ApiModelProperty("备用字段4")
    private String character4;

    @ApiModelProperty("备用字段5")
    private String character5;
}
