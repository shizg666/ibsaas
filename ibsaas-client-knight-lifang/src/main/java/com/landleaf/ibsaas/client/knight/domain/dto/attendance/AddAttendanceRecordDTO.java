package com.landleaf.ibsaas.client.knight.domain.dto.attendance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "添加打卡记录")
public class AddAttendanceRecordDTO {
    @ApiModelProperty(value = "卡号", name = "卡号", example = "0000000036B351B4", dataType = "String", required = true)
    private String serial;
    @ApiModelProperty(value = "门编号", name = "门编号", example = "1", dataType = "Integer", required = true)
    private Integer doorId;
    @ApiModelProperty(value = "打卡时间", name = "打卡时间", example = "yyyy-MM-dd HH:mm:ss", dataType = "String", required = true)
    private String cardTime;
    @ApiModelProperty(value = "备注", name = "备注", example = "备注", dataType = "String", required = false)
    private String describe;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Integer getDoorId() {
        return doorId;
    }

    public void setDoorId(Integer doorId) {
        this.doorId = doorId;
    }

    public String getCardTime() {
        return cardTime;
    }

    public void setCardTime(String cardTime) {
        this.cardTime = cardTime;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
