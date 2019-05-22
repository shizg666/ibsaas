package com.landleaf.ibsaas.web.web.controller.knight;


import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.knight.TBuilding;
import com.landleaf.ibsaas.common.domain.knight.TDoor;
import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.knight.IBuildingService;
import com.landleaf.ibsaas.web.web.service.knight.IDoorService;
import com.landleaf.ibsaas.web.web.service.knight.IFloorService;
import com.landleaf.ibsaas.web.web.vo.BuildingReponseVO;
import com.landleaf.ibsaas.web.web.vo.DoorReponseVO;
import com.landleaf.ibsaas.web.web.vo.FloorReponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/knight")
@Api(value = "/knight", description = "门位置业务相关操作")
public class DoorController extends BasicController {
    @Autowired
    private IBuildingService iBuildingService;
    @Autowired
    private IFloorService iFloorService;
    @Autowired
    private IDoorService iDoorService;


    @GetMapping("door/getDoorInfoAll")
    @ApiOperation(value = "获取楼栋所有信息", notes = "获取楼栋所有信息")
    public Response getBuildingInfoAll() {
        List<BuildingReponseVO> list = iBuildingService.getBuildingAllInfo();
        return returnSuccess(list);
    }
    @GetMapping("door/getDoorInfoAll/{floorId}")
    @ApiOperation(value = "获取楼层所有门信息", notes = "获取楼栋所有信息")
    public Response getDoorsByflooId(@PathVariable @ApiParam(name="floorId",value="楼层id",required=true) Long floorId){
        FloorReponseVO floorReponseVO = iFloorService.getFloorAllById(floorId);
        return returnSuccess(floorReponseVO);
    }

    @PostMapping("door/addOrUpdateBuilding")
    @ApiOperation(value = "添加或者修改楼栋信息", notes = "添加或者修改楼栋信息")
    public Response addOrUpdateBuilding(@RequestBody TBuilding tBuilding) {
        TBuilding tBuilding1 = iBuildingService.addBuildingOrUpdate(tBuilding);
        return returnSuccess(tBuilding1);
    }

    @PostMapping("door/addOrUpdateFloor")
    @ApiOperation(value = "添加或者修改楼层信息", notes = "添加或者修改楼层信息")
    public Response addOrUpdateFloor(@RequestBody TFloor tFloor) {
        TFloor tFloor1 = iFloorService.addFloorOrUpdate(tFloor);
        return returnSuccess(tFloor1);
    }

    @PostMapping("door/addOrUpdateDoor")
    @ApiOperation(value = "添加或者修改门信息", notes = "添加或者修改楼层信息")
    public Response addOrUpdateDoor(@RequestBody TDoor tDoor) {
        TDoor tDoor1 = iDoorService.addDoorOrUpdate(tDoor);
        return returnSuccess(tDoor1);
    }

    @PostMapping("door/bacthAddOrUpdateFloor")
    @ApiOperation(value = "添加或者修改门信息", notes = "添加或者修改楼层信息")
    public Response bacthAddOrUpdateFloor(@RequestBody List<TDoor> tDoors) {
        List<TDoor> tDoorList = iDoorService.bacthAddOrUpdateFloor(tDoors);
        return returnSuccess(tDoorList);
    }





}
