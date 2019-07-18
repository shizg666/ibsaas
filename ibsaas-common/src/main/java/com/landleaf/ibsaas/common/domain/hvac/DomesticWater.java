package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/7/18 18:05
 * @description:
 */
@Data
@ApiModel("生活水")
public class DomesticWater extends BaseDevice implements Serializable {
}
