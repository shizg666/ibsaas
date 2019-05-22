package com.landleaf.ibsaas.web.web.service.knight;

import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.web.web.vo.FloorReponseVO;
import com.landleaf.ibsaas.web.web.vo.RoleFloorDoorsReponseVO;

import java.util.List;

public interface IFloorService {
    TFloor addFloorOrUpdate (TFloor tFloor);

    FloorReponseVO getFloorAllById(Long id);

    Integer deleteFloor(Long id);

    List<TFloor> getFloorListByParentId(Long buildingId);

    RoleFloorDoorsReponseVO getfloorDoorByRoleId(Long floorId, String roleId);
}
