package com.landleaf.ibsaas.common.domain.hvac.vo;

import com.landleaf.ibsaas.common.domain.hvac.ElectricMeter;
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
public class ElectricMeterVO extends ElectricMeter implements Serializable {
}
