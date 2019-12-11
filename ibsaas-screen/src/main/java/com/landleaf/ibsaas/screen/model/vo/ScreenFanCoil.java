package com.landleaf.ibsaas.screen.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Lokiy
 * @date 2019/12/11 11:47
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("大屏风盘状态")
public class ScreenFanCoil {

    @ApiModelProperty("分盘总数")
    private String totalNum;

    @ApiModelProperty("已开启数")
    private String onNum;
}
