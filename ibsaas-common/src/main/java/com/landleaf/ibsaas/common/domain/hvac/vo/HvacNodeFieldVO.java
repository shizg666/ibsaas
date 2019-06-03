package com.landleaf.ibsaas.common.domain.hvac.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/3 14:53
 * @description: 节点字段对象
 */
@Data
public class HvacNodeFieldVO implements Serializable {

    private String nodeId;

    private String fieldName;

    private Integer deviceInstanceNumber;

    private String bacnetObjectType;

    private Integer instanceNumber;

    private Integer permission;
}
