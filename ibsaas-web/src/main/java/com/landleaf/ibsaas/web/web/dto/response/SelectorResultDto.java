package com.landleaf.ibsaas.web.web.dto.response;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Version
 * @Title: SelectorResultDto
 * @Description: selector 返回dto
 */
public class SelectorResultDto implements Serializable {

    private static final long serialVersionUID = 1868985800845365466L;

    @ApiModelProperty(value = "实际值", required = true, dataType = "String")
    private String value;

    @ApiModelProperty(value = "返回值", required = true, dataType = "String")
    private String displayName;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
