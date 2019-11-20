package com.landleaf.ibsaas.web.web.service.buliding;

import com.landleaf.ibsaas.common.domain.building.vo.BuildingCloneVO;
import com.landleaf.ibsaas.common.domain.knight.TBuilding;
import com.landleaf.ibsaas.web.web.vo.BuildingReponseVO;

import java.util.List;
import java.util.Map;

public interface IBuildingCommonService {

    Integer deleteBuilding(Long id);

    void addBuildingOrUpdate(TBuilding tBuilding);


    /**
     * 获取某一类型楼栋的所有信息
     * @return
     */
    List<BuildingReponseVO> getBuildingAllInfoByType(Integer type);

    /**
     * 获取所有类型楼栋的所有信息
     * @return
     */
    Map<String, List<BuildingReponseVO>> getBuildingAllInfo();

    List<TBuilding> getBuildingList();

    List<BuildingReponseVO> cloneBuilding(BuildingCloneVO buildingCloneVO);
}
