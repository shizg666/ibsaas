package com.landleaf.ibsaas.web.web.controller.knight;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.knight.TBuilding;
import com.landleaf.ibsaas.common.domain.knight.TDoor;
import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.knight.IBuildingService;
import com.landleaf.ibsaas.web.web.service.knight.IDoorService;
import com.landleaf.ibsaas.web.web.service.knight.IFloorService;
import com.landleaf.ibsaas.web.web.vo.BuildingReponseVO;
import com.landleaf.ibsaas.web.web.vo.FloorReponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/knight")
@Api(value = "/knight", description = "门位置业务相关操作")
public class DoorController extends BasicController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DoorController.class);

    @Autowired
    private IBuildingService iBuildingService;
    @Autowired
    private IFloorService iFloorService;
    @Autowired
    private IDoorService iDoorService;


    @GetMapping("/v1/door/getDoorInfoAll")
    @ApiOperation(value = "获取楼栋所有信息", notes = "获取楼栋所有信息")
    public Response<List<BuildingReponseVO>> getBuildingInfoAll() {
        List<BuildingReponseVO> list = iBuildingService.getBuildingAllInfo();
        return returnSuccess(list);
    }

    @GetMapping("/v1/door/getBuildingList")
    @ApiOperation(value = "获取楼栋信息列表<id,name>")
    public Response<Map<Long,String>> getBuildingList() {
        List<TBuilding> list = iBuildingService.getBuildingList();
        Map<Long,String> map = new HashMap<>(list.size());
        for (TBuilding tBuilding : list) {
            map.put(tBuilding.getId(),tBuilding.getName());
        }
        return returnSuccess(map);
    }
    @GetMapping("/v1/door/getFloorListByParentId/{buildingId}")
    @ApiOperation(value = "根据楼栋id获取楼层信息列表<id,name>")
    public Response<Map<Long,String>> getFloorListByParentId(@PathVariable @ApiParam(name="buildingId",value="楼栋id",required=true) Long buildingId) {
        List<TFloor> list = iFloorService.getFloorListByParentId(buildingId);
        Map<Long,String> map = new HashMap<>(list.size());
        for (TFloor tFloor : list) {
            map.put(tFloor.getId(),tFloor.getName());
        }
        return returnSuccess(map);
    }

    @GetMapping("/v1/door/getDoorControlList")
    @ApiOperation(value = "获取门位置信息列表<id,name>")
    public Response<Map<Long,String>> getDoorControlList() {
        List<TDoor> list = iDoorService.getDoorControlList();
        Map<Long,String> map = new HashMap<>(list.size());
        for (TDoor tDoor : list) {
            map.put(tDoor.getId(),tDoor.getName());
        }
        return returnSuccess(map);
    }

    @GetMapping("/v1/door/getDoorInfoByControlIds")
    @ApiOperation(value = "根据门禁id获取门位置信息列表")
    public Response<List<TDoor>> getDoorInfoByControlIds(List<Long> controlIds) {
        List<TDoor> list = iDoorService.getDoorInfoByControlIds(controlIds);
        return returnSuccess(list);
    }

    @GetMapping("/v1/door/getFloorDoorsByflooId/{floorId}")
    @ApiOperation(value = "获取楼层所有门信息", notes = "获取楼栋所有信息")
    public Response<FloorReponseVO> getFloorDoorsByflooId(@PathVariable @ApiParam(name="floorId",value="楼层id",required=true) Long floorId){
        FloorReponseVO floorReponseVO = iFloorService.getFloorAllById(floorId);
        return returnSuccess(floorReponseVO);
    }
    @GetMapping("/v1/door/getDoorAllInfobyControlId/{controlId}")
    @ApiOperation(value = "根据门禁ID获取所有信息", notes = "")
    public Response<BuildingReponseVO> getDoorAllInfobyControlId(@PathVariable @ApiParam(name="controlId",value="门禁id",required=true) Long controlId){
        BuildingReponseVO buildingReponseVO = iDoorService.getDoorAllInfobyControlId(controlId);
        return returnSuccess(buildingReponseVO);
    }

    @PostMapping("/v1/door/addOrUpdateBuilding")
    @ApiOperation(value = "添加或者修改楼栋信息")
    public Response<TBuilding> addOrUpdateBuilding(@RequestBody TBuilding tBuilding) {
        log.info("DoorController ----->addOrUpdateBuilding TBuilding:{}", JSONObject.toJSONString(tBuilding));
        TBuilding tBuilding1 = iBuildingService.addBuildingOrUpdate(tBuilding);
        return returnSuccess(tBuilding1);
    }

    @PostMapping("/v1/door/addOrUpdateFloor")
    @ApiOperation(value = "添加或者修改楼层信息", notes = "添加或者修改楼层信息")
    public Response<TFloor> addOrUpdateFloor(@RequestBody TFloor tFloor) {
        TFloor tFloor1 = iFloorService.addFloorOrUpdate(tFloor);
        return returnSuccess(tFloor1);
    }

    @PostMapping("/v1/door/addOrUpdateDoor")
    @ApiOperation(value = "添加或者修改门信息", notes = "添加或者修改楼层信息")
    public Response<TDoor> addOrUpdateDoor(@RequestBody TDoor tDoor) {
        TDoor tDoor1 = iDoorService.addDoorOrUpdate(tDoor);
        return returnSuccess(tDoor1);
    }

    @PostMapping("/v1/door/bacthAddOrUpdateFloor")
    @ApiOperation(value = "批量添加或者修改门信息")
    public Response<List<TDoor>> bacthAddOrUpdateFloor(@RequestBody List<TDoor> tDoors) {
        List<TDoor> tDoorList = iDoorService.bacthAddOrUpdateFloor(tDoors);
        return returnSuccess(tDoorList);
    }

    @PostMapping("/v1/deleteBuilding/{id}")
    @ApiOperation(value = "删除楼栋信息", notes = "")
    public Response deleteBuilding( @PathVariable @ApiParam(name="id",value="楼栋id",required=true) Long id){
        iBuildingService.deleteBuilding(id);
        return returnSuccess();
    }

    @PostMapping("/v1/deleteFloor/{id}")
    @ApiOperation(value = "删除楼层信息", notes = "")
    public Response deleteFloor( @PathVariable @ApiParam(name="id",value="楼层id",required=true) Long id){
        iFloorService.deleteFloor(id);
        return returnSuccess();
    }

    @PostMapping("/v1/deleteDoor/{id}")
    @ApiOperation(value = "删除门信息", notes = "")
    public Response deleteDoor( @PathVariable @ApiParam(name="id",value="门id",required=true) Long id){
        iDoorService.deleteDoor(id);
        return returnSuccess();
    }

    @PostMapping("/v1/bindingDoorControl")
    @ApiOperation(value = "门禁绑定", notes = "门禁绑定")
    public Response bindingDoorControl(@RequestParam(value = "id")@ApiParam(name="id",value="门id",required=true) Long id , @RequestParam(value = "controId") @ApiParam(name="controId",value="门禁id",required=true)Long controId){
        iDoorService.bindingDoorControl(id,controId);
        return returnSuccess();
    }









}
