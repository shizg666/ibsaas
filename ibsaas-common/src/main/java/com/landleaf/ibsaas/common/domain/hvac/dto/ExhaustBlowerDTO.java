package com.landleaf.ibsaas.common.domain.hvac.dto;

import com.landleaf.ibsaas.common.domain.hvac.ExhaustBlower;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/7/18 18:07
 * @description:
 */
@Data
@ToString(callSuper = true)
public class ExhaustBlowerDTO extends ExhaustBlower implements Serializable {
}
