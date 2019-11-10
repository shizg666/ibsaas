package com.landleaf.ibsaas.common.domain.hvac.vo;

import com.landleaf.ibsaas.common.domain.hvac.NewFan;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/5/28 16:29
 * @description:
 */
@Data
@ToString(callSuper = true)
public class NewFanVO extends NewFan implements Serializable {
}
