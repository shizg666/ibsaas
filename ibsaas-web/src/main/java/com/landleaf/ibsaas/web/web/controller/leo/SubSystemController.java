package com.landleaf.ibsaas.web.web.controller.leo;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.leo.SubSystem;
import com.landleaf.ibsaas.web.web.constant.MessageConstants;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.dto.request.SelectorParamsDto;
import com.landleaf.ibsaas.web.web.service.leo.ISubSystemService;
import com.landleaf.ibsaas.web.web.vo.SubSystemQueryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 子系统管理控制器
 */
@RestController
@RequestMapping("/sub-systems")
@Api(value = "/sub-systems", description = "子系统相关操作")
public class SubSystemController extends BasicController {

    @Autowired
    private ISubSystemService systemService;

    /**
     * @description 据条件查询子系统列表
     * @param queryVO
     * @author wyl
     * @date 2019/3/21 0021 13:48
     * @return com.landleaf.leo.web.dto.response.Response
     * @version 1.0
    */
    @ApiOperation(value = "获取子系统列表", notes = "获取子系统列表")
    @RequestMapping(value = "/v1", method = RequestMethod.GET)
    public Response listSystems(SubSystemQueryVO queryVO) {
        return returnSuccess(systemService.listSubSystems(queryVO));
    }

    /**
     * @description 新增子系统
     * @param subSystem
     * @author wyl
     * @date 2019/3/21 0021 13:48
     * @return com.landleaf.leo.web.dto.response.Response
     * @version 1.0
    */
    @RequestMapping(value = "/v1", method = RequestMethod.POST)
    public Response addSubSystem(@RequestBody SubSystem subSystem) {
        return returnSuccess(systemService.addSubSystem(subSystem), MessageConstants.COMMON_ADD_SUCCESS_MESSAGE);
    }

    /**
     * @param id
     * @param subSystem
     * @return
     * @author wyl
     * @date 2017年08月17日09:08:49
     * @description:修改子系统
     */
    @RequestMapping(value = "/v1/id/{id}", method = RequestMethod.PUT)
    public Response updateSubSystem(@PathVariable String id, @RequestBody SubSystem subSystem) {
        return returnSuccess(systemService.updateSubSystem(id, subSystem), MessageConstants.COMMON_UPDATE_SUCCESS_MESSAGE);
    }

    /**
     * @param id
     * @return
     * @author wyl
     * @date 2017年08月17日09:10:45
     * @description:删除子系统
     */
    @RequestMapping(value = "/v1/id/{id}", method = RequestMethod.DELETE)
    public Response updateSubSystem(@PathVariable String id) {
        systemService.deleteSubSystem(id);
        return returnSuccess(MessageConstants.COMMON_DELETE_SUCCESS_MESSAGE);
    }

    /**
     * @param:
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 17:09
     * @description:提供给系统选择下拉框公共组件，查询所有系统列表
     */
    @ApiOperation(value = "查询所有系统列表", notes = "查询所有系统列表，返回编码及名称")
    @RequestMapping(value = "/v1/display", method = RequestMethod.GET)
    public Response listSystemsToDisplay() {
        return returnSuccess(systemService.listSystemsToDisplay());
    }

    /**
     * @param id
     * @return
     * @author Dy
     * @createDate 2017/8/22
     * @description:按id查询子系统对象信息
     */
    @ApiOperation(value = "按主键id查询系统信息", notes = "按主键id查询系统信息")
    @RequestMapping(value = "/v1/subSystemInfo", method = RequestMethod.GET)
    public Response querySubSystemById(String id){
        return returnSuccess(systemService.querySubSystemById(id));
    }

    /**
     * @param paramsDto
     * @return
     * @author Dy
     * @createDate 2017/8/27
     * @description:通用分页模糊查询系统名称列表
     */
    @ApiOperation(value = "分页模糊查询系统名称列表", notes = "分页模糊查询系统名称列表")
    @RequestMapping(value = "/v1/systemName", method = RequestMethod.GET)
    public Response querySubsystemList(SelectorParamsDto paramsDto){
        return returnSuccess(systemService.querySubsystemList(paramsDto));
    }

   /**
    * @description 查询系统所有编码列表
    * @author wyl
    * @date 2019/3/21 0021 13:42
    * @return com.landleaf.leo.web.dto.response.Response
    * @version 1.0
   */
    @ApiOperation(value = "查询系统编码列表", notes = "查询系统编码列表")
    @RequestMapping(value = "/v1/systemCode", method = RequestMethod.GET)
    public Response querySubsystemCodeList(){
        return returnSuccess(systemService.querySubsystemCodeList());
    }
}
