package com.landleaf.ibsaas.web.web.service.hvac;

import com.landleaf.ibsaas.common.domain.hvac.vo.SensorVO;

import java.util.List;

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
     * 根据某个id获取信息
     * @param id
     * @return
     */
    SensorVO getInfoById(String id);
}
