package com.landleaf.ibsaas.web.web.controller.parking;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.parking.ParkingRealCountInit;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.domain.parking.request.*;
import com.landleaf.ibsaas.common.domain.parking.response.ChannelResponseDTO;
import com.landleaf.ibsaas.common.domain.parking.response.ChargeruleResponseDTO;
import com.landleaf.ibsaas.common.enums.parking.*;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.utils.MessageUtil;
import com.landleaf.ibsaas.common.utils.date.DateUtil;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.web.tcp.cache.ConcurrentHashMapCacheUtils;
import com.landleaf.ibsaas.web.tcp.init.IbsaasWebTCPClientHandler;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.exception.ParkingException;
import com.landleaf.ibsaas.web.web.service.parking.IParkingRealCountInitService;
import io.netty.channel.ChannelHandlerContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 停车业务相关操作控制器
 */
@RestController
@RequestMapping("/parking")
@Api(value = "/parking", description = "停车业务相关操作")
public class ParkingController extends BasicController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingController.class);

    @Autowired
    private IbsaasWebTCPClientHandler ibsaasWebTCPClientHandler;

    @Autowired
    private IParkingRealCountInitService parkingRealCountInitService;


    /**
     * 获取通道类型
     */
    @GetMapping("/channels")
    @ApiOperation(value = "获取通道列表", notes = "获取通道列表")
    public Response requestInnerClient() {

        //查找长连接会话
        ChannelHandlerContext ctx = getChannelHandlerContext(TCPMessageSourceEnum.SERVER.clientId);

        //请求参数
        TCPMessage data = new TCPMessage();
        String msgId = MessageUtil.generateId(20);
        ChannelListQueryDTO queryDTO = new ChannelListQueryDTO();
        data.build(DateUtil.format(new Date()),
                TCPMessageSourceEnum.CLIENT_OUTER_CLOUD_SYSTEM.clientId,
                TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId,
                msgId,//返回时消息唯一标记
                MsgTypeEnum.PARKING.name,//主消息名称
                SubMsgTypeEnum.CHANNEL_LIST.name,//子消息名称
                queryDTO,
                null);
        //发送消息
        writeAndFlush(ctx, data);
        TCPMessage tcpMessage = (TCPMessage) ConcurrentHashMapCacheUtils.getCache(msgId, 60 * 1000L);
        if (tcpMessage == null) {
            LOGGER.error("与获取数据超时");
            throw new BusinessException(ParkingException.GET_DATA_TIMEOUT_FROM_SERVER);
        }
        List<Map<String, String>> results = Lists.newArrayList();
        try {
            List<ChannelResponseDTO> channelResponseDTOList = JSON.parseArray(JSON.toJSONString(tcpMessage.getResponse().getResult()), ChannelResponseDTO.class);
            if (!CollectionUtils.isEmpty(channelResponseDTOList)) {
                channelResponseDTOList.forEach(item -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("value", item.getChannelCode());
                    map.put("displayName", item.getChannelName());
                    results.add(map);
                });
            }
        } catch (Exception e) {
            LOGGER.error("数据类型格式错误");
            throw new BusinessException("接收到数据类型格式错误");
        }
        return returnSuccess(results);
    }

    /**
     * 通道类型（枚举：进/出）
     */
    @GetMapping("/channels-type")
    @ApiOperation(value = "获取通道类型枚举", notes = "获取通道类型枚举")
    public Response channelsType() {
        List<Map<String, String>> results = Lists.newArrayList();
        for (ChannelTypeEnum channelTypeEnum : ChannelTypeEnum.values()) {
            Map<String, String> map = new HashMap<>();
            map.put("value", String.valueOf(channelTypeEnum.name));
            map.put("displayName", channelTypeEnum.name);
            results.add(map);
        }
        return returnSuccess(results);
    }

    /**
     * 获取收费类型
     */
    @GetMapping("/chargerules")
    @ApiOperation(value = "获取收费类型列表", notes = "获取收费类型列表")
    public Response queryAllChargerule() {
        //查找中转服务端长连接会话
        ChannelHandlerContext ctx = getChannelHandlerContext(TCPMessageSourceEnum.SERVER.clientId);

        //请求参数
        TCPMessage data = new TCPMessage();
        String msgId = MessageUtil.generateId(20);
        data.build(DateUtil.format(new Date()),
                TCPMessageSourceEnum.CLIENT_OUTER_CLOUD_SYSTEM.clientId,
                TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId,
                msgId,
                MsgTypeEnum.PARKING.name,
                SubMsgTypeEnum.CHARGERULE_LIST.name,
                null,
                null);
        writeAndFlush(ctx, data);

        TCPMessage tcpMessage = (TCPMessage) ConcurrentHashMapCacheUtils.getCache(msgId, 60 * 1000L);
        if (tcpMessage == null) {
            LOGGER.error("与获取数据超时");
            throw new BusinessException(ParkingException.GET_DATA_TIMEOUT_FROM_SERVER);
        }

        List<Map<String, String>> results = Lists.newArrayList();
        try {
            List<ChargeruleResponseDTO> chargeruleResponseDTOS = JSON.parseArray(
                    JSON.toJSONString(tcpMessage.getResponse().getResult()),
                    ChargeruleResponseDTO.class);
            if (!CollectionUtils.isEmpty(chargeruleResponseDTOS)) {
                chargeruleResponseDTOS.forEach(item -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("value", item.getChargeTypeCode());
                    map.put("displayName", item.getChargeTypeName());
                    results.add(map);
                });
            }
        } catch (Exception e) {
            LOGGER.error("数据类型格式错误");
            throw new BusinessException("接收到数据类型格式错误");
        }
        return returnSuccess(results);
    }

    /**
     * 车位实时监控
     *
     * @param queryDTO
     */
    @GetMapping("/usercrdtm/real-count")
    @ApiOperation(value = "车位实时监控", notes = "车位实时监控")
    public Response realCount(@ApiParam UsercrdtmRealCountQueryDTO queryDTO) {
        ChannelHandlerContext ctx = getChannelHandlerContext(TCPMessageSourceEnum.SERVER.clientId);
        //请求参数
        TCPMessage data = new TCPMessage();

        //查找初始配置
        if (StringUtil.isEmpty(queryDTO.getClientId())) {
            //后期由前端传入，暂写枚举
            queryDTO.setClientId(TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId);
        }
        UsercrdtmRealCountQueryDTO initParams = parkingRealCountInitService.queryRealCountInitParams(queryDTO.getClientId());
        if (initParams == null) {
            throw new BusinessException("车位初始化配置不存在,请联系管理员配置");
        }
        String msgId = MessageUtil.generateId(20);
        BeanUtils.copyProperties(initParams, queryDTO);
        data.build(DateUtil.format(new Date()),
                TCPMessageSourceEnum.CLIENT_OUTER_CLOUD_SYSTEM.clientId,
                TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId,
                msgId, MsgTypeEnum.PARKING.name,
                SubMsgTypeEnum.PARKING_REAL_COUNT.name,
                queryDTO,
                null);
        writeAndFlush(ctx, data);
        TCPMessage tcpMessage = (TCPMessage) ConcurrentHashMapCacheUtils.getCache(msgId, 60 * 1000L);
        if (tcpMessage == null) {
            LOGGER.error("与获取数据超时");
            throw new BusinessException(ParkingException.GET_DATA_TIMEOUT_FROM_SERVER);
        }
        return returnSuccess(tcpMessage.getResponse().getResult());
    }

    /**
     * 车位分时段监控
     *
     * @param queryDTO
     */
    @GetMapping("/usercrdtm/real-count/hour")
    @ApiOperation(value = "车位分时段监控", notes = "车位分时段监控")
    public Response realCountFHour(UsercrdtmRealCountQueryByHourDTO queryDTO) {
        ChannelHandlerContext ctx = getChannelHandlerContext(TCPMessageSourceEnum.SERVER.clientId);
        //请求参数
        TCPMessage data = new TCPMessage();
        String msgId = MessageUtil.generateId(20);
        //查找初始配置
        if (StringUtil.isEmpty(queryDTO.getClientId())) {
            //后期由前端传入，暂写枚举
            queryDTO.setClientId(TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId);
        }
        UsercrdtmRealCountQueryDTO initParams = parkingRealCountInitService.queryRealCountInitParams(queryDTO.getClientId());
        if (initParams == null) {
            throw new BusinessException("车位初始化配置不存在,请联系管理员配置");
        }
        BeanUtils.copyProperties(initParams,queryDTO);
        data.build(DateUtil.format(new Date()),
                TCPMessageSourceEnum.CLIENT_OUTER_CLOUD_SYSTEM.clientId,
                TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId,
                msgId,
                MsgTypeEnum.PARKING.name,
                SubMsgTypeEnum.PARKING_REAL_COUNT_HOUR.name,
                queryDTO,
                null);
        writeAndFlush(ctx, data);
        TCPMessage tcpMessage = (TCPMessage) ConcurrentHashMapCacheUtils.getCache(msgId, 60 * 1000L);
        if (tcpMessage == null) {
            LOGGER.error("与获取数据超时");
            throw new BusinessException(ParkingException.GET_DATA_TIMEOUT_FROM_SERVER);
        }
        Map<String, Object> map = null;
        try {
            List<UsercrdtmRealCountQueryByHourDTO> chargeruleResponseDTOS = JSON.parseArray(JSON.toJSONString(tcpMessage.getResponse().getResult()), UsercrdtmRealCountQueryByHourDTO.class);


            List<String> xList = chargeruleResponseDTOS.stream().map(i -> i.getCurrentHour()).collect(Collectors.toList());
            List<Integer> yList = chargeruleResponseDTOS.stream().map(i -> i.getOccupyCount()).collect(Collectors.toList());
            map = Maps.newHashMap();
            map.put("x", xList);
            map.put("y", yList);
        } catch (Exception e) {
            LOGGER.error("数据类型格式错误");
            throw new BusinessException("接收到数据类型格式错误");
        }
        return returnSuccess(map);
    }

    /**
     * 车辆进出记录
     *
     * @param queryDTO
     */
    @GetMapping("/usercrdtm/list")
    @ApiOperation(value = "车辆进出记录", notes = "车辆进出记录")
    public Response usercrdtmList(UsercrdtmListQueryDTO queryDTO) {
        ChannelHandlerContext ctx = getChannelHandlerContext(TCPMessageSourceEnum.SERVER.clientId);
        //请求参数
        TCPMessage data = new TCPMessage();
        String msgId = MessageUtil.generateId(20);
        data.build(DateUtil.format(new Date()),
                TCPMessageSourceEnum.CLIENT_OUTER_CLOUD_SYSTEM.clientId,
                TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId,
                msgId,
                MsgTypeEnum.PARKING.name,
                SubMsgTypeEnum.PARKING_RECORD.name,
                queryDTO,
                null);
        writeAndFlush(ctx, data);
        TCPMessage tcpMessage = (TCPMessage) ConcurrentHashMapCacheUtils.getCache(msgId, 5 * 1000L);
        if (tcpMessage == null) {
            LOGGER.error("与获取数据超时");
            throw new ParkingException(ParkingException.GET_DATA_TIMEOUT_FROM_SERVER);
        }
        JSONObject result = (JSONObject) tcpMessage.getResponse().getResult();
        if(result==null||result.size()==0){
            Map<String,Object> resultMap = Maps.newHashMap();
            resultMap.put("total",0);
            resultMap.put("list",Lists.newArrayList());
            return returnSuccess(resultMap);
        }
        return returnSuccess(tcpMessage.getResponse().getResult());
    }

    /**
     * 车辆列表
     *
     * @param queryDTO
     */
    @GetMapping("/userinfo/list")
    @ApiOperation(value = "车辆列表", notes = "车辆列表")
    public Response userinfoList(UserinfoListQueryDTO queryDTO) {
        ChannelHandlerContext ctx = getChannelHandlerContext(TCPMessageSourceEnum.SERVER.clientId);
        //请求参数
        TCPMessage data = new TCPMessage();
        String msgId = MessageUtil.generateId(20);
        data.build(DateUtil.format(new Date()),
                TCPMessageSourceEnum.CLIENT_OUTER_CLOUD_SYSTEM.clientId,
                TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId,
                msgId,
                MsgTypeEnum.PARKING.name,
                SubMsgTypeEnum.CAR_LIST.name,
                queryDTO,
                null);
        writeAndFlush(ctx, data);
        TCPMessage tcpMessage = (TCPMessage) ConcurrentHashMapCacheUtils.getCache(msgId, 5 * 1000L);
        if (tcpMessage == null) {
            LOGGER.error("与获取数据超时");
            throw new ParkingException(ParkingException.GET_DATA_TIMEOUT_FROM_SERVER);
        }
        JSONObject result = (JSONObject) tcpMessage.getResponse().getResult();
        if(result==null||result.size()==0){
            Map<String,Object> resultMap = Maps.newHashMap();
            resultMap.put("total",0);
            resultMap.put("list",Lists.newArrayList());
            return returnSuccess(resultMap);
        }
        return returnSuccess(tcpMessage.getResponse().getResult());

    }

    /**
     * 车辆到期状态（枚举：未到期/已过期）
     */
    @GetMapping("/userinfo/expire-status")
    @ApiOperation(value = "车辆到期状态（枚举：未到期/已过期）", notes = "车辆到期状态（枚举：未到期/已过期）")
    public Response userinfoExpireStatus() {
        List<Map<String, String>> results = Lists.newArrayList();
        for (ExpireStatusEnum expireStatusEnum : ExpireStatusEnum.values()) {
            Map<String, String> map = new HashMap<>();
            map.put("value", String.valueOf(expireStatusEnum.name));
            map.put("displayName", expireStatusEnum.name);
            results.add(map);
        }
        return returnSuccess(results);
    }


    /**
     * 车辆详情
     *
     * @param queryDTO
     */
    @GetMapping("/userinfo/detail")
    @ApiOperation(value = "车辆详情", notes = "车辆详情")
    public Response userinfoDetail(UserinfoDetailQueryDTO queryDTO) {
        ChannelHandlerContext ctx = getChannelHandlerContext(TCPMessageSourceEnum.SERVER.clientId);
        //请求参数
        TCPMessage data = new TCPMessage();
        String msgId = MessageUtil.generateId(20);
        data.build(DateUtil.format(new Date()),
                TCPMessageSourceEnum.CLIENT_OUTER_CLOUD_SYSTEM.clientId,
                TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId,
                msgId,
                MsgTypeEnum.PARKING.name,
                SubMsgTypeEnum.CAR_DETAIL.name,
                queryDTO,
                null);
        writeAndFlush(ctx, data);
        TCPMessage tcpMessage = (TCPMessage) ConcurrentHashMapCacheUtils.getCache(msgId, 30 * 1000L);
        if (tcpMessage == null) {
            LOGGER.error("与获取数据超时");
            tcpMessage = (TCPMessage) ConcurrentHashMapCacheUtils.getCache(msgId, 30 * 1000L);
            if (tcpMessage == null) {
                throw new BusinessException(ParkingException.GET_DATA_TIMEOUT_FROM_SERVER);
            }
        }
        return returnSuccess(tcpMessage.getResponse().getResult());
    }


    /**
     * 修改初始化车位数
     *
     * @param dto
     */
    @ApiOperation(value = "修改初始化车位数", notes = "修改初始化车位数")
    @RequestMapping(value = "/real-count/init", method = RequestMethod.PUT)
    public Response updateRealCountInitParams(@RequestBody @ApiParam UsercrdtmRealCountQueryDTO dto) {
        //查找初始配置
        if (StringUtil.isEmpty(dto.getClientId())) {
            //后期由前端传入，暂写枚举
            dto.setClientId(TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId);
        }
        ParkingRealCountInit parkingRealCountInit = parkingRealCountInitService.updateRealCountInitParams(dto);
        return returnSuccess(parkingRealCountInit, "设置成功");
    }

    @ApiOperation(value = "新增初始化车位数", notes = "新增初始化车位数")
    @RequestMapping(value = "/real-count/init", method = RequestMethod.POST)
    public Response saveRealCountInitParams(@RequestBody @ApiParam UsercrdtmRealCountQueryDTO dto) {
        //查找初始配置
        if (StringUtil.isEmpty(dto.getClientId())) {
            //后期由前端传入，暂写枚举
            dto.setClientId(TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId);
        }
        ParkingRealCountInit parkingRealCountInit = parkingRealCountInitService.saveRealCountInitParams(dto);
        return returnSuccess(parkingRealCountInit, "设置成功");
    }

    @ApiOperation(value = "查询车位数初始化配置", notes = "查询车位数初始化配置")
    @RequestMapping(value = "/real-count/init", method = RequestMethod.GET)
    public Response queryRealCountInitParams(@RequestParam(value = "clientId", required = false) String clientId) {
        //查找初始配置
        if (StringUtil.isEmpty(clientId)) {
            //后期由前端传入，暂写枚举
            clientId = TCPMessageSourceEnum.CLIENT_INNER_CAR_SYSTEM.clientId;
        }
        return returnSuccess(parkingRealCountInitService.queryRealCountInitParams(clientId));
    }

}
