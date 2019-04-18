package com.landleaf.ibsaas.web.web.vo;

import com.landleaf.ibsaas.common.domain.leo.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @description 角色信息前后端交互对象
 * @author wyl
 * @date 2019/3/20 0020 17:01
 * @return
 * @version 1.0
*/
@ApiModel(value = "角色信息前后端交互对象")
public class RoleQueryInfoVO extends Role implements Serializable {

    private static final long serialVersionUID = -3730742866233722978L;
    @ApiModelProperty(value = "角色所属系统名称", required = false, dataType = "String")
    private String systemName;

    @ApiModelProperty(value = "查询参数", required = false, dataType = "String")
    private String query;

    @ApiModelProperty(value = "分页页数", required = true, dataType = "int")
    private int page;

    @ApiModelProperty(value = "每页条数", required = true, dataType = "int")
    private int limit;

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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
