package com.landleaf.ibsaas.web.web.service.knight;

import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.Response;

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
    Response mJUrgentEventRecord(String doorName, String start, String end, int page, int limit);
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
}
