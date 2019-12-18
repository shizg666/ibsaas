package com.landleaf.ibsaas.screen.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lokiy
 * @date 2019/12/11 11:58
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("lgc会议对象")
public class LgcMeeting implements Serializable {

    @ApiModelProperty("时间")
    private String meetingTime;

    @ApiModelProperty("会议室")
    private String meetingRoom;

    @ApiModelProperty("内容")
    private String content;
}
