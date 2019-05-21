package com.landleaf.ibsaas.web.web.controller.knight;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.dto.knight.attendance.WebAddAttendanceRecordDTO;
import com.landleaf.ibsaas.web.web.dto.knight.control.*;
import com.landleaf.ibsaas.web.web.dto.knight.depart.WebAddDepartDTO;
import com.landleaf.ibsaas.web.web.dto.knight.depart.WebDeleteDepartDTO;
import com.landleaf.ibsaas.web.web.dto.knight.depart.WebQueryDepartDTO;
import com.landleaf.ibsaas.web.web.dto.knight.emply.*;
import com.landleaf.ibsaas.web.web.dto.knight.role.WebQueryRoleDTO;
import com.landleaf.ibsaas.web.web.dto.knight.userrole.WebMjUserRoleDTO;
import com.landleaf.ibsaas.web.web.service.knight.IKnightServeice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 门禁业务相关操作控制器
 */
@RestController
@RequestMapping("/knight")
@Api(value = "/knight", description = "门禁业务相关操作")
public class KnightController extends BasicController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightController.class);


    @Autowired
    private IKnightServeice knightServeice;

    @PostMapping("door/getDoorInfoAll")
    @ApiOperation(value = "获取全部门信息分页查询", notes = "获取全部门信息分页查询")
    public Response getDoorInfoAll(@RequestBody WebQueryMjDoorDTO requestBody) {
        Response result = new Response();

        result = knightServeice.getDoorInfoAllByCondition(requestBody.getDoorName(), requestBody.getPage(), requestBody.getLimit());

        return result;

    }

    @PostMapping("event/mJUrgentEventRecord")
    @ApiOperation(value = "门禁报警记录分页查询", notes = "门禁报警记录分页查询")
    public Response mJUrgentEventRecord(@RequestBody WebQueryMjUrgentEventRecordDTO requestBody) {

        Response response = knightServeice.mJUrgentEventRecord(requestBody.getDoorName(), requestBody.getStart(), requestBody.getEnd(),
                requestBody.getPage(), requestBody.getLimit());

        return response;
    }

    @PostMapping("door/mjOpenDoorRecord")
    @ApiOperation(value = "进出记录分页查询", notes = "进出记录分页查询")
    public Response mjOpenDoorRecord(@RequestBody WebQueryMjDoorOpenRecordDTO requestBody) {
        Response result = knightServeice.mjOpenDoorRecord(requestBody.getStart(), requestBody.getEnd(), requestBody.getPage(), requestBody.getLimit());
        return result;
    }

    @PostMapping("role/mjRoles")
    @ApiOperation(value = "门禁权限分页查询", notes = "门禁权限分页查询")
    public Response mjRoles(@RequestBody WebQueryRoleDTO requestBody) {
        Response result = knightServeice.mjRoles(requestBody.getName(), requestBody.getDepartId(), requestBody.getPage(), requestBody.getLimit());
        return result;
    }


    @ApiOperation(value = "分页查询部门", notes = "分页查询部门")
    @PostMapping("depart/queryDepart")
    public Response queryDepart(@RequestBody WebQueryDepartDTO requestBody) {
        Response result = new Response();
        result = knightServeice.queryDepart(requestBody.getPage(), requestBody.getLimit());
        return result;
    }

    @PostMapping("depart/addDepart")
    @ApiOperation(value = "添加部门", notes = "添加部门")
    public Response addDepart(@RequestBody WebAddDepartDTO requestBody) {

        Response result = new Response();
        result = knightServeice.addDepart(requestBody);
        return result;
    }

    @PostMapping("depart/deleteDepart")
    @ApiOperation(value = "删除部门", notes = "删除部门")
    public Response deleteDepart(@RequestBody WebDeleteDepartDTO requestBody) {
        Response result = new Response();
        result = knightServeice.deleteDepart(requestBody);

        return result;
    }


    @PostMapping("emply/selectEmply")
    @ApiOperation(value = "分页查询人员", notes = "分页查询人员")
    public Response selectEmply(@RequestBody WebQueryEmplyDTO requestBody) {
        Response result = new Response();
        result = knightServeice.selectEmply(requestBody);
        return result;
    }

    @PostMapping("emply/addEmply")
    @ApiOperation(value = "添加人员", notes = "添加人员")
    public Response addEmply(@RequestBody WebAddEmplyDTO requestBody) {
        Response result = new Response();
        result = knightServeice.addEmply(requestBody);
        return result;
    }

    @PostMapping("emply/deleteEmply")
    @ApiOperation(value = "删除人员", notes = "删除人员")
    public Response deleteEmply(@RequestBody WebDeleteEmplyDTO requestBody) {
        Response result = new Response();
        result = knightServeice.deleteEmply(requestBody);
        return result;
    }

    @PostMapping("emply/updateEmply")
    @ApiOperation(value = "修改人员", notes = "修改人员")
    public Response updateEmply(@RequestBody WebUpdateEmplyDTO requestBody) {
        Response result = new Response();
        result = knightServeice.updateEmply(requestBody);
        return result;
    }

    @PostMapping("card/sendCard")
    @ApiOperation(value = "发卡", notes = "发卡")
    public Response sendCard(@RequestBody WebSendCardDTO requestBody) {
        Response result = new Response();
        result = knightServeice.sendCard(requestBody);
        return result;
    }

    @PostMapping("card/deleteCard")
    @ApiOperation(value = "删卡", notes = "删卡")
    public Response deleteCard(@RequestBody WebDeleteCardDTO requestBody) {
        Response result = new Response();
        result = knightServeice.deleteCard(requestBody);
        return result;
    }

    @PostMapping("reguser/registeruser")
    @ApiOperation(value = "注册人员", notes = "注册人员")
    public Response registeruser(@RequestBody WebRegisterUserDTO requestBody) {
        Response result = new Response();
        result = knightServeice.registeruser(requestBody);
        return result;
    }


    @PostMapping("reguser/unregisteruser")
    @ApiOperation(value = "解除人员权限", notes = "解除人员权限")
    public Response unregisteruser(@RequestBody WebUnRegisterUserDTO requestBody) {

        Response result = new Response();
        result = knightServeice.unregisteruser(requestBody);
        return result;
    }

    @PostMapping("emply/bind-role")
    @ApiOperation(value = "人员绑定角色", notes = "人员绑定角色")
    public Response bindRole(@RequestBody WebMjUserRoleDTO requestBody) {

        Response result = new Response();
        result = knightServeice.bindRole(requestBody);
        return result;
    }

    @PostMapping("reguser/queryRegisteruserByDb")
    @ApiOperation(value = "查询人员权限", notes = "查询人员权限")
    public Response queryRegisteruserByDb(@RequestBody WebQueryRegisterUserByDbDTO requestBody) {
        Response result = new Response();
        result = knightServeice.queryRegisteruserByDb(requestBody);
        return result;
    }


    @PostMapping("mjDevice/getMjDeviceByPageDb")
    @ApiOperation(value = "分页查询设备列表", notes = "分页查询设备列表")
    public Response getMjDeviceByPageDb(@RequestBody WebQueryMjDeviceDTO requestBody) {
        Response result = new Response();
        result = knightServeice.getMjDeviceByPageDb(requestBody.getPage(), requestBody.getLimit());

        return result;

    }

    @PostMapping("mjDevice/getMjDeviceById")
    @ApiOperation(value = "根据设备id获取设备信息", notes = "根据设备id获取设备信息")
    public Response getMjDeviceById(@RequestBody WebQueryMjDeviceDTO requestBody) {
        Response result = new Response();
        result = knightServeice.getMjDeviceById(requestBody.getDeviceSysId());

        return result;

    }


    @PostMapping("door/getDoorInfoById")
    @ApiOperation(value = "根据门id获取门信息", notes = "根据门id获取门信息")
    public Response getDoorInfoById(@RequestBody WebQueryMjDoorByIdDTO requestBody) {
        Response result = new Response();
        result = knightServeice.getDoorInfoById(requestBody.getDoorId());
        return result;

    }


    @PostMapping("attendance/setAttendanceRecord")
    @ApiOperation(value = "添加打卡记录", notes = "添加打卡记录")
    public Response setAttendanceRecord(@RequestBody WebAddAttendanceRecordDTO requestBody) {

        Response result = new Response();
        result = knightServeice.setAttendanceRecord(requestBody);

        return result;
    }


}
