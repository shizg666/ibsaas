package com.landleaf.ibsaas.web.web.service.light;

import com.landleaf.ibsaas.common.domain.light.SelectedVo;
import com.landleaf.ibsaas.common.domain.light.vo.LightSceneTimingReqVO;
import com.landleaf.ibsaas.common.domain.light.vo.LightSceneTimingRespVO;
import com.landleaf.ibsaas.common.domain.light.vo.LightTimingSwitchReqVO;
import com.landleaf.ibsaas.datasource.mybatis.service.IBaseService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lokiy
 * @since 2019-11-21
 */
public interface ILightSceneTimingService<T>  {

    void addAreaTime(LightSceneTimingReqVO reqVO);

    void timeSwitch(LightTimingSwitchReqVO reqVO);

    List<LightSceneTimingRespVO> getListAreaTime(Long areaId);

    void executeTime(LocalDateTime nowDate);

    void deleteTime(Long id);

    void update(LightSceneTimingReqVO reqVO);

    List<SelectedVo> getSceneListByDevice(Long deviceId);
}
