package com.landleaf.ibsaas.web.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @description 子系统界面查询参数
 * @author wyl
 * @date 2019/3/20 0020 16:59
 * @version 1.0
*/
@ApiModel(value = "子系统列表查询参数")
public class SubSystemQueryVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 5083140160353259000L;
    /**
     * 系统编号
     */
    @ApiModelProperty(value = "子系统编码", dataType = "String", example = "LEO")
    private String systemCode;

    /**
     * 系统名称
     */
    @ApiModelProperty(value = "子系统名称", dataType = "String", example = "权限管理系统")
    private String systemName;

    @ApiModelProperty(value = "查询参数", dataType = "String", example = "LEO")
    private String query;

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
