package com.landleaf.ibsaas.common.domain.light.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;


/**
 * 灯光控制DTO
 */
@ApiModel(description = "灯光控制消息返回实体")
@ToString
@Data
public class LightMsgResponse {

    @ApiModelProperty(value = "区域/房间地址")
    private String region;
    @ApiModelProperty(value = "组")
    private String group;
    @ApiModelProperty(value = "设备地址")
    private String device;
    @ApiModelProperty(value = "情景值")
    private String scenes;
    @ApiModelProperty(value = "值")
    private String value;
    @ApiModelProperty(value = "返回值")
    private String result;
    @ApiModelProperty(value = "消息类型")
    private String type;
    @ApiModelProperty(value = "消息来源地址")
    private String address;
    @ApiModelProperty(value = "消息来源服务器")
    private String host;
    @ApiModelProperty(value = "接收时间")
    private Date ct;


}
