package com.landleaf.ibsaas.common.domain.energy.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/25 16:05
 * @description:
 */
@Data
@ApiModel("y坐标对比数据")
@AllArgsConstructor
@NoArgsConstructor
public class ProportionalDataList {

    @ApiModelProperty("对比数据")
    private List<?> comp;

    @ApiModelProperty("现数据")
    private List<?> current;


}
