package com.landleaf.ibsaas.screen.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/12/13 9:45
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("风冷热泵机组数据")
public class ScreenAchpDetail implements Serializable {

    @ApiModelProperty("开关机状态")
    private String adOnOffState;

}
