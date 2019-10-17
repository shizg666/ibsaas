package com.landleaf.ibsaas.common.domain.light.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.io.Serializable;

/**
 * 灯光控制DTO
 */
@ApiModel(description = "灯光控制消息实体")
@ToString
public class LightRedisDTO implements Serializable {


    @ApiModelProperty(value = "区域/房间地址")
    private String region;
    @ApiModelProperty(value = "组")
    private String group;
    @ApiModelProperty(value = "设备地址")
    private String device;
    @ApiModelProperty(value = "设置的值")
    private String value;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }


}
