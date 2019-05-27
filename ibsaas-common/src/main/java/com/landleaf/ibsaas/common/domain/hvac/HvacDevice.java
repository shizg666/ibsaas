package com.landleaf.ibsaas.common.domain.hvac;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Lokiy
 * @date 2019/5/27 15:08
 * @description:  暖通设备硬件网关地址和设备号
 */
@Data
@Table(name = "t_hvac_device")
@ApiModel("远程设备")
public class HvacDevice extends BasicEntity {


    /**
     * 设备网关ip
     */
    @ApiModelProperty("远程设备网关ip")
    @NotBlank(message = "网关ip不能为空")
    private String ip;

    /**
     * 设备端口:默认47808
     */
    @ApiModelProperty("远程设备端口")
    @NotNull(message = "设备端口不能为空")
    private Integer port;

    /**
     *  设备号
     */
    @ApiModelProperty("远程设备设备号")
    @NotNull(message = "设备号不能为空")
    private Integer deviceInstanceNumber;

    /**
     * 设备描述
     */
    @ApiModelProperty("设备描述")
    private String description;

}
