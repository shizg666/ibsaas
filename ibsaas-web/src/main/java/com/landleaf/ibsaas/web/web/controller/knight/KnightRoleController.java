package com.landleaf.ibsaas.web.web.controller.knight;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.knight.IFloorService;
import com.landleaf.ibsaas.web.web.vo.BuildingReponseVO;
import com.landleaf.ibsaas.web.web.vo.FloorReponseVO;
import com.landleaf.ibsaas.web.web.vo.RoleFloorDoorsReponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/knight")
@Api(value = "/knight", description = "角色相关操作")
public class KnightRoleController extends BasicController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KnightRoleController.class);
    @Autowired
    private IFloorService iFloorService;

    @GetMapping("/v1/role/getRoleDoorInfo")
    @ApiOperation(value = "查询某一角色在某一楼层的门禁信息")
    public Response<List<BuildingReponseVO>> getfloorDoorByRoleId(@RequestParam(value = "floorId")@ApiParam(name="floorId",value="门id",required=true) Long floorId , @RequestParam(value = "roleId") @ApiParam(name="roleId",value="角色id",required=true)Long roleId) {
//        List<BuildingReponseVO> list = iBuildingService.getBuildingAllInfo();
//        RoleFloorDoorsReponseVO roleFloorDoorsReponseVO = iFloorService.getfloorDoorByRoleId(floorId,roleId);
        return returnSuccess();
    }
}
