package com.landleaf.ibsaas.web.web.controller.knight;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.knight.role.MjRole;
import com.landleaf.ibsaas.web.web.constant.MessageConstants;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.knight.IFloorService;
import com.landleaf.ibsaas.web.web.service.knight.MjRoleService;
import com.landleaf.ibsaas.web.web.vo.MjRoleRequestVO;
import com.landleaf.ibsaas.web.web.vo.RoleFloorDoorsReponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/knight")
@Api(value = "/knight", description = "角色相关操作")
public class MjRoleController extends BasicController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MjRoleController.class);
    @Autowired
    private IFloorService iFloorService;

    @Autowired
    private MjRoleService mjRoleService;

    @GetMapping("/v1/mjRole/getRoleDoorInfo")
    @ApiOperation(value = "查询某一角色在某一楼层所拥有的门禁信息()")
    public Response<RoleFloorDoorsReponseVO> getfloorDoorByRoleId(@RequestParam(value = "floorId")@ApiParam(name="floorId",value="楼层id",required=true) Long floorId , @RequestParam(value = "roleId",required = false) @ApiParam(name="roleId",value="角色id")String roleId) {
        RoleFloorDoorsReponseVO roleFloorDoorsReponseVO = iFloorService.getfloorControlDoorByRoleId(floorId,roleId);
        return returnSuccess(roleFloorDoorsReponseVO);
    }

    @ApiOperation(value = "根据角色主健id查询", notes = "根据角色主健id查询")
    @RequestMapping(value = "/v1/mjRole/{id}", method = RequestMethod.GET)
    public Response<MjRole> getMjRoleInfoById(@PathVariable @ApiParam(name="id",value="角色id",required=true) String id) {
        MjRole role = mjRoleService.selectByPrimaryKey(id);
        return returnSuccess(role);
    }
//    @ApiOperation(value = "修改角色信息", notes = "修改角色信息")
//    @PostMapping(value = "/v1/mjRole/updateMjRole")
//    public Response updateMjRole(@RequestBody @ApiParam MjRoleRequestVO mjRoleRequestVO) {
//        Integer result = mjRoleService.updateMjRoleDooorInfo(mjRoleRequestVO);
//        return returnSuccess(result, MessageConstants.COMMON_UPDATE_SUCCESS_MESSAGE);
//    }
//
//    @ApiOperation(value = "添加角色信息", notes = "添加角色信息")
//    @PostMapping(value = "/v1/mjRole/addMjRole")
//    public Response addMjRole(@RequestBody @ApiParam MjRoleRequestVO mjRoleRequestVO) {
//        Integer result = mjRoleService.addMjRoleDooorInfo(mjRoleRequestVO);
//        return returnSuccess(result, MessageConstants.COMMON_ADD_SUCCESS_MESSAGE);
//    }

    @ApiOperation(value = "添加或者修改角色信息", notes = "添加角色信息")
    @PostMapping(value = "/v1/mjRole/addOrUpdateMjRole")
    public Response addOrUpdateMjRole(@RequestBody @ApiParam MjRoleRequestVO mjRoleRequestVO) {
        String message;
        if (mjRoleRequestVO.getId() == null || mjRoleRequestVO.getId() ==""){
            message ="添加成功！";
        }else {
            message = "修改成功！";
        }
        MjRole mjRole = mjRoleService.addOrUpdateMjRole(mjRoleRequestVO);
        return returnSuccess(mjRole, message);
    }

    @PostMapping("/v1/deleteMjRole/{id}")
    @ApiOperation(value = "根据角色id删除门禁角色", notes = "")
    public Response deleteMjRole( @PathVariable @ApiParam(name="id",value="角色id",required=true) String id){
        Integer result = mjRoleService.deleteMjRoleDooorInfo(id);
        return returnSuccess(result, MessageConstants.COMMON_DELETE_SUCCESS_MESSAGE);
    }

}
