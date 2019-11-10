package com.landleaf.ibsaas.common.domain.hvac.vo;

import com.landleaf.ibsaas.common.domain.hvac.WaterMeter;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/10 18:13
 * @description:
 */
@Data
@ToString(callSuper = true)
public class WaterMeterVO extends WaterMeter implements Serializable {
}
