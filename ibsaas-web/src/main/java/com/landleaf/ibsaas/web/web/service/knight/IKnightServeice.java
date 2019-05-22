package com.landleaf.ibsaas.web.web.service.knight;

import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.web.web.dto.knight.attendance.WebAddAttendanceRecordDTO;
import com.landleaf.ibsaas.web.web.dto.knight.control.WebQueryRegisterUserByDbDTO;
import com.landleaf.ibsaas.web.web.dto.knight.control.WebRegisterUserDTO;
import com.landleaf.ibsaas.web.web.dto.knight.control.WebUnRegisterUserDTO;
import com.landleaf.ibsaas.web.web.dto.knight.depart.WebAddDepartDTO;
import com.landleaf.ibsaas.web.web.dto.knight.depart.WebDeleteDepartDTO;
import com.landleaf.ibsaas.web.web.dto.knight.emply.*;
import com.landleaf.ibsaas.web.web.dto.knight.userrole.WebMjUserRoleDTO;

import java.util.List;

/**
 * 门禁业务相关操作
 */
public interface IKnightServeice {


    /**
     * 门禁分页查询
     * @param doorName   门名称
     * @param page       当前页
     * @param limit      每页最大数
     * @return
     */
    Response getDoorInfoAllByCondition(String doorName, int page, int limit);


    /**
     * 根据设备ID获取设备信息
     * @param ids
     * @return
     */
    Response getMjDeviceByIds(List<Integer> ids);

    /**
     * 门禁报警记录分页查询
     * @param doorName     门名称
     * @param start        开始时间
     * @param end          截止时间
     * @param page         当前页
     * @param limit        最大记录数
     * @return
     */
    Response mJUrgentEventRecord(String doorName,String stationName, String start, String end, int page, int limit);
    /**
     * 进出记录分页查询
     * @param start        开始时间
     * @param end          截止时间
     * @param page         当前页
     * @param limit        最大记录数
     * @return
     */
    Response mjOpenDoorRecord(String start, String end, int page, int limit);

    /**
     * 角色分页查询
     * @param name      角色名称
     * @param departId  部门
     * @param page       当前页
     * @param limit      最大记录数
     * @return
     */
    Response mjRoles(String name, Integer departId, int page, int limit);


    /**
     * 部门分页查询
     * @param page       当前页
     * @param limit      每页最大数
     * @return
     */
    Response queryDepart(int page, int limit);

    Response addDepart(WebAddDepartDTO requestBody);

    Response deleteDepart(WebDeleteDepartDTO requestBody);

    Response selectEmply(WebQueryEmplyDTO requestBody);

    Response addEmply(WebAddEmplyDTO requestBody);

    Response deleteEmply(WebDeleteEmplyDTO requestBody);

    Response sendCard(WebSendCardDTO requestBody);

    Response deleteCard(WebDeleteCardDTO requestBody);

    Response updateEmply(WebUpdateEmplyDTO requestBody);

    Response registeruser(WebRegisterUserDTO requestBody);

    Response unregisteruser(WebUnRegisterUserDTO requestBody);

    Response bindRole(WebMjUserRoleDTO requestBody);

    Response queryRegisteruserByDb(WebQueryRegisterUserByDbDTO requestBody);

    Response getMjDeviceByPageDb(int page, int limit);

    Response getMjDeviceById(Integer deviceSysId);

    Response getDoorInfoById(Integer doorId);

    Response setAttendanceRecord(WebAddAttendanceRecordDTO requestBody);

    Response queryAllDepart();
}
