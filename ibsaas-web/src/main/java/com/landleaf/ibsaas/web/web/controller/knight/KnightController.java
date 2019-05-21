/*
package com.landleaf.ibsaas.web.web.controller.knight;

import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.knight.attendance.AddAttendanceRecordDTO;
import com.landleaf.ibsaas.common.domain.knight.attendance.QueryAttendanceRecordDTO;
import com.landleaf.ibsaas.common.domain.knight.attendance.QueryAttendanceResultDTO;
import com.landleaf.ibsaas.common.domain.knight.control.QueryMjDeviceDTO;
import com.landleaf.ibsaas.common.domain.knight.control.*;
import com.landleaf.ibsaas.common.domain.knight.control.RegisterUserByDbDTO;
import com.landleaf.ibsaas.common.domain.knight.control.RegisterUserDTO;
import com.landleaf.ibsaas.common.domain.knight.control.UnRegisterUserByDbDTO;
import com.landleaf.ibsaas.common.domain.knight.control.UnRegisterUserDTO;
import com.landleaf.ibsaas.common.domain.knight.depart.AddDepartDTO;
import com.landleaf.ibsaas.common.domain.knight.depart.DeleteDepartDTO;
import com.landleaf.ibsaas.common.domain.knight.depart.QueryDepartDTO;
import com.landleaf.ibsaas.common.domain.knight.emply.*;
import com.landleaf.ibsaas.common.enums.knight.KnightSubMsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.MsgTypeEnum;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.dto.knight.control.*;
import com.landleaf.ibsaas.web.web.dto.knight.emply.WebQueryEmplyDTO;
import com.landleaf.ibsaas.web.web.dto.knight.role.WebQueryRoleDTO;
import com.landleaf.ibsaas.web.web.service.knight.IKnightServeice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

*/
/**
 * 门禁业务相关操作控制器
 *//*

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
    public Response queryDepart(@RequestBody QueryDepartDTO requestBody) {
        Response result = new Response();

        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.QUERY_DEPART.getName());


        return result;
    }


    @PostMapping("depart/addDepart")
    @ApiOperation(value = "添加部门", notes = "添加部门")
    public Response addDepart(@RequestBody AddDepartDTO requestBody) {

        Response result = new Response();

        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.ADD_DEPART.getName());


        return result;
    }

    @PostMapping("depart/deleteDepart")
    @ApiOperation(value = "删除部门", notes = "删除部门")
    public Response testDeleteDepart(@RequestBody DeleteDepartDTO requestBody) {
        Response result = new Response();

        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.DELETE_DEPART.getName());


        return result;
    }

    @PostMapping("emply/getAllEmplyList")
    @ApiOperation(value = "全部人员", notes = "全部人员")
    public Response getAllEmplyList(@RequestBody QueryEmplyDTO requestBody) {
        Response result = new Response();

        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.GET_ALL_EMPLY_LIST.getName());


        return result;
    }

    @PostMapping("emply/selectEmply")
    @ApiOperation(value = "分页查询人员", notes = "分页查询人员")
    public Response selectEmply(@RequestBody WebQueryEmplyDTO requestBody) {
        Response result = new Response();
        QueryRegisterUserByDbDTO sendRequest = new QueryRegisterUserByDbDTO();
        BeanUtils.copyProperties(requestBody, sendRequest);
        sendRequest.setCurPage(requestBody.getPage());
        sendRequest.setPageSize(requestBody.getLimit());
        result = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.SELECT_EMPLY.getName());
        return result;
    }

    @PostMapping("emply/addEmply")
    @ApiOperation(value = "添加人员", notes = "添加人员")
    public Response addEmply(@RequestBody AddEmplyDTO requestBody) {
        Response result = new Response();

        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.ADD_EMPLY.getName());


        return result;
    }

    @PostMapping("emply/deleteEmply")
    @ApiOperation(value = "删除人员", notes = "删除人员")
    public Response deleteEmply(@RequestBody DeleteEmplyDTO requestBody) {
        Response result = new Response();

        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.DELETE_EMPLY.getName());


        return result;
    }

    @PostMapping("card/sendCard")
    @ApiOperation(value = "发卡", notes = "发卡")
    public Response sendCard(@RequestBody SendCardDTO requestBody) {
        Response result = new Response();

        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.SEND_CARD.getName());


        return result;
    }

    @PostMapping("card/deleteCard")
    @ApiOperation(value = "删卡", notes = "删卡")
    public Response deleteCard(@RequestBody DeleteCardDTO requestBody) {
        Response result = new Response();

        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.DELETE_CARD.getName());


        return result;
    }

    @PostMapping("emply/updateEmply")
    @ApiOperation(value = "修改人员", notes = "修改人员")
    public Response updateEmply(@RequestBody UpdateEmplyDTO requestBody) {
        Response result = new Response();

        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.UPDATE_EMPLY.getName());


        return result;
    }

    @PostMapping("reguser/registeruser")
    @ApiOperation(value = "注册人员", notes = "注册人员")
    public Response registeruser(@RequestBody RegisterUserDTO requestBody) {
        Response result = new Response();

        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.REGISTER_USER.getName());


        return result;
    }

    @PostMapping("reguser/registeruserByDb")
    @ApiOperation(value = "注册人员2", notes = "注册人员2")
    public Response registeruserByDb(@RequestBody RegisterUserByDbDTO requestBody) {
        Response result = new Response();

        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.REGISTER_USER_BY_DB.getName());


        return result;
    }

    @PostMapping("reguser/queryRegisteruserByDb")
    @ApiOperation(value = "查询人员权限", notes = "查询人员权限")
    public Response queryRegisteruserByDb(@RequestBody WebQueryRegisterUserByDbDTO requestBody) {
        Response result = new Response();
        QueryRegisterUserByDbDTO sendRequest = new QueryRegisterUserByDbDTO();
        BeanUtils.copyProperties(requestBody, sendRequest);
        sendRequest.setCurPage(requestBody.getPage());
        sendRequest.setPageSize(requestBody.getLimit());
        result = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.QUERY_REGISTER_USER_BY_DB.getName());


        return result;
    }

    @PostMapping("reguser/unregisteruser")
    @ApiOperation(value = "解除人员权限", notes = "解除人员权限")
    public Response unregisteruser(@RequestBody UnRegisterUserDTO requestBody) {

        Response result = new Response();

        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.UN_REGISTER_USER.getName());


        return result;
    }

    @PostMapping("reguser/unregisteruserByDb")
    @ApiOperation(value = "解除人员权限2", notes = "解除人员权限2")
    public Response unregisteruserByDb(@RequestBody UnRegisterUserByDbDTO requestBody) {
        Response result = new Response();

        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.UN_REGISTER_USER_BY_DB.getName());


        return result;

    }

    @PostMapping("mjDevice/getMjDeviceAll")
    @ApiOperation(value = "获取全部设备信息", notes = "获取全部设备信息")
    public Response getMjDeviceAll(@RequestBody QueryMjDeviceDTO requestBody) {
        Response result = new Response();
        knightServeice.getMjDeviceByIds();
        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.GET_MJ_DEVICE_ALL.getName());


        return result;

    }

    @PostMapping("mjDevice/getMjDeviceById")
    @ApiOperation(value = "根据设备id获取设备信息", notes = "根据设备id获取设备信息")
    public Response getMjDeviceById(@RequestBody QueryMjDeviceDTO requestBody) {
        Response result = new Response();

        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.GET_MJ_DEVICE_BY_ID.getName());


        return result;

    }


    @PostMapping("door/getDoorInfoById")
    @ApiOperation(value = "根据门id获取门信息", notes = "根据门id获取门信息")
    public Response getDoorInfoById(@RequestBody WebQueryMjDoorByIdDTO requestBody) {
        Response result = new Response();

        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.GET_DOOR_INFO_BY_ID.getName());


        return result;

    }


    @PostMapping("attendance/getAttendanceResult")
    @ApiOperation(value = "查询考勤核算", notes = "查询考勤核算")
    public Response getAttendanceResult(@RequestBody QueryAttendanceResultDTO requestBody) {
        Response result = new Response();

        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.GET_ATTENDANCE_RESULT.getName());


        return result;

    }

    @PostMapping("attendance/getAttendanceRecord")
    @ApiOperation(value = "考勤打卡记录分页查询", notes = "考勤打卡记录分页查询")
    public Response getAttendanceRecord(@RequestBody QueryAttendanceRecordDTO requestBody) {
        Response result = new Response();

        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.GET_ATTENDANCE_RECORD.getName());


        return result;

    }

    @PostMapping("attendance/setAttendanceRecord")
    @ApiOperation(value = "添加打卡记录", notes = "添加打卡记录")
    public Response setAttendanceRecord(@RequestBody AddAttendanceRecordDTO requestBody) {

        Response result = new Response();

        result = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.SET_ATTENDANCE_RECORD.getName());


        return result;
    }


}
*/
