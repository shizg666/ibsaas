package com.landleaf.ibsaas.common.enums.hvac;

/**
 * @author Lokiy
 * @date 2019/5/28 11:28
 * @description: bacnet设备权限枚举
 */
public enum BacnetPremissionEnum {

    /**
     * 4:只读
     * 2:只写
     * 6:可读可写
     */
    READ(4),

    WRITE(2),

    READ_AND_WRITE(6),

    ;



    private Integer permission;

    BacnetPremissionEnum(Integer permission) {
        this.permission = permission;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }
}
