package com.landleaf.ibsaas.web.web.controller.building;


import com.alibaba.fastjson.JSONObject;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.building.vo.BuildingCloneVO;
import com.landleaf.ibsaas.common.domain.knight.TBuilding;
import com.landleaf.ibsaas.common.domain.knight.TFloor;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.buliding.IBuildingCommonService;
import com.landleaf.ibsaas.web.web.service.buliding.IFloorCommonService;
import com.landleaf.ibsaas.web.web.vo.BuildingReponseVO;
import com.landleaf.ibsaas.web.web.vo.TBuildingVO;
import com.landleaf.ibsaas.web.web.vo.TFloorVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/building")
@Api(value = "/building", description = "楼栋操作")
@Slf4j
public class BuildingController extends BasicController {

    @Autowired
    private IBuildingCommonService iBuildingCommonService;
    @Autowired
    private IFloorCommonService iFloorCommonService;



    @GetMapping("/getBuildingInfoAll")
    @ApiOperation(value = "获取所有类型楼栋信息(包括楼层信息)", notes = "获取楼栋所有信息")
    public Response<Map<String,List<BuildingReponseVO>>> getBuildingInfoAll() {
        Map<String, List<BuildingReponseVO>> data = iBuildingCommonService.getBuildingAllInfo();
        return returnSuccess(data);
    }

    @GetMapping("/getBuildingInfoAllByType")
    @ApiOperation(value = "获取某一类型楼栋信息(包括楼层信息)(1：门禁，2：照明)", notes = "获取楼栋所有信息")
    public Response<List<BuildingReponseVO>> getBuildingInfoAllByType(@RequestParam(value = "type") Integer type) {
        List<BuildingReponseVO> list = iBuildingCommonService.getBuildingAllInfoByType(type);
        return returnSuccess(list);
    }

    @PostMapping("/addOrUpdateBuilding")
    @ApiOperation(value = "添加或者修改楼栋信息")
    public Response<TBuildingVO> addOrUpdateBuilding(@RequestBody TBuilding tBuilding) {
        log.info("BuildingController ----->addOrUpdateBuilding TBuilding:{}", JSONObject.toJSONString(tBuilding));
        TBuilding tBuilding1 = iBuildingCommonService.addBuildingOrUpdate(tBuilding);
        TBuildingVO tBuildingVO = new TBuildingVO();
        BeanUtils.copyProperties(tBuilding1,tBuildingVO);
        tBuildingVO.setKey("building_"+tBuildingVO.getId());
        return returnSuccess(tBuildingVO);
    }

    @PostMapping("/deleteBuilding/{id}")
    @ApiOperation(value = "根据主键删除楼栋信息", notes = "")
    public Response deleteBuilding( @PathVariable @ApiParam(name="id",value="楼栋id",required=true) Long id){
        iBuildingCommonService.deleteBuilding(id);
        return returnSuccess();
    }

    @PostMapping("/floor/addOrUpdateFloor")
    @ApiOperation(value = "添加或者修改楼层信息", notes = "添加或者修改楼层信息")
    public Response<TFloorVO> addOrUpdateFloor(@RequestBody TFloor tFloor) {
        TFloor tFloor1 = iFloorCommonService.addFloorOrUpdate(tFloor);
        TFloorVO tFloorVO = new TFloorVO();
        BeanUtils.copyProperties(tFloor1,tFloorVO);
        tFloorVO.setKey("floor_"+tFloorVO.getId());
        return returnSuccess(tFloorVO);
    }

    @GetMapping("/floor/getFloorListByParentId/{buildingId}")
    @ApiOperation(value = "根据楼栋id获取楼层信息列表<id,name>")
    public Response<Map<Long,String>> getFloorListByParentId(@PathVariable @ApiParam(name="buildingId",value="楼栋id",required=true) Long buildingId) {
        List<TFloor> list = iFloorCommonService.getFloorListByParentId(buildingId);
        Map<Long,String> map = new HashMap<>(list.size());
        for (TFloor tFloor : list) {
            map.put(tFloor.getId(),tFloor.getName());
        }
        return returnSuccess(map);
    }

    @PostMapping("/cloneBuilding")
    @ApiOperation(value = "复制某一类型的楼栋楼层")
    public Response<Map<Long,String>> cloneBuilding(@RequestBody BuildingCloneVO buildingCloneVO) {
        log.info("BuildingController ----->cloneBuilding TBuilding:{}", JSONObject.toJSONString(buildingCloneVO));
        List<BuildingReponseVO> buildingReponseVOS = iBuildingCommonService.cloneBuilding(buildingCloneVO);
        return returnSuccess(buildingReponseVOS);
    }


}
