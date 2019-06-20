package com.landleaf.ibsaas.common.domain.energy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/18 16:48
 * @description: 横纵坐标-horizontal and vertical coordinates
 */
@Data
@ApiModel("横纵坐标返回对象")
@AllArgsConstructor
@NoArgsConstructor
public class HlVl {

    @ApiModelProperty("横坐标")
    private List<?> xs;

    @ApiModelProperty("纵坐标")
    private List<?> ys;
}
