package com.landleaf.ibsaas.common.domain.hvac.dto;

import com.landleaf.ibsaas.common.domain.hvac.WaterMeter;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/10 18:14
 * @description:
 */
@Data
@ToString(callSuper = true)
public class WaterMeterDTO extends WaterMeter implements Serializable {
}
