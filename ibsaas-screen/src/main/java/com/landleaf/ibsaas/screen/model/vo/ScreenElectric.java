package com.landleaf.ibsaas.screen.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Lokiy
 * @date 2019/12/13 17:34
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("大屏电量信息")
public class ScreenElectric {


    @ApiModelProperty("当年累计总能耗")
    private String yearTotal;

    @ApiModelProperty("当月累计总能耗")
    private String monthTotal;
}
