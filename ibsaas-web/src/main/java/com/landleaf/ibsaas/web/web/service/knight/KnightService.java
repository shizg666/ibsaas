package com.landleaf.ibsaas.web.web.service.knight;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.google.gson.internal.LinkedTreeMap;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.knight.KnightMessage;
import com.landleaf.ibsaas.common.domain.knight.control.*;
import com.landleaf.ibsaas.common.enums.knight.KnightSubMsgTypeEnum;
import com.landleaf.ibsaas.common.enums.parking.MsgTypeEnum;
import com.landleaf.ibsaas.common.utils.MessageUtil;
import com.landleaf.ibsaas.rocketmq.TagConstants;
import com.landleaf.ibsaas.web.asyn.IFutureService;
import com.landleaf.ibsaas.web.rocketmq.WebMqProducer;
import com.landleaf.ibsaas.web.web.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class KnightService implements IKnightServeice {
    private static String CLIENT_TOPIC = "ibsaas_knight_lifang_lgc_1921681010";
    private static Long TIME_OUT = 30 * 1000L;
    ;

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightService.class);


    @Autowired
    private WebMqProducer webMqProducer;
    @Autowired
    private IFutureService futureService;


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
                try {
                    Response<List<Station>> mjDeviceResponse = getMjDeviceByIds(devicesysIds);
                    List<Station> stationList = mjDeviceResponse.getResult();
                    Map<Integer, List<Station>> stationGroup = stationList.stream().collect(Collectors.groupingBy(Station::getDeviceSysId));
                    for (Door door : doorList) {
                        List<Station> findStations = stationGroup.get(door.getDevicesysid());
                        if(!CollectionUtils.isEmpty(findStations)){
                            door.setStationName(findStations.get(0).getStationName());
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("获取设备信息异常{}",e.getMessage(),e);
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
     * @param doorName     门名称
     * @param start        开始时间
     * @param end          截止时间
     * @param page         当前页
     * @param limit        最大记录数
     * @return
     */
    @Override
    public Response mJUrgentEventRecord(String doorName, String start, String end, int page, int limit) {
        Response response = new Response();
        QueryMjUrgentEventRecordDTO sendRequest = new QueryMjUrgentEventRecordDTO();
        sendRequest.setDoorName(doorName);
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
     * @param start        开始时间
     * @param end          截止时间
     * @param page         当前页
     * @param limit        最大记录数
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
                KnightSubMsgTypeEnum.MJ_OPEN_DOOR_RECORD.getName());
        if (response != null && response.getResult() != null) {
            String jsonString = JSON.toJSONString(response.getResult());
            PageInfo<MjOpenDoorRecord> mjOpenDoorRecordPageInfo = JSON.parseObject(jsonString, new TypeReference<PageInfo<MjOpenDoorRecord>>() {
            });
            response.setResult(mjOpenDoorRecordPageInfo);
            List<MjOpenDoorRecord> mjOpenDoorRecords = mjOpenDoorRecordPageInfo.getList();
        }
        return response;
    }
    /**
     * 角色分页查询
     * @param name      角色名称
     * @param departId  部门
     * @param page       当前页
     * @param limit      最大记录数
     * @return
     */
    @Override
    public Response mjRoles(String name, Integer departId, int page, int limit) {




        return null;
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
            try {
                Object tmpResult = result.getResponse().getResult();
                if (tmpResult != null) {
                    LinkedTreeMap knightResponse = (LinkedTreeMap) result.getResponse().getResult();
                    response.setMessage((String) knightResponse.get("resultInfo"));
                    response.setResult(knightResponse.get("obj"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
        throw new BusinessException("未获取到数据,稍后再试");
    }
}
