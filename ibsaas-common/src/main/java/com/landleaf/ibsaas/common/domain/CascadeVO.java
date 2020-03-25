package com.landleaf.ibsaas.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value="级联数据对象", description="页面级联数据对象")
public class CascadeVO implements Serializable {


    @ApiModelProperty(value = "值")
    private Long value;
    @ApiModelProperty(value = "显示的值")
    private String label;
    @ApiModelProperty(value = "下级没有就是null")
    private List<CascadeVO> children;

    public CascadeVO(String label, Long value) {
        this.value = value;
        this.label = label;
    }

    public CascadeVO(String label, Long value, List<CascadeVO> children) {
        this.value = value;
        this.label = label;
        this.children = children;
    }

}
