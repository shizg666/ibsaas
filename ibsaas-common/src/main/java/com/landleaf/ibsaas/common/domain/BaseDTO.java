package com.landleaf.ibsaas.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/17 14:36
 * @description: 基础传输对象  用于分页用
 */
@Data
@ApiModel("基础传输对象")
public class BaseDTO implements Serializable {

    @ApiModelProperty("当前页")
    @Min(value = 1,message = "当前页码不合法")
    private Integer page = 1;

    @ApiModelProperty("每页的数量")
    @Min(value = 1,message = "每页展示数量不合法")
    private Integer limit = 10;
}
