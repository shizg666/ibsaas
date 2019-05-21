package com.landleaf.ibsaas.web.web.service.knight;

import com.landleaf.ibsaas.common.domain.knight.TBuilding;
import com.landleaf.ibsaas.web.web.vo.BuildingReponseVO;

import java.util.List;

public interface IBuildingService {

    TBuilding addBuildingOrUpdate (TBuilding tBuilding);


    List<BuildingReponseVO> getBuildingAllInfo();
}
