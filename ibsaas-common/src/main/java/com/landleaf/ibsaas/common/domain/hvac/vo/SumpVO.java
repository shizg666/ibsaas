package com.landleaf.ibsaas.common.domain.hvac.vo;

import com.landleaf.ibsaas.common.domain.hvac.Sump;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/7/18 18:03
 * @description:
 */
@Data
@ToString(callSuper = true)
public class SumpVO extends Sump implements Serializable {
}
