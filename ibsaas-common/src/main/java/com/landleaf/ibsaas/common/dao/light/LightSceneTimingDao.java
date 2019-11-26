package com.landleaf.ibsaas.common.dao.light;

import com.landleaf.ibsaas.common.domain.light.LightSceneTiming;
import com.landleaf.ibsaas.common.domain.light.SceneTimingDTO;
import com.landleaf.ibsaas.common.domain.light.SelectedVo;
import com.landleaf.ibsaas.common.domain.light.vo.LightSceneTimingRespVO;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lokiy
 * @since 2019-11-21
 */
public interface LightSceneTimingDao extends BaseDao<LightSceneTiming> {

    List<LightSceneTimingRespVO> getListAreaTime(@Param("deviceId") Long deviceId);

    List<SceneTimingDTO> getListExecuteByTime(@Param("time") String time);

    int updateById(LightSceneTiming lightSceneTiming);

    int deleteById(@Param("id") Long id);

    List<SelectedVo> getSceneListByDevice(@Param("deviceId") Long deviceId);
}
