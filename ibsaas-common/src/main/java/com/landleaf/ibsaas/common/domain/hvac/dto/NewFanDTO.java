package com.landleaf.ibsaas.common.domain.hvac.dto;

import com.landleaf.ibsaas.common.domain.hvac.NewFan;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/3 13:57
 * @description:
 */
@Data
@ToString(callSuper = true)
public class NewFanDTO extends NewFan implements Serializable {

}
