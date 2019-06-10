package com.landleaf.ibsaas.common.domain.hvac.vo;

import com.landleaf.ibsaas.common.domain.hvac.HydraulicModule;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/6 17:18
 * @description:
 */
@Data
@ToString(callSuper = true)
public class HydraulicModuleVO extends HydraulicModule implements Serializable {
}
