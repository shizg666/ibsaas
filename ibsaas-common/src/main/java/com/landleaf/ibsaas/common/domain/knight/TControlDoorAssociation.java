package com.landleaf.ibsaas.common.domain.knight;

public class TControlDoorAssociation {
    private Long id;

    private Integer contrloId;

    private Long doorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getContrloId() {
        return contrloId;
    }

    public void setContrloId(Integer contrloId) {
        this.contrloId = contrloId;
    }

    public Long getDoorId() {
        return doorId;
    }

    public void setDoorId(Long doorId) {
        this.doorId = doorId;
    }
}