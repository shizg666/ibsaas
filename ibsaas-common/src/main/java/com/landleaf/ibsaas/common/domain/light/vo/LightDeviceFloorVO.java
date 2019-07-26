package com.landleaf.ibsaas.common.domain.light.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LightDeviceFloorVO {

    private String name;
    private String adress;
    private Integer floor;

}
