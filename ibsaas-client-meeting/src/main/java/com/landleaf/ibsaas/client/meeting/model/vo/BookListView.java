package com.landleaf.ibsaas.client.meeting.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author Lokiy
 * @date 2019/12/18 0018
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("会议预定视图对象")
public class BookListView {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("房间id")
    private String roomId;

    @ApiModelProperty("房间名称")
    private String roomName;

    @ApiModelProperty("开始时间")
    private Date beginTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("部门")
    private String department;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("主题")
    private String subject;


}
