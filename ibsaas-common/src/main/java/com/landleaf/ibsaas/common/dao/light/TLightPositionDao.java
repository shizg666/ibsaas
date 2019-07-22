package com.landleaf.ibsaas.common.dao.light;

import com.landleaf.ibsaas.common.domain.light.TLightPosition;
import com.landleaf.ibsaas.common.domain.light.vo.LightPositionDeviceVO;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TLightPositionDao extends BaseDao<TLightPosition> {

    List<LightPositionDeviceVO> getUnPositionDeviceList();
}