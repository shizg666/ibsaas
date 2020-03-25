package com.landleaf.ibsaas.screen.model.vo;

import com.landleaf.ibsaas.common.domain.energy.HlVl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Lokiy
 * @date 2019/12/17 9:55
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("大屏能源数据")
public class ScreenEnergy {

    /**
     * 电量数值数据
     */
    @ApiModelProperty("电量数值数据")
    private ScreenElectric electricNumeric;

    /**
     * 电量图形数据
     */
    @ApiModelProperty("电量图形数据")
    private HlVl electricGraphics;
}
