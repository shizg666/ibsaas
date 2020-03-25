package com.landleaf.ibsaas.common.domain.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value="SelectedVo", description="页面下拉选择数据对象")
@EqualsAndHashCode
public class SelectedVo implements Serializable {


    @ApiModelProperty(value = "值")
    private Long value;

    @ApiModelProperty(value = "显示的值")
    private String label;

    public SelectedVo(String label, Long value) {
        this.value = value;
        this.label = label;
    }

}
