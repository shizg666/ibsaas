package com.landleaf.ibsaas.client.knight.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.landleaf.ibsaas.client.knight.restful.RestTemplateClient;
import com.landleaf.ibsaas.common.domain.knight.attendance.AttendanceRecord;
import com.landleaf.ibsaas.common.domain.knight.attendance.AttendanceResult;
import com.landleaf.ibsaas.common.domain.knight.control.Door;
import com.landleaf.ibsaas.common.domain.knight.control.MjOpenDoorRecord;
import com.landleaf.ibsaas.common.domain.knight.control.MjUrgentEventRecord;
import com.landleaf.ibsaas.common.domain.knight.control.Station;
import com.landleaf.ibsaas.common.domain.knight.depart.Depart;
import com.landleaf.ibsaas.common.domain.knight.emply.Emply;
import com.landleaf.ibsaas.common.domain.knight.KnightResponse;
import com.landleaf.ibsaas.common.utils.MessageUtil;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 立方数据提供-http方式
 */
@Component
public class LiFangHttpProvider implements InitializingBean {
    public static final Logger LOGGER = LoggerFactory.getLogger(LiFangHttpProvider.class);
    @Value("${knight.data.source.url}")
    private String preUrl;
    /**********************************部门*****************************************/
    //查询部门
    public static String QUERY_DEPART_URL = "/reformer/interface/queryDepart";
    //添加部门
    public static String ADD_DEPART_URL = "/reformer/interface/addDepart";
    //删除部门
    public static String DELETE_DEPART_URL = "/reformer/interface/deleteDepart";
    /**********************************人员*****************************************/
    //查询全部人员
    public static String GET_ALL_EMPLY_LIST_URL = "/reformer/interface/getAllEmplyList";
    //查询人员
    public static String SELECT_EMPLY_URL = "/reformer/interface/hr/selectEmply";
    //添加人员
    public static String ADD_EMPLY_URL = "/reformer/interface/hr/addEmply";
    //修改人员
    public static String UPDATE_EMPLY_URL = "/reformer/interface/hr/updateEmply";
    //删除人员
    public static String DELETE_EMPLY_URL = "/reformer/interface/hr/deleteEmply";
    //发卡
    public static String SEND_CARD_URL = "/reformer/interface/sendCard";
    //删卡
    public static String DELETE_CARD_URL = "/reformer/interface/deleteCard";
    /**********************************门禁*****************************************/
    //遥控开门
    public static String OPEN_DOOR_URL = "/reformer/interface/remotecontrol/opendoor";
    //遥控关门
    public static String CLOSE_DOOR_URL = "/reformer/interface/remotecontrol/closedoor";
    //紧急开门
    public static String URGENT_OPEN_URL = "/reformer/interface/remotecontrol/urgentopen";
    //紧急关门
    public static String URGENT_CLOSE_URL = "/reformer/interface/remotecontrol/urgentclose";
    //取消紧急
    public static String CANCEL_URGENT_URL = "/reformer/interface/remotecontrol/cancelurgent";
    //布防
    public static String SET_DEFENSE_URL = "/reformer/interface/remotecontrol/setDefense";
    //取消布防
    public static String CANCEL_DEFENSE_URL = "/reformer/interface/remotecontrol/cancelDefense";
    //注册人员
    public static String REGISTER_USER_URL = "/reformer/interface/registeruser";
    //解除人员权限
    public static String UN_REGISTER_USER_URL = "/reformer/interface/unregisteruser";
    //获取全部设备信息
    public static String GET_MJ_DEVICE_ALL_URL = "/reformer/interface/getMjDeviceAll";
    //根据设备ID获取设备信息
    public static String GET_MJ_DEVICE_BY_ID_URL = "/reformer/interface/getMjDeviceById";
    //获取全部门信息分页查询
    public static String GET_DOOR_INFO_ALL_URL = "/reformer/interface/getDoorInfoAll";
    //根据门ID获取门信息
    public static String GET_DOOR_INFO_BY_ID_URL = "/reformer/interface/getDoorInfoById";
    //门禁记录分页查询
    public static String MJ_OPEN_DOOR_RECORD_URL = "/reformer/interface/MjOpenDoorRecord";
    //门禁报警记录分页查询
    public static String MJ_URGENT_EVENT_RECORD_URL = "/reformer/interface/MJUrgentEventRecord";
    //一卡通接收推送门禁记录
    public static String SEND_OPEN_DOOR_RECORD_URL = "/reformer/interface/SendOpenDoorRecord";
    /**********************************考勤*****************************************/
    //查询考勤核算
    public static String GET_ATTENDANCE_RESULT_URL = "/reformer/interface/getAttendanceResult";
    //添加打卡记录
    public static String SET_ATTENDANCE_RECORD_URL = "/reformer/interface/setAttendanceRecord";
    //考勤打卡记录分页查询
    public static String GET_ATTENDANCE_RECORD_URL = "/reformer/interface/getAttendanceRecord";

