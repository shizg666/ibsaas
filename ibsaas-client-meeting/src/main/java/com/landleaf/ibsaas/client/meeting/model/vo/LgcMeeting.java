package com.landleaf.ibsaas.client.meeting.model.vo;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date meetingTime;

    @ApiModelProperty("会议室")
    private String meetingRoom;

    @ApiModelProperty("内容")
    private String content;
}
