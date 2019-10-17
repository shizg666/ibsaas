package com.landleaf.ibsaas.common.domain.hvac.modbus;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;

/**
 * @author Lokiy
 * @date 2019/7/5 15:42
 * @description:
 */
@Data
@Table(name = "t_mb_register")
@ApiModel("modbus具体寄存地址点")
public class MbRegister extends BasicEntity {

    @ApiModelProperty("modbus设备类型")
    private Integer mbType;

    @ApiModelProperty("站点id")
    private String masterId;

    @ApiModelProperty("节点id")
    private String nodeId;

    @ApiModelProperty("字段id")
    private String fieldId;

    @ApiModelProperty("从节点id")
    private Integer slaveId;

    @ApiModelProperty("功能码 1/2/3/4/6/16")
    private Integer functionCode;

    @ApiModelProperty("寄存器地址(偏移量)")
    private Integer offset;

    @ApiModelProperty("传输类型")
    private Integer dataType;
}
