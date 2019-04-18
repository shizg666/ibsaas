package com.landleaf.ibsaas.web.web.controller.leo;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.leo.Role;
import com.landleaf.ibsaas.common.domain.leo.RoleResource;
import com.landleaf.ibsaas.common.domain.leo.UserRole;
import com.landleaf.ibsaas.web.web.constant.MessageConstants;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.dto.request.SelectorParamsDto;
import com.landleaf.ibsaas.web.web.service.leo.IRoleService;
import com.landleaf.ibsaas.web.web.vo.RoleQueryInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色信息维护
 */
@RestController
@RequestMapping("/roles")
@Api(value = "/roles", description = "角色相关操作")
public class RoleController extends BasicController {

    @Autowired
    private IRoleService roleService;

    /**
     * @param userCode
     * @param systemCode
     * @return
     * @author wyl
     * @date 2017年08月13日16:11:16
     * @description:获取用户在指定系统的已分配角色列表
     */
    @ApiOperation(value = "获取用户在指定系统的已分配角色列表", notes = "获取用户在指定系统的已分配角色列表")
    @RequestMapping(value = "/v1/user/{userCode}/system/{systemCode}", method = RequestMethod.GET)
    public Response listUserRoles(@PathVariable String userCode, @PathVariable String systemCode) {
        UserRole userRole = new UserRole();
        userRole.setUserCode(userCode);
        userRole.setBelongSystem(systemCode);
        List<Role> roles = roleService.listUserRoles(userRole);
        return returnSuccess(roles);
    }

    /**
     * @param systemCode
     * @return
     * @author wyl
     * @date 2017年08月13日17:29:47.3
     * @description:获取指定系统下所有有效的角色
     */
    @ApiOperation(value = "获取指定系统下所有有效的角色", notes = "获取指定系统下所有有效的角色")
    @RequestMapping(value = "/v1/system/{systemCode}", method = RequestMethod.GET)
    public Response listSystemRoles(@PathVariable String systemCode) {
        return returnSuccess(roleService.listSystemRoles(systemCode));
    }

    /**
     * @param:
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 17:05
     * @description:根据角色名称分页模糊查询名称列表
     */
    @ApiOperation(value = "根据角色名称分页模糊查询名称列表", notes = "角色名称对应请求实体中query字段，用于角色管理界面名称搜索框")
    @RequestMapping(value = "/v1/role-list/name", method = RequestMethod.GET)
    public Response queryRole(@ApiParam SelectorParamsDto selectorParamsDto) {
        return returnSuccess(roleService.queryRole(selectorParamsDto));
    }

    /**
     * @param:
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 17:06
     * @description:根据条件分页获取角色列表
     */
    @ApiOperation(value = "条件分页获取角色列表", notes = "条件分页获取角色列表，条件为角色编码，角色名称，所属系统编码")
    @RequestMapping(value = "/v1", method = RequestMethod.GET)
    public Response listRoleQueryInfos(@ApiParam RoleQueryInfoVO roleQueryInfo) {
        return returnSuccess(roleService.listRoleQueryInfo(roleQueryInfo));
    }

    /**
     * @param:
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 17:06
     * @description:新增角色
     */
    @ApiOperation(value = "新增角色", notes = "新增角色")
    @RequestMapping(value = "/v1/role", method = RequestMethod.POST)
    public Response addRole(@RequestBody @ApiParam Role role) {
        return returnSuccess(roleService.addRole(role), MessageConstants.COMMON_ADD_SUCCESS_MESSAGE);
    }

    /**
     * @param:
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 17:06
     * @description:根据角色主健id查询
     */
    @ApiOperation(value = "根据角色主健id查询", notes = "根据角色主健id查询")
    @RequestMapping(value = "/v1/role", method = RequestMethod.GET)
    public Response findRoleInfo(@RequestParam("id") String id) {
        return returnSuccess(roleService.queryRoleById(id));
    }


    /**
     * @param:id
     * @param:role
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 17:07
     * @description:修改角色信息
     */
    @ApiOperation(value = "修改角色信息", notes = "修改角色信息")
    @RequestMapping(value = "/v1/role/{id}", method = RequestMethod.PUT)
    public Response updateRole(@PathVariable String id, @RequestBody @ApiParam Role role) {
        return returnSuccess(roleService.updateRole(id, role), MessageConstants.COMMON_UPDATE_SUCCESS_MESSAGE);
    }

    /**
     * @param:roleCode
     * @param:resourceCodes
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 17:07
     * @description:关联角色权限信息
     */
    @ApiOperation(value = "关联角色权限信息", notes = "关联角色权限信息")
    @RequestMapping(value = "/v1/role/{roleCode}/relevance/system/{systemCode}/resource", method = RequestMethod.PUT)
    public Response updateRoleResource(@PathVariable String roleCode, @PathVariable("systemCode") String systemCode, @RequestBody String[] resourceCodes) {
        roleService.updateRoleResource(roleCode, systemCode, resourceCodes);
        return returnSuccess(null, MessageConstants.COMMON_UPDATE_SUCCESS_MESSAGE);
    }

    /**
     * @param:id
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 17:08
     * @description:根据角色主键删除
     */
    @ApiOperation(value = "根据角色主键删除", notes = "根据角色主键删除")
    @RequestMapping(value = "/v1/role/{id}", method = RequestMethod.DELETE)
    public Response batchDeleteRole(@PathVariable String id) {
        roleService.deleteRoleById(id);
        return returnSuccess(null, MessageConstants.COMMON_DELETE_SUCCESS_MESSAGE);
    }

    /**
     * @param:roleCode
     * @param:systemCode
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 17:08
     * @description:获取角色下所有权限
     */
    @ApiOperation(value = "获取角色下所有权限", notes = "获取角色下所有权限")
    @RequestMapping(value = "/v1/resources/role/{roleCode}/system/{systemCode}", method = RequestMethod.GET)
    public Response listRoleResources(@PathVariable String roleCode, @PathVariable String systemCode) {
        RoleResource roleResource = new RoleResource();
        roleResource.setRoleCode(roleCode);
        roleResource.setBelongSystem(systemCode);
        return returnSuccess(roleService.listRoleAllResources(roleResource));
    }


}
