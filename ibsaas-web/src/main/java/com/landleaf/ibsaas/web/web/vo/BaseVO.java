package com.landleaf.ibsaas.web.web.vo;

/**
 * @author wyl
 * @version 1.0
 * @description
 * @date 2019年03月20日 17:00
 */
public class BaseVO {
    private int page;

    private int limit;

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
