package com.landleaf.ibsaas.common.domain.parking.request;

//车辆详情查询请求DTO
public class UserinfoDetailQueryDTO {

    /**
     * 记录唯一标记
     */
    private String uniqueId;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
