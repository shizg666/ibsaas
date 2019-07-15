package com.landleaf.ibsaas.common.domain.light;

public class TLightType {
    private Long id;

    private String name;

    private String reserved;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved == null ? null : reserved.trim();
    }
}