package com.landleaf.ibsaas.common.domain.hvac.dto;

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
public class RainwaterCollectionDTO extends RainwaterCollection implements Serializable {
}
