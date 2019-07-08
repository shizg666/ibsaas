package com.landleaf.ibsaas.web.web.service.hvac;

import com.landleaf.ibsaas.common.domain.hvac.vo.SensorVO;

import java.util.List;
import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/6/10 13:54
 * @description:
 */
public interface ISensorWebService {

    /**
     * 传感器全览
     * @return
     */
    List<SensorVO> overview();

    /**
     * 传感器分层总览
     * @return
     */
    Map<String, Map<String, SensorVO>> totalOverview();

    /**
     * 根据某个id获取信息
     * @param id
     * @return
     */
    SensorVO getInfoById(String id);
}
