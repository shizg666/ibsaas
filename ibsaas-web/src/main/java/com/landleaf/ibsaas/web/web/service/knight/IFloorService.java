package com.landleaf.ibsaas.web.web.service.knight;

import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.web.web.vo.DoorReponseVO;
import com.landleaf.ibsaas.web.web.vo.FloorReponseVO;

import java.util.List;

public interface IFloorService {
    TFloor addFloorOrUpdate (TFloor tFloor);

    FloorReponseVO getFloorAllById(Long id);
}
