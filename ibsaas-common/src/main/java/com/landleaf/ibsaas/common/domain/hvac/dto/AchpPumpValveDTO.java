package com.landleaf.ibsaas.common.domain.hvac.dto;

import com.landleaf.ibsaas.common.domain.hvac.AchpPumpValve;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/7/10 11:16
 * @description:
 */
@Data
@ToString(callSuper = true)
public class AchpPumpValveDTO extends AchpPumpValve implements Serializable {
}
