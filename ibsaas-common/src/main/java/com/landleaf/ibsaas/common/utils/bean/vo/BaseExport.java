package com.landleaf.ibsaas.common.utils.bean.vo;

import java.io.Serializable;

/**
 * 导出和查询基类
 *
 * @author jin
 * @date 2016-9-2
 * @author HT
 */

public class BaseExport implements Serializable {

    private static final long serialVersionUID = 478448487030513533L;

    /**
     * 查询条数
     */
    protected int pageSize = 10000;
    /**
     * 查询页码
     */
    protected int pageNum = 1;

    public BaseExport() {
        super();
    }

    public BaseExport(int pageSize, int pageNum) {
        this();
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public void initBaseReport(int pagesize, int pageNum, Long userId, Boolean exportFlag) {
        this.pageSize = pagesize;
        this.pageNum = pageNum;
    }

    public int getPagesize() {
        return pageSize;
    }

    public void setPagesize(int pagesize) {
        this.pageSize = pagesize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}


