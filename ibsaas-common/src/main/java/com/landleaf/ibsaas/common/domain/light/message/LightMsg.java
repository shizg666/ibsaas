package com.landleaf.ibsaas.common.domain.light.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 灯光控制DTO
 */
@ApiModel(description = "灯光控制消息实体")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Accessors(chain = true)
@Builder
public class LightMsg implements Serializable {

    @ApiModelProperty(value = "楼层")
    private String floor;
    @ApiModelProperty(value = "类型（1:灯光场景控制处理器）")
    private String type;
    @ApiModelProperty(value = "区域/房间地址")
    private String region;
    @ApiModelProperty(value = "组")
    private String group;
    @ApiModelProperty(value = "设备地址")
    private String device;
    @ApiModelProperty(value = "设置的值")
    private String value;
    @ApiModelProperty(value = "区域地址")
    private String adress;

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

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
