package com.landleaf.ibsaas.common.domain.parking.response;


//收费类型返回DTO
public class ChargeruleResponseDTO {

    //记录唯一标记
    private String uniqueId;
    //收费类型编码
    private String chargeTypeCode;
    //收费类型名称
    private String chargeTypeName;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getChargeTypeCode() {
        return chargeTypeCode;
    }

    public void setChargeTypeCode(String chargeTypeCode) {
        this.chargeTypeCode = chargeTypeCode;
    }

    public String getChargeTypeName() {
        return chargeTypeName;
    }

    public void setChargeTypeName(String chargeTypeName) {
        this.chargeTypeName = chargeTypeName;
    }
}
