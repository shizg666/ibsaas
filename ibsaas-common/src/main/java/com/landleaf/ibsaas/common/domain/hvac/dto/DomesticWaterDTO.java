package com.landleaf.ibsaas.common.domain.hvac.dto;

import com.landleaf.ibsaas.common.domain.hvac.DomesticWater;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/7/18 18:05
 * @description:
 */
@Data
@ToString(callSuper = true)
public class DomesticWaterDTO extends DomesticWater implements Serializable {
}
