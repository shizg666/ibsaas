package com.landleaf.ibsaas.web.web.service.knight;

import com.landleaf.ibsaas.common.domain.knight.TDoor;
import com.landleaf.ibsaas.common.domain.knight.TFloor;

import java.util.List;

public interface IDoorService {

    TDoor addDoorOrUpdate (TDoor tDoor);

    List<TDoor> bacthAddOrUpdateFloor(List<TDoor> tDoors);
}
