package com.landleaf.ibsaas.web.web.controller.parking;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.parking.ParkingRealCountInit;
import com.landleaf.ibsaas.common.domain.parking.TCPMessage;
import com.landleaf.ibsaas.common.domain.parking.request.*;
import com.landleaf.ibsaas.common.domain.parking.response.ChargeruleResponseDTO;
import com.landleaf.ibsaas.common.domain.parking.response.UserinfoResponseDTO;
import com.landleaf.ibsaas.common.enums.parking.*;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.utils.MessageUtil;
import com.landleaf.ibsaas.common.utils.date.DateUtil;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import com.landleaf.ibsaas.web.tcp.cache.ConcurrentHashMapCacheUtils;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.exception.ParkingException;
import com.landleaf.ibsaas.web.web.service.parking.IParkingRealCountInitService;
import com.landleaf.ibsaas.web.web.service.parking.IParkingServeice;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * 停车业务相关操作
 */
@RestController
@RequestMapping("/parking")
@Api(value = "/parking", description = "停车业务相关操作")
public class ParkingController extends BasicController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingController.class);


    @Autowired
    private IParkingServeice parkingServeice;
    @Autowired
    private IParkingRealCountInitService parkingRealCountInitService;

    /**
     * 获取通道类型
     */
    @GetMapping("/channels")
    @ApiOperation(value = "获取通道列表", notes = "获取通道列表")
    public Response getChannelList() {

        ChannelListQueryDTO queryDTO = new ChannelListQueryDTO();
        Response response = parkingServeice.getChannelList(queryDTO);
        return response;
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
        Response response=parkingServeice.queryAllChargerule();
       return  response;
    }

    /**
     * 车位实时监控
     *
     * @param queryDTO
     */
    @GetMapping("/usercrdtm/real-count")
    @ApiOperation(value = "车位实时监控", notes = "车位实时监控")
    public Response realCount(@ApiParam UsercrdtmRealCountQueryDTO queryDTO) {
        Response response=parkingServeice.realCount(queryDTO);
        return  response;
    }

    /**
     * 车位分时段监控
     *
     * @param queryDTO
     */
    @GetMapping("/usercrdtm/real-count/hour")
    @ApiOperation(value = "车位分时段监控", notes = "车位分时段监控")
    public Response realCountFHour(UsercrdtmRealCountQueryByHourDTO queryDTO) {
        Map<String, Object> map = parkingServeice.realCountFHour(queryDTO);
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
        JSONObject result =parkingServeice.usercrdtmList(queryDTO);
        if (result == null || result.size() == 0) {
            Map<String, Object> resultMap = Maps.newHashMap();
            resultMap.put("total", 0);
            resultMap.put("list", Lists.newArrayList());
            return returnSuccess(resultMap);
        }
        return returnSuccess(result);
    }

    /**
     * 车辆列表
     *
     * @param queryDTO
     */
    @GetMapping("/userinfo/list")
    @ApiOperation(value = "车辆列表", notes = "车辆列表")
    public Response userinfoList(UserinfoListQueryDTO queryDTO) {
        JSONObject result =parkingServeice.userinfoList(queryDTO);
        if (result == null || result.size() == 0) {
            Map<String, Object> resultMap = Maps.newHashMap();
            resultMap.put("list", Lists.newArrayList());
            resultMap.put("total", 0);
            return returnSuccess(resultMap);
        }
        return returnSuccess(result);

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
        UserinfoResponseDTO result= parkingServeice.userinfoDetail(queryDTO);
        return returnSuccess(result);
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

    @ApiOperation(value = "查询历史车流量", notes = "查询历史车流量")
    @RequestMapping(value = "/traffic-flow", method = RequestMethod.POST)
    public Response trafficFlow(@RequestBody UsercrdtmInHistoryQueryDTO queryDTO) {
        List<UsercrdtmInHistoryQueryDTO> historyQueryDTOS=  parkingServeice.trafficFlow(queryDTO);
        Map<String, Object> map = null;
        try {
            List<String> xList = historyQueryDTOS.stream().map(i -> i.getCurrent()).collect(Collectors.toList());
            List<Integer> yList = historyQueryDTOS.stream().map(i -> {
                Integer count = i.getCount();
                if (count == null) {
                    return 0;
                } else {
                    return count;
                }
            }).collect(Collectors.toList());
            map = Maps.newHashMap();
            map.put("x", xList);
            map.put("y", yList);
        } catch (Exception e) {
            LOGGER.error("数据类型格式错误");
            throw new BusinessException("接收到数据类型格式错误");
        }
        return returnSuccess(map);
    }


}
