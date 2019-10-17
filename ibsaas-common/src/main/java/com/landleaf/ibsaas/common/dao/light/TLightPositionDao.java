package com.landleaf.ibsaas.common.dao.light;

import com.landleaf.ibsaas.common.domain.light.TLightPosition;
import com.landleaf.ibsaas.common.domain.light.vo.LightDeviceFloorVO;
import com.landleaf.ibsaas.common.domain.light.vo.LightPositionDeviceVO;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TLightPositionDao extends BaseDao<TLightPosition> {

    TLightPosition selectByid(Long id);

    int deleteByPrimaryId(Long id);

    List<LightPositionDeviceVO> getUnPositionDeviceList();

    List<LightPositionDeviceVO> getPositionDeviceList(@Param("floorId") Long floorId);

    List<LightDeviceFloorVO> getDeviceFloor();
}