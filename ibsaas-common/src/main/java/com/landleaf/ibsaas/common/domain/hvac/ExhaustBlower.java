package com.landleaf.ibsaas.common.domain.hvac;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/7/18 18:07
 * @description:
 */
@Data
@ApiModel("排风机")
public class ExhaustBlower extends BaseDevice implements Serializable {
}