    public static Map<String, String> errorCodeMap = Maps.newHashMap();

    @Autowired
    private RestTemplateClient restTemplateClient;


    public KnightResponse queryDepart(String param) {
        KnightResponse knightResponse = null;
        try {
            String data = post(QUERY_DEPART_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
            Object obj = knightResponse.getObj();
            PageInfo pageInfo = new PageInfo();
            if(obj!=null){
                pageInfo = JSON.parseObject(JSON.toJSONString(obj), PageInfo.class);
            }
            knightResponse.setObj(pageInfo);
            List list = pageInfo.getList();
            List<Depart> departs = Lists.newArrayList();
            if(!CollectionUtils.isEmpty(list)){
               departs = JSON.parseArray(JSON.toJSONString(list), Depart.class);
            }
            pageInfo.setList(departs);
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;
    }

    public KnightResponse addDepart(String param) {
        KnightResponse knightResponse = null;
        try {
            String data = post(ADD_DEPART_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
            Object obj = knightResponse.getObj();
            Depart depart = new Depart();
            if(obj!=null){
                depart = JSON.parseObject(JSON.toJSONString(obj), Depart.class);
            }
            knightResponse.setObj(depart);
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;
    }

    public KnightResponse deleteDepart(String param) {
        KnightResponse knightResponse = null;
        try {
            String data = post(DELETE_DEPART_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;
    }

    public KnightResponse getAllEmplyList(String param) {
        KnightResponse knightResponse = null;
        try {
            String data = post(GET_ALL_EMPLY_LIST_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
            List<Emply> emplyList = Lists.newArrayList();
            Object obj = knightResponse.getObj();
            if(obj!=null){
                emplyList= JSON.parseArray(JSON.toJSONString(obj), Emply.class);
            }
            knightResponse.setObj(emplyList);
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;

    }

    public KnightResponse selectEmply(String param) {
        KnightResponse knightResponse = null;
        try {
            String data = post(SELECT_EMPLY_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
            List<Emply> emplyList = Lists.newArrayList();
            Object obj = knightResponse.getObj();
            if(obj!=null){
                emplyList= JSON.parseArray(JSON.toJSONString(obj), Emply.class);
            }
            knightResponse.setObj(emplyList);
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;

    }

    public KnightResponse addEmply(String param) {

        KnightResponse knightResponse = null;
        try {
            String data = post(ADD_EMPLY_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
            Object obj = knightResponse.getObj();
            Emply emply = new Emply();
            if(obj!=null){
                emply = JSON.parseObject(JSON.toJSONString(obj), Emply.class);
            }
            knightResponse.setObj(emply);
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;
    }


    public KnightResponse deleteEmply(String param) {

        KnightResponse knightResponse = null;
        try {
            String data = post(DELETE_EMPLY_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;
    }

    public KnightResponse sendCard(String param) {

        KnightResponse knightResponse = null;
        try {
            String data = post(SEND_CARD_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;
    }


    public KnightResponse deleteCard(String param) {
        KnightResponse knightResponse = null;
        try {
            String data = post(DELETE_CARD_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;
    }


    public KnightResponse updateEmply(String param) {
        KnightResponse knightResponse = null;
        try {
            String data = post(UPDATE_EMPLY_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;

    }


    public KnightResponse registeruser(String param) {

        KnightResponse knightResponse = null;
        try {
            String data = post(REGISTER_USER_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;
    }


    public KnightResponse unregisteruser(String param) {
        KnightResponse knightResponse = null;
        try {
            String data = post(UN_REGISTER_USER_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;
    }

    public KnightResponse getMjDeviceAll(String param) {
        KnightResponse knightResponse = null;
        try {
            String data = post(GET_MJ_DEVICE_ALL_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
            List<Station> stations = Lists.newArrayList();
            Object obj = knightResponse.getObj();
            if(obj!=null){
                stations= JSON.parseArray(JSON.toJSONString(obj), Station.class);
            }
            knightResponse.setObj(stations);
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;
    }

    public KnightResponse getMjDeviceById(String param) {
        KnightResponse knightResponse = null;
        try {
            String data = post(GET_MJ_DEVICE_BY_ID_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
            Object obj = knightResponse.getObj();
            Station station = new Station();
            if(obj!=null){
                station = JSON.parseObject(JSON.toJSONString(obj), Station.class);
            }
            knightResponse.setObj(station);
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;
    }

    public KnightResponse getDoorInfoAll(String param) {
        KnightResponse knightResponse = null;
        try {
            String data = post(GET_DOOR_INFO_ALL_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
            Object obj = knightResponse.getObj();
            PageInfo pageInfo = new PageInfo();
            if(obj!=null){
                pageInfo = JSON.parseObject(JSON.toJSONString(obj), PageInfo.class);
            }
            knightResponse.setObj(pageInfo);
            List list = pageInfo.getList();
            List<Door> doors = Lists.newArrayList();
            if(!CollectionUtils.isEmpty(list)){
                doors = JSON.parseArray(JSON.toJSONString(list), Door.class);
            }
            pageInfo.setList(doors);
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;

    }


    public KnightResponse getDoorInfoById(String param) {

        KnightResponse knightResponse = null;
        try {
            String data = post(GET_DOOR_INFO_BY_ID_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
            Object obj = knightResponse.getObj();
            Door door = new Door();
            if(obj!=null){
                door = JSON.parseObject(JSON.toJSONString(obj), Door.class);
            }
            knightResponse.setObj(door);
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;
    }

    public KnightResponse mjOpenDoorRecord(String param) {
        KnightResponse knightResponse = null;
        try {
            String data = post(MJ_OPEN_DOOR_RECORD_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
            Object obj = knightResponse.getObj();
            PageInfo pageInfo = new PageInfo();
            if(obj!=null){
                pageInfo = JSON.parseObject(JSON.toJSONString(obj), PageInfo.class);
            }
            knightResponse.setObj(pageInfo);
            List list = pageInfo.getList();
            List<MjOpenDoorRecord> records = Lists.newArrayList();
            if(!CollectionUtils.isEmpty(list)){
                records = JSON.parseArray(JSON.toJSONString(list), MjOpenDoorRecord.class);
            }
            pageInfo.setList(records);
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;
    }

    public KnightResponse mJUrgentEventRecord(String param) {
        KnightResponse knightResponse = null;
        try {
            String data = post(MJ_URGENT_EVENT_RECORD_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
            Object obj = knightResponse.getObj();
            PageInfo pageInfo = new PageInfo();
            if(obj!=null){
                pageInfo = JSON.parseObject(JSON.toJSONString(obj), PageInfo.class);
            }
            knightResponse.setObj(pageInfo);
            List list = pageInfo.getList();
            List<MjUrgentEventRecord> records = Lists.newArrayList();
            if(!CollectionUtils.isEmpty(list)){
                records = JSON.parseArray(JSON.toJSONString(list), MjUrgentEventRecord.class);
            }
            pageInfo.setList(records);
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;
    }


    public KnightResponse getAttendanceResult(String param) {
        KnightResponse knightResponse = null;
        try {
            String data = post(GET_ATTENDANCE_RESULT_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
            Object obj = knightResponse.getObj();
            List<AttendanceResult> records = Lists.newArrayList();
            if(obj!=null){
                records = JSON.parseArray(JSON.toJSONString(obj), AttendanceResult.class);
            }
            knightResponse.setObj(records);
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;
    }

    public KnightResponse getAttendanceRecord(String param) {
        KnightResponse knightResponse = null;
        try {
            String data = post(GET_ATTENDANCE_RECORD_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
            Object obj = knightResponse.getObj();
            PageInfo pageInfo = new PageInfo();
            if(obj!=null){
                pageInfo = JSON.parseObject(JSON.toJSONString(obj), PageInfo.class);
            }
            knightResponse.setObj(pageInfo);
            List list = pageInfo.getList();
            List<AttendanceRecord> records = Lists.newArrayList();
            if(!CollectionUtils.isEmpty(list)){
                records = JSON.parseArray(JSON.toJSONString(list), AttendanceRecord.class);
            }
            pageInfo.setList(records);
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;
    }

    public KnightResponse setAttendanceRecord(String param) {
        KnightResponse knightResponse = null;
        try {
            String data = post(SET_ATTENDANCE_RECORD_URL, param);
            knightResponse = MessageUtil.getInstance().getGson().fromJson(data, new TypeToken<KnightResponse>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return knightResponse;

    }

    public String post(String url, String param) {
        String requestUrl = preUrl + url + "?data={1}";
        LOGGER.info("请求[url]:{},参数:{}", requestUrl, param);
        String response = restTemplateClient.postForObject(requestUrl, null, new TypeReference<String>() {
        }, param);
        LOGGER.info("返回结果[result]:{}", response);
        return response;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        errorCodeMap.put("200", "请求成功");
        errorCodeMap.put("-17", "没有session");
        errorCodeMap.put("-18", "获取结果超时");
        errorCodeMap.put("-19", "消息无效");
        errorCodeMap.put("-20", "调用参数错误");
        errorCodeMap.put("-21", "验证码错误，解密失败");
        errorCodeMap.put("100", "字符校验错");
        errorCodeMap.put("101", "返回不合理字符");
        errorCodeMap.put("102", "时间数据校验错");
        errorCodeMap.put("103", "数据超出范围");
        errorCodeMap.put("104", "时间格式校验错");
        errorCodeMap.put("105", "查询的项目为空");
        errorCodeMap.put("112", "返回数据为空");
        errorCodeMap.put("106", "时间读取出错");
        errorCodeMap.put("107", "数据格式校验错");
        errorCodeMap.put("108", "flash存储空间已满");
        errorCodeMap.put("109", "当前是紧急状态，本操作未执行");
        errorCodeMap.put("110", "flash操作失败");
        errorCodeMap.put("111", "功能不可开启");
        errorCodeMap.put("32", "记录已收到且格式正常，传回上层程序但未处理，已记入日志");
        errorCodeMap.put("5047", "余额不足");
        errorCodeMap.put("5048", "超过本餐餐次消费次数");
        errorCodeMap.put("5049", "超过今天餐次消费次数");
        errorCodeMap.put("50410", "超过本餐金额消费次数");
        errorCodeMap.put("50411", "超过今天金额消费次数");
        errorCodeMap.put("50412", "超过单次消费最大金额");
        errorCodeMap.put("50413", "退款时间超时");
        errorCodeMap.put("50414", "已经退款");
        errorCodeMap.put("5071", "最近无消费记录");
        errorCodeMap.put("5100201", "部门不存在");
        errorCodeMap.put("5100202", "卡号格式错误");
        errorCodeMap.put("5100203", "人员数量超过最大值，不能继续增加");
        errorCodeMap.put("5100204", "无效的证件号");
        errorCodeMap.put("5100205", "证件号已经存在");
        errorCodeMap.put("5100206", "无效的姓名");
        errorCodeMap.put("5100207", "姓名超过最大长度");
        errorCodeMap.put("5100208", "无效的用户类型");
        errorCodeMap.put("5100209", "无效的用户性别");
        errorCodeMap.put("5100210", "无效的手机号");
        errorCodeMap.put("5100211", "人员不存在");
        errorCodeMap.put("5100212", "有卡号不能删除");
        errorCodeMap.put("5100213", "有特殊卡不能删除");
        errorCodeMap.put("5100214", "有挂失卡不能删除");
        errorCodeMap.put("5100215", "该人员已有成员卡");
        errorCodeMap.put("5100216", "该人员已有特殊卡");
        errorCodeMap.put("5100217", "有挂失卡,不能立即发卡");
        errorCodeMap.put("5100218", "解挂请使用解挂功能");
        errorCodeMap.put("5100219", "这是用户的挂失卡");
        errorCodeMap.put("5100220", "该员工无卡");
        errorCodeMap.put("5100221", "类型强转失败，部门为空");
        errorCodeMap.put("5100222", "上级部门不能为空");
        errorCodeMap.put("5100223", "同一个上级部门下的部门名称不能相同");
        errorCodeMap.put("5100224", "总部门不能删除");
        errorCodeMap.put("5100225", "该部门存在下级部门，无法删除");
        errorCodeMap.put("5100226", "该部门下存在人员，不能删除");
        errorCodeMap.put("520000", "门禁错误码");
        errorCodeMap.put("520001", "设备编号错误");
        errorCodeMap.put("520002", "门通道号错误");
        errorCodeMap.put("520003", "设备不存在");
        errorCodeMap.put("520004", "该门不存在或未设置用户群组");
        errorCodeMap.put("520005", "无效的人员编号");
        errorCodeMap.put("520006", "无效的时间");
        errorCodeMap.put("520007", "未设置权限组");
        errorCodeMap.put("520008", "用户未发卡");
        errorCodeMap.put("520008", "该卡为门禁特殊卡");
        errorCodeMap.put("540000", "消费错误码");
        errorCodeMap.put("540000", "该卡为消费特殊卡");
    }



}
