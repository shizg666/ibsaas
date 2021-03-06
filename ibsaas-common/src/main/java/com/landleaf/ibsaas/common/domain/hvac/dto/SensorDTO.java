package com.landleaf.ibsaas.common.domain.hvac.dto;

import com.landleaf.ibsaas.common.domain.hvac.Sensor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/6 17:14
 * @description:
 */
@Data
@ToString(callSuper = true)
public class SensorDTO extends Sensor implements Serializable {

}
