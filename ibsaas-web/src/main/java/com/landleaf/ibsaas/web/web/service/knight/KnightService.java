package com.landleaf.ibsaas.web.web.service.knight;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.internal.LinkedTreeMap;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.knight.KnightMessage;
import com.landleaf.ibsaas.common.domain.knight.TDoor;
import com.landleaf.ibsaas.common.domain.knight.attendance.AddAttendanceRecordDTO;
import com.landleaf.ibsaas.common.domain.knight.control.*;
import com.landleaf.ibsaas.common.domain.knight.depart.AddDepartDTO;
import com.landleaf.ibsaas.common.domain.knight.depart.DeleteDepartDTO;
import com.landleaf.ibsaas.common.domain.knight.depart.Depart;
import com.landleaf.ibsaas.common.domain.knight.depart.QueryDepartDTO;
import com.landleaf.ibsaas.common.domain.knight.emply.*;
import com.landleaf.ibsaas.common.domain.knight.role.MjRole;
import com.landleaf.ibsaas.common.domain.knight.role.MjRoleDTO;
import com.landleaf.ibsaas.common.domain.knight.role.MjRoleResource;
import com.landleaf.ibsaas.common.enums.knight.KnightSubMsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.MsgTypeEnum;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.redis.RedisUtil;
import com.landleaf.ibsaas.common.utils.MessageUtil;
import com.landleaf.ibsaas.common.utils.date.DateUtils;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.web.asyn.IFutureService;
import com.landleaf.ibsaas.web.rocketmq.WebMqProducer;
import com.landleaf.ibsaas.web.web.dto.knight.attendance.WebAddAttendanceRecordDTO;
import com.landleaf.ibsaas.web.web.dto.knight.control.WebQueryRegisterUserByDbDTO;
import com.landleaf.ibsaas.web.web.dto.knight.control.WebRegisterUserDTO;
import com.landleaf.ibsaas.web.web.dto.knight.control.WebUnRegisterUserDTO;
import com.landleaf.ibsaas.web.web.dto.knight.depart.WebAddDepartDTO;
import com.landleaf.ibsaas.web.web.dto.knight.depart.WebDeleteDepartDTO;
import com.landleaf.ibsaas.web.web.dto.knight.emply.*;
import com.landleaf.ibsaas.web.web.dto.knight.userrole.WebMjUserRoleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class KnightService implements IKnightServeice {
    private static String CLIENT_TOPIC = "ibsaas_knight_lifang_lgc_1921681010";
    private static Long TIME_OUT = 20 * 1000L;
    ;

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightService.class);


    @Autowired
    private WebMqProducer webMqProducer;
    @Autowired
    private IFutureService futureService;
    @Autowired
    private IMjRoleResourceService mjRoleResourceService;
    @Autowired
    private IMjRoleService mjRoleService;
    @Autowired
    private IMjUserRoleService mjUserRoleService;
    @Autowired
    private IDoorService doorService;

    @Autowired
    private Executor mjRegisterUserTaskExecutor;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public Response getDoorInfoAllByCondition(String doorName, int page, int limit) {
        //组织参数
        QueryMjDoorDTO sendRequest = new QueryMjDoorDTO();
        sendRequest.setDoorName(doorName);
        sendRequest.setPageSize(limit);
        sendRequest.setCurPage(page);

        //获取基础数据
        Response response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.GET_DOOR_INFO_ALL.getName());

        if (response != null && response.getResult() != null) {
            String jsonString = JSON.toJSONString(response.getResult());
            PageInfo<Door> doorPageInfo = JSON.parseObject(jsonString, new TypeReference<PageInfo<Door>>() {
            });
            response.setResult(doorPageInfo);
            List<Door> doorList = doorPageInfo.getList();
            if (!CollectionUtils.isEmpty(doorList)) {
                List<Integer> devicesysIds = doorList.stream().map(i -> i.getDevicesysid()).collect(Collectors.toList());
                List<Long> doorIds = doorList.stream().map(i -> {
                    return Long.valueOf(i.getDoorId());
                }).collect(Collectors.toList());
                Map<Integer, List<Station>> stationGroup = Maps.newHashMap();
                Map<Integer, List<TDoor>> doorControllGroup = Maps.newHashMap();
                try {
                    //设备名称
                    Response<List<Station>> mjDeviceResponse = getMjDeviceByIds(devicesysIds);
                    List<Station> stationList = mjDeviceResponse.getResult();
                    if (!CollectionUtils.isEmpty(stationList)) {
                        stationGroup = stationList.stream().collect(Collectors.groupingBy(Station::getDeviceSysId));
                    }
                    //位置名称
                    List<TDoor> doorControlls = doorService.getDoorInfoByControlIds(doorIds);
                    if (!CollectionUtils.isEmpty(doorControlls)) {
                        doorControllGroup = doorControlls.stream().collect(Collectors.groupingBy(TDoor::getControlId));
                    }
                    for (Door door : doorList) {
                        List<Station> findStations = stationGroup.get(door.getDevicesysid());
                        if (!CollectionUtils.isEmpty(findStations)) {
                            door.setStationName(findStations.get(0).getStationName());
                        }
                        try {
                            List<TDoor> doors = doorControllGroup.get(door.getDoorId());
                            if (!CollectionUtils.isEmpty(doors)) {
                                door.setLocationId(String.valueOf(doors.get(0).getId()));
                                door.setLocationName(doors.get(0).getName());
                            }
                        } catch (Exception e) {
                            LOGGER.error("获取门禁位置信息异常{}", e.getMessage(), e);
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("获取设备信息异常{}", e.getMessage(), e);
                }
            }
        }
        return response;
    }

    /**
     * 根据设备ID获取设备信息
     *
     * @param ids
     * @return
     */
    @Override
    public Response<List<Station>> getMjDeviceByIds(List<Integer> ids) {
        QueryMjDeviceDTO sendRequest = new QueryMjDeviceDTO();
        sendRequest.setDeviceSysIds(ids);
        Response response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.GET_MJ_DEVICE_BY_IDS_DB.getName());
        if (response != null && response.getResult() != null) {
            String jsonString = JSON.toJSONString(response.getResult());

            List<Station> stations = JSON.parseArray(jsonString, Station.class);
            response.setResult(stations);
        }
        return response;
    }

    /**
     * 门禁报警记录分页查询
     *
     * @param doorName 门名称
     * @param start    开始时间
     * @param end      截止时间
     * @param page     当前页
     * @param limit    最大记录数
     * @return
     */
    @Override
    public Response mJUrgentEventRecord(String doorName, String stationName, String start, String end, int page, int limit) {
        Response response = new Response();
        QueryMjUrgentEventRecordDTO sendRequest = new QueryMjUrgentEventRecordDTO();
        sendRequest.setDoorName(doorName);
        sendRequest.setStationName(stationName);
        sendRequest.setStart(start);
        sendRequest.setEnd(end);
        sendRequest.setCurPage(page);
        sendRequest.setPageSize(limit);
        response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.MJ_URGENT_EVENT_RECORD.getName());
        if (response != null && response.getResult() != null) {
            String jsonString = JSON.toJSONString(response.getResult());
            PageInfo<MjUrgentEventRecord> mjUrgentEventRecordPageInfo = JSON.parseObject(jsonString, new TypeReference<PageInfo<MjUrgentEventRecord>>() {
            });
            response.setResult(mjUrgentEventRecordPageInfo);
            List<MjUrgentEventRecord> mjUrgentEventRecords = mjUrgentEventRecordPageInfo.getList();
        }

        return response;
    }

    /**
     * 进出记录分页查询
     *
     * @param start 开始时间
     * @param end   截止时间
     * @param page  当前页
     * @param limit 最大记录数
     * @return
     */
    @Override
    public Response mjOpenDoorRecord(String start, String end, int page, int limit) {
        Response response = new Response();
        QueryMjDoorOpenRecordDTO sendRequest = new QueryMjDoorOpenRecordDTO();
        sendRequest.setStart(start);
        sendRequest.setEnd(end);
        sendRequest.setCurPage(page);
        sendRequest.setPageSize(limit);
        response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.MJ_OPEN_DOOR_RECORD_DB.getName());
        if (response != null && response.getResult() != null) {
            String jsonString = JSON.toJSONString(response.getResult());
            PageInfo<KQCardRecord> mjOpenDoorRecordPageInfo = JSON.parseObject(jsonString, new TypeReference<PageInfo<KQCardRecord>>() {
            });
            response.setResult(mjOpenDoorRecordPageInfo);
            List<KQCardRecord> mjOpenDoorRecords = mjOpenDoorRecordPageInfo.getList();
        }
        return response;
    }

    /**
     * 角色分页查询
     *
     * @param name     角色名称
     * @param departId 部门
     * @param page     当前页
     * @param limit    最大记录数
     * @return
     */
    @Override
    public Response mjRoles(String name, Integer departId, int page, int limit) {
        Response response = new Response();
        List<MjRoleDTO> dtoResult = Lists.newArrayList();
        PageInfo<MjRole> pageInfo = mjRoleService.getPageInfo(name, departId, page, limit);
        List<MjRole> list = pageInfo.getList();
        if (!CollectionUtils.isEmpty(list)) {
            Map<Integer, List<Depart>> departGroup = Maps.newHashMap();
            try {
                Response<List<Depart>> departResponse = queryAllDepart();
                List<Depart> departList = departResponse.getResult();
                departGroup = departList.stream().collect(Collectors.groupingBy(Depart::getDepartId));
            } catch (Exception e) {
                LOGGER.error("获取部门数据异常{}", e.getMessage(), e);
            }
            Map<Integer, List<Depart>> finalDepartGroup = departGroup;
            dtoResult = list.stream().map(i -> {
                MjRoleDTO dto = new MjRoleDTO();
                BeanUtils.copyProperties(i, dto);
                List<Depart> departList = finalDepartGroup.get(i.getDepartId());
                if (!CollectionUtils.isEmpty(departList)) {
                    dto.setDepartName(departList.get(0).getName());
                }
                return dto;
            }).collect(Collectors.toList());
        }
        PageInfo newPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, newPageInfo);
        newPageInfo.setList(dtoResult);
        response.setResult(newPageInfo);
        response.setSuccess(true);
        return response;
    }

    /**
     * 部门分页查询
     *
     * @param page  当前页
     * @param limit 每页最大数
     * @return
     */
    @Override
    public Response<PageInfo> queryDepart(int page, int limit) {
        Response response = new Response();
        QueryDepartDTO sendRequest = new QueryDepartDTO();
        sendRequest.setCurPage(page);
        sendRequest.setPageSize(limit);
        response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.QUERY_DEPART.getName());
        if (response != null && response.getResult() != null) {
            String jsonString = JSON.toJSONString(response.getResult());
            PageInfo<Depart> departPageInfo = JSON.parseObject(jsonString, new TypeReference<PageInfo<Depart>>() {
            });
            response.setResult(departPageInfo);
            List<Depart> departList = departPageInfo.getList();
        }
        return response;
    }

    @Override
    public Response queryAllDepart() {
        String key = RedisUtil.STRING_KEY_QUERY_ALL_DEPART;
        Object redisData=redisUtil.get(key);
        List<Depart> departList = Lists.newArrayList();
        if(redisData==null){
            Response response = queryDepart(1, 10000);
            if (response != null && response.getResult() != null) {
                String jsonString = JSON.toJSONString(response.getResult());
                PageInfo<Depart> departPageInfo = JSON.parseObject(jsonString, new TypeReference<PageInfo<Depart>>() {
                });
                departList = departPageInfo.getList();
                response.setResult(departList);
                if(!CollectionUtils.isEmpty(departList)){
                    redisUtil.set(key,JSON.toJSONString(departList),3600);
                }
            }
            return response;
        }else {
            departList= JSON.parseArray((String) redisData,Depart.class);
        }
        Response response = new Response();
        response.setSuccess(true);
        response.setResult(departList);
        return response;
    }

    @Override
    public Response queryAllEmplyType() {
        Response response = new Response();
        EmplyTypeDTO sendRequest = new EmplyTypeDTO();
        response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.QUERY_ALL_EMPLYTYPE.getName());
        if (response != null && response.getResult() != null) {
            String jsonString = JSON.toJSONString(response.getResult());
            List<EmplyTypeDTO> emplyTypeDTOS = JSON.parseArray(jsonString, EmplyTypeDTO.class);
            response.setResult(emplyTypeDTOS);
        }
        return response;
    }

    @Override
    public Response addDepart(WebAddDepartDTO requestBody) {
        Response response = new Response();
        AddDepartDTO sendRequest = new AddDepartDTO();
        BeanUtils.copyProperties(requestBody, sendRequest);
        response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.ADD_DEPART.getName());
        return response;
    }

    @Override
    public Response deleteDepart(WebDeleteDepartDTO requestBody) {
        Response response = new Response();
        DeleteDepartDTO sendRequest = new DeleteDepartDTO();
        BeanUtils.copyProperties(requestBody, sendRequest);
        response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.DELETE_DEPART.getName());
        return response;
    }

    @Override
    public Response selectEmply(WebQueryEmplyDTO requestBody) {
        Response response = new Response();
        QueryEmplyDTO sendRequest = new QueryEmplyDTO();
        BeanUtils.copyProperties(requestBody, sendRequest);
        sendRequest.setCurPage(requestBody.getPage());
        sendRequest.setPageSize(requestBody.getLimit());
        response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.SELECT_EMPLY.getName());
        if (response != null && response.getResult() != null) {
            String jsonString = JSON.toJSONString(response.getResult());
            PageInfo<Emply> emplyPageInfo = JSON.parseObject(jsonString, new TypeReference<PageInfo<Emply>>() {
            });
            response.setResult(emplyPageInfo);
            List<Emply> list = emplyPageInfo.getList();
            Map<Integer, List<Depart>> departGroup = Maps.newHashMap();
            try {
                Response<PageInfo> departResponse = queryDepart(1, 10000);
                List<Depart> departList = departResponse.getResult().getList();
                departGroup = departList.stream().collect(Collectors.groupingBy(Depart::getDepartId));
            } catch (Exception e) {
                LOGGER.error("获取部门数据异常{}", e.getMessage(), e);
            }

            Map<Integer, List<Depart>> finalDepartGroup = departGroup;
            for (Emply emply : list) {
                List<Depart> departList = finalDepartGroup.get(emply.getDepartId());
                if (!CollectionUtils.isEmpty(departList)) {
                    emply.setDepartName(departList.get(0).getName());
                }
                try {
                    List<MjRole> userRoles = mjUserRoleService.getUserRoleBySysNo(emply.getSysNo());
                    if (!CollectionUtils.isEmpty(userRoles)) {
                        emply.setRoleIds(userRoles.stream().map(i -> {
                            return i.getId();
                        }).collect(Collectors.toList()));
                        emply.setRoleNames(String.join(",", userRoles.stream().map(i -> {
                            return i.getName();
                        }).collect(Collectors.toList())));
                    }
                } catch (Exception e) {
                    LOGGER.error("获取用户角色异常{}", e.getMessage(), e);
                }

                try {
                    if (emply.getInvalidate() != null) {
                        emply.setInvalidateStr(DateUtils.convert(emply.getInvalidate()));
                    }
                    if(emply.getDeleted()!=null){
                        switch (emply.getDeleted()){
                            case 0:
                                emply.setDeletedStr("否");
                                break;
                            case 1:
                                emply.setDeletedStr("是");
                                break;
                            default:
                                emply.setDeletedStr("否");
                                break;
                        }
                    }
                    if (emply.getEmployeeSex() != null) {
                        switch (emply.getEmployeeSex()) {
                            case 1:
                                emply.setEmployeeSexStr("男");
                                break;
                            case 2:
                                emply.setEmployeeSexStr("女");
                                break;
                            default:
                                emply.setEmployeeSexStr("未知");
                                break;
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("失效时间转换异常{}", e.getMessage(), e);
                }
            }
        }


        return response;
    }

    @Override
    public Response addEmply(WebAddEmplyDTO requestBody) {
        Response response = new Response();
        AddEmplyDTO sendRequest = new AddEmplyDTO();
        BeanUtils.copyProperties(requestBody, sendRequest);
        //新增用户
        response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.ADD_EMPLY.getName());
        return response;
    }

    @Override
    public Response deleteEmply(WebDeleteEmplyDTO requestBody) {
        Response response = new Response();
        DeleteEmplyDTO sendRequest = new DeleteEmplyDTO();
        BeanUtils.copyProperties(requestBody, sendRequest);
        response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.DELETE_EMPLY.getName());
        return response;
    }

    @Override
    public Response sendCard(WebSendCardDTO requestBody) {
        Response response = new Response();
        SendCardDTO sendRequest = new SendCardDTO();
        BeanUtils.copyProperties(requestBody, sendRequest);
        response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.SEND_CARD.getName());
        return response;
    }

    @Override
    public Response deleteCard(WebDeleteCardDTO requestBody) {
        Response response = new Response();
        DeleteCardDTO sendRequest = new DeleteCardDTO();
        BeanUtils.copyProperties(requestBody, sendRequest);
        response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.DELETE_CARD.getName());
        return response;
    }

    @Override
    public Response updateEmply(WebUpdateEmplyDTO requestBody) {
        Response response = new Response();
        UpdateEmplyDTO sendRequest = new UpdateEmplyDTO();
        BeanUtils.copyProperties(requestBody, sendRequest);
        response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.UPDATE_EMPLY.getName());
        return response;
    }

    @Override
    public Response registeruser(WebRegisterUserDTO requestBody) {
        Response response = new Response();
        RegisterUserDTO sendRequest = new RegisterUserDTO();
        BeanUtils.copyProperties(requestBody, sendRequest);
        response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.REGISTER_USER.getName());
        return response;
    }

    @Override
    public Response unregisteruser(WebUnRegisterUserDTO requestBody) {
        Response response = new Response();
        RegisterUserDTO sendRequest = new RegisterUserDTO();
        BeanUtils.copyProperties(requestBody, sendRequest);
        response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.UN_REGISTER_USER.getName());
        return response;
    }

    @Override
    public Response bindRole(Integer sysNo, List<String> roleIds) {
        Response response = new Response();
        response.setSuccess(true);

        //解除用户所有权限
        unRegisterUserAllPermission(sysNo);
        //通过角色生成权限
        if(!CollectionUtils.isEmpty(roleIds)){
            registeruserByMjRole(sysNo,roleIds);
        }
        //插入用户角色中间表
        int count = mjUserRoleService.userBindRole(sysNo,roleIds);



        //通过角色生成权限
//        mjRegisterUserTaskExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                registeruserByMjRole(sysNo,roleIds);
//            }
//        });
        return response;
    }

    /**
     * 更新角色下面所有用户的权限
     * @param roleId
     * @return
     */
    @Override
    public Response updatePermissionByRole(String roleId) {
        Response response = new Response();
        response.setSuccess(true);
        //查询角色下面所有用户
        List<MjUserRole> userRoles = mjUserRoleService.getUserRoleByRoleId(roleId);
        if(CollectionUtils.isEmpty(userRoles)){
            return response;
        }
        List<Integer> sysNoList = userRoles.stream().map(i -> {
            return i.getMjUserId();
        }).collect(Collectors.toList());
        //通过角色生成权限
        mjRegisterUserTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                for (Integer sysNo : sysNoList) {
                    List roleIds = mjUserRoleService.getUserRoleBySysNo(sysNo);
                    bindRole(sysNo,roleIds);
                }
            }
        });
        return response;
    }

    //解除用户所有权限
    private void unRegisterUserAllPermission(Integer sysNo) {
        //解除原有权限
        WebQueryRegisterUserByDbDTO queryRegisterUserByDbDTO = new WebQueryRegisterUserByDbDTO();
        queryRegisterUserByDbDTO.setPage(1);
        queryRegisterUserByDbDTO.setLimit(10000);
        queryRegisterUserByDbDTO.setSysNo(sysNo);
        Response<PageInfo<MjReguser>> registerUserResponse = queryRegisteruserByDb(queryRegisterUserByDbDTO);
        if (registerUserResponse != null && registerUserResponse.getResult() != null) {
            PageInfo<MjReguser> pageInfo = registerUserResponse.getResult();
            List<MjReguser> list = pageInfo.getList();
            if (!CollectionUtils.isEmpty(list)) {
                //解除原来权限
                for (MjReguser mjReguser : list) {
                    WebUnRegisterUserDTO param = new WebUnRegisterUserDTO();
                    param.setDoorId(String.valueOf(mjReguser.getDoorId()));
                    param.setEmployeeId(String.valueOf(mjReguser.getSysNo()));
                    Response unregisteruser = unregisteruser(param);
                }
            }
        }
    }

    //通过角色生成权限
    private void registeruserByMjRole(Integer sysNo, List<String> roleIds) {
        List<MjRoleResource> mjRoleResourceList = mjRoleResourceService.findRoleResourceByRoleIds(roleIds);

        Set<Integer> doorIds = Sets.newHashSet();
        if (!CollectionUtils.isEmpty(mjRoleResourceList)) {
            for (MjRoleResource mjRoleResource : mjRoleResourceList) {
                doorIds.add(mjRoleResource.getMjDoorId());
            }
            for (Integer doorId : doorIds) {
                    WebRegisterUserDTO param = new WebRegisterUserDTO();
                    param.setDoorId(String.valueOf(doorId));
                    param.setEmployeeId(String.valueOf(sysNo));
                    Date date = new Date();
                    param.setStartTime(DateUtils.convert(new Date(), "yyyyMMddHHmmss"));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.YEAR, 50);
                    param.setEndTime(DateUtils.convert(calendar.getTime(), "yyyyMMddHHmmss"));
                    Response response = registeruser(param);
            }
        }
    }

    @Override
    public Response<PageInfo<MjReguser>> queryRegisteruserByDb(WebQueryRegisterUserByDbDTO requestBody) {
        Response response = new Response();
        QueryRegisterUserByDbDTO sendRequest = new QueryRegisterUserByDbDTO();
        BeanUtils.copyProperties(requestBody, sendRequest);
        sendRequest.setCurPage(requestBody.getPage());
        sendRequest.setPageSize(requestBody.getLimit());
        response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.QUERY_REGISTER_USER_BY_DB.getName());
        if (response != null && response.getResult() != null) {
            String jsonString = JSON.toJSONString(response.getResult());
            PageInfo<MjReguser> mjReguserPageInfo = JSON.parseObject(jsonString, new TypeReference<PageInfo<MjReguser>>() {
            });
            response.setResult(mjReguserPageInfo);
            List<MjReguser> departList = mjReguserPageInfo.getList();
        }
        return response;
    }

    @Override
    public Response getMjDeviceByPageDb(int page, int limit) {
        Response response = new Response();
        QueryRegisterUserByDbDTO sendRequest = new QueryRegisterUserByDbDTO();
        sendRequest.setCurPage(page);
        sendRequest.setPageSize(limit);
        response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.GET_MJ_DEVICE_BY_PAGE_DB.getName());
        if (response != null && response.getResult() != null) {
            String jsonString = JSON.toJSONString(response.getResult());
            PageInfo<Station> stationPageInfo = JSON.parseObject(jsonString, new TypeReference<PageInfo<Station>>() {
            });
            response.setResult(stationPageInfo);
            List<Station> stationList = stationPageInfo.getList();
        }
        return response;
    }

    @Override
    public Response getMjDeviceById(Integer deviceSysId) {
        Response response = new Response();
        QueryMjDeviceDTO sendRequest = new QueryMjDeviceDTO();
        sendRequest.setDeviceSysId(deviceSysId);
        response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.GET_MJ_DEVICE_BY_ID.getName());
        if (response != null && response.getResult() != null) {
            String jsonString = JSON.toJSONString(response.getResult());
            Station station = JSON.parseObject(jsonString, Station.class);
            response.setResult(station);
        }
        return response;
    }

    @Override
    public Response getDoorInfoById(Integer doorId) {
        Response response = new Response();
        QueryMjDoorByIdDTO sendRequest = new QueryMjDoorByIdDTO();
        sendRequest.setDoorId(doorId);
        response = sendMsgWaitForResult(sendRequest,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.GET_DOOR_INFO_BY_ID.getName());
        if (response != null && response.getResult() != null) {
            String jsonString = JSON.toJSONString(response.getResult());
            Station station = JSON.parseObject(jsonString, Station.class);
            response.setResult(station);
        }
        return response;
    }

    @Override
    public Response setAttendanceRecord(WebAddAttendanceRecordDTO requestBody) {
        Response response = new Response();
        AddAttendanceRecordDTO sendRequest = new AddAttendanceRecordDTO();
        BeanUtils.copyProperties(requestBody, sendRequest);
        response = sendMsgWaitForResult(requestBody,
                MsgTypeEnum.KNIGHT.getName(),
                KnightSubMsgTypeEnum.SET_ATTENDANCE_RECORD.getName());
        return response;
    }


    /**
     * 获取门禁业务响应数据
     *
     * @param requestBody 请求参数
     * @param msgName     请求消息类型
     * @param subMsgName  请求消息子类型
     * @return
     */
    private Response sendMsgWaitForResult(Object requestBody, String msgName, String subMsgName) {


        Response response = new Response();
        KnightMessage request = new KnightMessage();
        String msgId = MessageUtil.generateId(20);
        request.setMsgId(msgId);
        request.setMsgName(msgName);
        request.setSubMsgName(subMsgName);
        request.setRequestBody(requestBody);
        webMqProducer.sendMessage(JSON.toJSONString(request), CLIENT_TOPIC, TagConstants.TAGS_DEFAULT);
        Future<KnightMessage> cacheFuture = futureService.getKnightCacheFuture(msgId, TIME_OUT);
        KnightMessage result = new KnightMessage();
        try {
            result = cacheFuture.get(TIME_OUT, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        if (result != null && result.getResponse() != null) {
                Object tmpResult = result.getResponse().getResult();
                if (tmpResult != null) {

                    LinkedTreeMap knightResponse = (LinkedTreeMap) result.getResponse().getResult();
                    response.setMessage((String) knightResponse.get("resultInfo"));
                    response.setResult(knightResponse.get("obj"));
                    String resultCode = (String) knightResponse.get("result");
                    if (StringUtil.isEquals("200", resultCode)) {
                        response.setSuccess(true);
                    } else {
                        throw new BusinessException((String) knightResponse.get("resultInfo"));
                    }
                }
            return response;
        }
        throw new BusinessException("未获取到数据,稍后再试");
    }
}
