package com.landleaf.ibsaas.common.enums.knight;


import com.landleaf.ibsaas.common.domain.knight.attendance.AddAttendanceRecordDTO;
import com.landleaf.ibsaas.common.domain.knight.attendance.QueryAttendanceRecordDTO;
import com.landleaf.ibsaas.common.domain.knight.attendance.QueryAttendanceResultDTO;
import com.landleaf.ibsaas.common.domain.knight.control.*;
import com.landleaf.ibsaas.common.domain.knight.depart.AddDepartDTO;
import com.landleaf.ibsaas.common.domain.knight.depart.DeleteDepartDTO;
import com.landleaf.ibsaas.common.domain.knight.depart.QueryDepartDTO;
import com.landleaf.ibsaas.common.domain.knight.emply.*;
import com.landleaf.ibsaas.common.enums.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * TCPMessage子消息类型
 */
public enum KnightSubMsgTypeEnum implements BaseEnum {
    QUERY_DEPART(1,2, "queryDepart", "分页查询部门"),
    ADD_DEPART(2,2, "addDepart", "添加部门"),
    DELETE_DEPART(3,2, "deleteDepart", "删除部门"),
    GET_ALL_EMPLY_LIST(4, 2,"getAllEmplyList", "全部人员"),
    SELECT_EMPLY(5, 2,"selectEmply", "分页查询人员"),
    ADD_EMPLY(6, 2,"addEmply", "添加人员"),
    DELETE_EMPLY(7, 2,"deleteEmply", "删除人员"),
    SEND_CARD(8, 2,"sendCard", "发卡"),
    DELETE_CARD(9, 2,"deleteCard", "删卡"),
    UPDATE_EMPLY(11, 2,"updateEmply", "修改人员"),
    REGISTER_USER(12, 2,"registeruser", "注册人员"),
    UN_REGISTER_USER(13,2, "unregisteruser", "解除人员权限"),
    GET_MJ_DEVICE_ALL(14, 2,"getMjDeviceAll", "获取全部设备信息"),
    GET_MJ_DEVICE_BY_ID(15,2, "getMjDeviceById","根据设备id获取设备信息"),
    GET_DOOR_INFO_ALL(16, 2,"getDoorInfoAll", "获取全部门信息分页查询"),
    GET_DOOR_INFO_BY_ID(17,2, "getDoorInfoById", "根据门id获取门信息"),
    MJ_OPEN_DOOR_RECORD_DB(18, 2,"mjOpenDoorRecordByDb", "门禁记录分页查询"),
    MJ_URGENT_EVENT_RECORD(19,2, "mJUrgentEventRecord","门禁报警记录分页查询"),
    GET_ATTENDANCE_RESULT(20, 2,"getAttendanceResult", "查询考勤核算"),
    GET_ATTENDANCE_RECORD(21,2, "getAttendanceRecord", "考勤打卡记录分页查询"),
    SET_ATTENDANCE_RECORD(22,2, "setAttendanceRecord", "添加打卡记录"),
    REGISTER_USER_BY_DB(23, 2,"registeruserByDb", "注册人员2"),
    UN_REGISTER_USER_BY_DB(24,2, "unregisteruserByDb", "解除人员权限2"),
    QUERY_REGISTER_USER_BY_DB(25,2, "queryRegisteruserByDb", "查询人员权限"),
    GET_MJ_DEVICE_BY_IDS_DB(26,2, "getMjDeviceByIdsDb", "根据设备ids获取设备信息"),
    GET_MJ_DEVICE_BY_PAGE_DB(27,2, "getMjDeviceByPageDb", "分页查询设备列表"),
    ;
    public final int type;
    public int pid;
    public String name;
    public String des;

    KnightSubMsgTypeEnum(int type, int pid, String name, String des) {
        this.type = type;
        this.pid=pid;
        this.name = name;
        this.des=des;
    }
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public int getType() {
        return this.type;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    private static Map<Integer, KnightSubMsgTypeEnum> map = new HashMap<Integer, KnightSubMsgTypeEnum>();;

    static {
        for(KnightSubMsgTypeEnum enumObj : KnightSubMsgTypeEnum.values()){
            map.put(enumObj.getType(), enumObj);
        }
    }

    public static KnightSubMsgTypeEnum getInstByType(int type){
        return map.get(type);
    }
}
