package com.landleaf.ibsaas.common.domain.hvac.vo;

import com.landleaf.ibsaas.common.domain.hvac.RainwaterCollection;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/7/18 17:57
 * @description:
 */
@Data
@ToString(callSuper = true)
public class RainwaterCollectionVO extends RainwaterCollection implements Serializable {
}
