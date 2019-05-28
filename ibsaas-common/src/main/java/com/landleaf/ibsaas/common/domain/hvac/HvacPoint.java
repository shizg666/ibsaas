package com.landleaf.ibsaas.common.domain.hvac;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;

/**
 * @author Lokiy
 * @date 2019/5/28 8:48
 * @description: 远程设备点位
 */
@Data
@Table(name = "t_hvac_point")
@ApiModel("远程设备点位")
public class HvacPoint extends BasicEntity {

    /**
     * 节点id
     */
    @ApiModelProperty("节点id")
    private String nodeId;

    /**
     * 字段id
     */
    @ApiModelProperty("字段id")
    private String fieldId;

    /**
     * bacnet对象类型
     */
    @ApiModelProperty("becnet对象类型")
    private String bacnetObjectType;

    /**
     * 点位实例号
     */
    @ApiModelProperty("点位实例号")
    private Integer instanceNumber;
}
