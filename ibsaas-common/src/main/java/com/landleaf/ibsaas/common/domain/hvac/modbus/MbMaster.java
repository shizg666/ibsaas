package com.landleaf.ibsaas.common.domain.hvac.modbus;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;

/**
 * @author Lokiy
 * @date 2019/7/5 15:20
 * @description:
 */
@Data
@Table(name = "t_mb_master")
@ApiModel("modbus站点对象")
public class MbMaster extends BasicEntity  {

    @ApiModelProperty("ip地址")
    private String host;

    @ApiModelProperty("端口号")
    private Integer port;

    @ApiModelProperty("是否封装")
    private Integer encapsulated;
}
