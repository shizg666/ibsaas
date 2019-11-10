package com.landleaf.ibsaas.web.web.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Version
 * @Title: RequestParamSelectorDto
 * @Description: selector 请求参数dto
 */
@ApiModel(value = "selector 请求参数dto")
public class SelectorParamsDto implements Serializable {

    private static final long serialVersionUID = -3341127849999323137L;

    @ApiModelProperty(value = "查询参数", required = true, dataType = "String")
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
