package com.landleaf.ibsaas.common.domain.hvac.dto;

import com.landleaf.ibsaas.common.domain.hvac.FanCoil;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/10 16:19
 * @description:
 */
@Data
@ToString(callSuper = true)
public class FanCoilDTO extends FanCoil implements Serializable {
}
