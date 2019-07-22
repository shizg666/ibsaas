package com.landleaf.ibsaas.common.domain.light.vo;

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
//    @ApiModelProperty(value = "名称")
//    private String name;
    @ApiModelProperty(value = "设备主键id")
    private Long deviceId;
    @ApiModelProperty(value = "楼层id")
    private Long floorId;
    @ApiModelProperty(value = "属性列表")
    private List<LightProductAttributeVO> list;
    @ApiModelProperty(value = "设备当前的状态值(默认是0)")
    private String state = "0";
//    @ApiModelProperty(value = "创建时间")
//    private Date ctime;
//    @ApiModelProperty(value = "修改时间")
//    private Date utime;
//    @ApiModelProperty(value = "图片url")
//    private String img;
//    @ApiModelProperty(value = "产品名称")
//    private String reserved;


    public List<LightProductAttributeVO> getList() {
        return list;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setList(List<LightProductAttributeVO> list) {
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

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name == null ? null : name.trim();
//    }

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