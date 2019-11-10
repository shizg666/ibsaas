package com.landleaf.ibsaas.web.web.service.knight;

import com.landleaf.ibsaas.common.domain.knight.TDoor;
import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.web.web.vo.BuildingReponseVO;

import java.util.List;

public interface IDoorService {

    TDoor addDoorOrUpdate (TDoor tDoor);

    List<TDoor> bacthAddOrUpdateFloor(List<TDoor> tDoors);

    Integer bindingDoorControl(Long id , Long controId);

    Integer deleteDoor(Long id);

    /**
     * 根据门禁设备id获取楼栋-楼层-门信息
     * @param controlId
     * @return
     */
    BuildingReponseVO getDoorAllInfobyControlId(Long controlId);

    List<TDoor> getDoorControlList();

    List<TDoor> getDoorInfoByControlIds(List<Long> controlIds);
}
