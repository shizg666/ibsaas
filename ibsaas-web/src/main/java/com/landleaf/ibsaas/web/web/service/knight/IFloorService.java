package com.landleaf.ibsaas.web.web.service.knight;

import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.web.web.vo.FloorReponseVO;
import com.landleaf.ibsaas.web.web.vo.RoleFloorDoorsReponseVO;

import java.util.List;

public interface IFloorService {
    TFloor addFloorOrUpdate (TFloor tFloor);

    /**
     * 查询楼层以及楼层门信息
     * @param id
     * @return
     */
    FloorReponseVO getFloorAllById(Long id);

    Integer deleteFloor(Long id);

    List<TFloor> getFloorListByParentId(Long buildingId);

    /**
     * 根据角色id查询某一楼层已绑定的门信息
     * @param floorId
     * @param roleId
     * @return
     */
    RoleFloorDoorsReponseVO getfloorControlDoorByRoleId(Long floorId, String roleId);
}
