package com.landleaf.ibsaas.web.web.service.hvac;

import com.landleaf.ibsaas.common.domain.hvac.vo.WeatherStationVO;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/6/6 16:28
 * @description:
 */
public interface IWeatherStationWebService {
    /**
     * 气象站总览
     * @return
     */
    List<WeatherStationVO> overview();

    /**
     * 查询单个信息
     * @param id
     * @return
     */
    WeatherStationVO getInfoById(String id);
}
