package com.landleaf.ibsaas.common.domain.hvac.vo;

import com.landleaf.ibsaas.common.domain.hvac.WeatherStation;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/6/6 16:05
 * @description:
 */
@Data
@ToString(callSuper = true)
public class WeatherStationVO extends WeatherStation implements Serializable {

}
