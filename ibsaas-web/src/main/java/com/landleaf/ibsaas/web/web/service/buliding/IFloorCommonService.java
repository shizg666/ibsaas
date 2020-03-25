package com.landleaf.ibsaas.web.web.service.buliding;

import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.web.web.vo.FloorReponseVO;
import com.landleaf.ibsaas.web.web.vo.RoleFloorDoorsReponseVO;

import java.util.List;

public interface IFloorCommonService {

    TFloor addFloorOrUpdate (TFloor tFloor);

    TFloor getFloorById(Long id);

    Integer deleteFloor(Long id,Integer Type);

    Integer deleteFloor(Long id);

    List<TFloor> getFloorListByParentId(Long buildingId);

}
