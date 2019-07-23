package com.landleaf.ibsaas.common.domain.hvac.vo;

import com.landleaf.ibsaas.common.domain.hvac.Ahu;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/7/2 17:24
 * @description:
 */
@Data
@ToString(callSuper = true)
public class AhuVO extends Ahu implements Serializable {
}
