package com.landleaf.ibsaas.common.domain.hvac.vo;

import com.landleaf.ibsaas.common.domain.hvac.FanCoil;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/10 16:18
 * @description:
 */
@Data
@ToString(callSuper = true)
public class FanCoilVO extends FanCoil implements Serializable {
}
