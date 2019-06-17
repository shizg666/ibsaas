package com.landleaf.ibsaas.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/17 14:56
 * @description:
 */
@Data
@ApiModel("分页返回对象")
@AllArgsConstructor
@NoArgsConstructor
public class BasePageVO<T> implements Serializable {

    /**
     * 返回结果
     */
    @ApiModelProperty("返回结果")
    private List<T> list;

    /**
     * 总条数
     */
    @ApiModelProperty("总条数")
    private Long total;

}
