package com.landleaf.ibsaas.web.web.vo;

import com.landleaf.ibsaas.common.domain.leo.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用户管理查询实体类
 */
@ApiModel(value = "用户信息前后端交互对象")
public class UserQueryInfoVO extends User implements Serializable {

    private static final long serialVersionUID = -5375876813963864064L;

    @ApiModelProperty(value = "查询参数", required = false, dataType = "String")
    private String query;

    @ApiModelProperty(value = "分页页数", required = true, dataType = "int")
    private int page;

    @ApiModelProperty(value = "每页条数", required = true, dataType = "int")
    private int limit;

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
