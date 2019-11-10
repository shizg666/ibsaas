package com.landleaf.ibsaas.web.web.controller.leo;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.leo.Resource;
import com.landleaf.ibsaas.common.domain.leo.User;
import com.landleaf.ibsaas.web.web.constant.MessageConstants;
import com.landleaf.ibsaas.web.web.context.user.UserContext;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.dto.request.SelectorParamsDto;
import com.landleaf.ibsaas.web.web.service.leo.IResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 权限菜单操作前端控制器
 */
@RestController
@RequestMapping("/resources")
@Api(value = "/resources", description = "权限相关操作")
public class ResourceController extends BasicController {

    @Autowired
    private IResourceService resourceService;

    /**
     * @description 列出当前登录用户在指定系统下的所有功能列表
     * @param systemCode
     * @author wyl
     * @date 2019/3/21 0021 13:47
     * @return com.landleaf.leo.web.dto.response.Response
     * @version 1.0
    */
    @ApiOperation(value = "列出当前登录用户在指定系统下的所有功能列表", notes = "列出当前登录用户在指定系统下的所有功能列表")
    @RequestMapping(value = "/v1/user/system/{systemCode}", method = RequestMethod.GET)
    public Response listUserResources(@PathVariable String systemCode) {
        return returnSuccess(resourceService.listUserAllResources(systemCode, ((User) UserContext.getCurrentUser()).getUserCode()));
    }

   /**
    * @description 根据系统编码查询所属权限列表
    * @param systemCode
    * @author wyl
    * @date 2019/3/21 0021 13:47
    * @return com.landleaf.leo.web.dto.response.Response
    * @version 1.0
   */
    @ApiOperation(value = "根据系统编码查询所属权限列表", notes = "根据系统编码查询所属权限列表，返回前端所需要的树状结构")
    @RequestMapping(value = "/v1/system/{systemCode}", method = RequestMethod.GET)
    public Response listResources(@PathVariable String systemCode) {
        return returnSuccess(resourceService.listAllResourcesBySystem(systemCode));
    }

    /**
     * @description 根据权限编码,所属系统查询权限详细信息
     * @param resourceCode
     * @param systemCode
     * @author wyl
     * @date 2019/3/21 0021 13:47
     * @return com.landleaf.leo.web.dto.response.Response
     * @version 1.0
    */
    @ApiOperation(value = "根据权限编码,所属系统查询权限详细信息", notes = "根据权限编码,所属系统查询权限详细信息")
    @RequestMapping(value = "/v1/resource/{resourceCode}/system/{systemCode}", method = RequestMethod.GET)
    public Response findResource(@PathVariable String resourceCode, @PathVariable String systemCode) {
        return returnSuccess(resourceService.findResourceByCode(resourceCode, systemCode));
    }

    /**
     * @description 根据所属系统编码权限名称模糊查询权限列表
     * @param systemCode
     * @param resourceName
     * @author wyl
     * @date 2019/3/21 0021 13:47
     * @return com.landleaf.leo.web.dto.response.Response
     * @version 1.0
    */
    @ApiOperation(value = "根据所属系统编码权限名称模糊查询权限列表", notes = "根据所属系统编码权限名称模糊查询权限列表，返回编码，名称集合，用于页面所属系统下拉框")
    @RequestMapping(value = "/v1/resource/name/system/{systemCode}", method = RequestMethod.GET)
    public Response findResourceByName(@PathVariable String systemCode, @RequestParam(value = "query", required = false) String resourceName) {
        return returnSuccess(resourceService.findResourceByNameToDisplay(systemCode, resourceName));
    }

    /**
     * @param:resource
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 16:57
     * @description:新增权限
     */
    @ApiOperation(value = "新增权限", notes = "新增权限")
    @RequestMapping(value = "/v1/resource", method = RequestMethod.POST)
    public Response addResource(@RequestBody @ApiParam Resource resource) {
        return returnSuccess(resourceService.addResource(resource), MessageConstants.COMMON_ADD_SUCCESS_MESSAGE);
    }

    /**
     * @param:id
     * @param:resource
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 16:57
     * @description:修改权限
     */
    @ApiOperation(value = "修改权限", notes = "修改权限")
    @RequestMapping(value = "/v1/resource/{id}", method = RequestMethod.PUT)
    public Response updateResource(@PathVariable String id, @RequestBody @ApiParam Resource resource) {
        return returnSuccess(resourceService.updateResource(id, resource), MessageConstants.COMMON_UPDATE_SUCCESS_MESSAGE);
    }

    /**
     * @param:id
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 16:57
     * @description:根据数据库id删除指定权限 逻辑删除
     */
    @ApiOperation(value = "根据主键删除权限", notes = "根据主键id删除权限")
    @RequestMapping(value = "/v1/resource/{id}", method = RequestMethod.DELETE)
    public Response deleteResource(@PathVariable String id) {
        resourceService.deleteResource(id);
        return returnSuccess(null, MessageConstants.COMMON_DELETE_SUCCESS_MESSAGE);
    }

    /**
     * @param:paramsDto
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 16:58
     * @description:通用方法根据code 返回一个菜单集合
     */
    @ApiOperation(value = "根据权限名称模糊查询权限列表", notes = "根据权限名称模糊查询权限列表，返回编码，名称集合，用于页面所属系统下拉框")
    @RequestMapping(value = "/v1/resourceList", method = RequestMethod.GET)
    public Response queryResource(@ApiParam SelectorParamsDto paramsDto) {
        return returnSuccess(resourceService.queryResource(paramsDto));
    }
}
