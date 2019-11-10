package com.landleaf.ibsaas.common.domain.light.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 灯光控制DTO
 */
@ApiModel(description = "灯光控制DTO")
public class LightControlDTO implements Serializable {

    @ApiModelProperty(value = "楼层")
    private String floor;
    @ApiModelProperty(value = "类型")
    private String type;
    @ApiModelProperty(value = "区域/房间地址")
    private String region;
    @ApiModelProperty(value = "组")
    private String group;
    @ApiModelProperty(value = "设备地址")
    private String device;
    @ApiModelProperty(value = "情景模式")
    private String scenes;


    public String getScenes() {
        return scenes;
    }

    public void setScenes(String scenes) {
        this.scenes = scenes;
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

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
