package com.landleaf.ibsaas.common.domain.light.vo;

import com.landleaf.ibsaas.common.domain.light.TLightAttribute;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@ApiModel(description = "灯光设备VO")
public class TLightPositionResponseVO {

    @ApiModelProperty(value = "主键id（修改必传）")
    private Long id;
    @ApiModelProperty(value = "横坐标")
    private String xPos;
    @ApiModelProperty(value = "纵坐标")
    private String yPos;
    @ApiModelProperty(value = "设备主键id")
    private Long deviceId;
    @ApiModelProperty(value = "楼层id")
    private Long floorId;
    @ApiModelProperty(value = "状态")
    private String state;
    @ApiModelProperty(value = "属性列表")
    List<TLightAttribute> list;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<TLightAttribute> getList() {
        return list;
    }

    public void setList(List<TLightAttribute> list) {
        this.list = list;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getxPos() {
        return xPos;
    }

    public void setxPos(String xPos) {
        this.xPos = xPos == null ? null : xPos.trim();
    }

    public String getyPos() {
        return yPos;
    }

    public void setyPos(String yPos) {
        this.yPos = yPos == null ? null : yPos.trim();
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }


}