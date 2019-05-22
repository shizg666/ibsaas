package com.landleaf.ibsaas.client.knight.processor.knight;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.client.knight.processor.LiFangHttpProvider;
import com.landleaf.ibsaas.client.knight.service.IKQCardRecordService;
import com.landleaf.ibsaas.client.knight.service.IMjReguerService;
import com.landleaf.ibsaas.client.knight.service.IStationService;
import com.landleaf.ibsaas.common.domain.knight.KnightResponse;
import com.landleaf.ibsaas.common.domain.knight.control.*;
import com.landleaf.ibsaas.common.domain.knight.depart.AddDepartDTO;
import com.landleaf.ibsaas.common.domain.knight.depart.DeleteDepartDTO;
import com.landleaf.ibsaas.common.domain.knight.depart.QueryDepartDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 门禁处理
 */
@Component
public class KnightControlMsgProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightControlMsgProcess.class);

    @Autowired
    private LiFangHttpProvider liFangHttpProvider;
    @Autowired
    private IMjReguerService mjReguerService;
    @Autowired
    private IKQCardRecordService kqCardRecordService;
    @Autowired
    private IStationService stationService;


    /**
     * 注册人员（人关联门）--未开通(通过接口)
     * @param requestBody
     * @return
     */
    public KnightResponse registeruserByInterface(RegisterUserDTO requestBody) {
        LOGGER.info("收到【注册人员】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = liFangHttpProvider.registeruser(param);
        //返回数据
        return knightResponse;
    }
    /**
     * 注册人员（人关联门）--通过本地数据库
     * @param requestBody
     * @return
     */
    public KnightResponse registeruserByDb(RegisterUserByDbDTO requestBody) {
        LOGGER.info("收到【注册人员】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = new KnightResponse();

        mjReguerService.registeruser(requestBody);
        knightResponse.setResult("200");
        knightResponse.setResultInfo("操作成功");
        //返回数据
        return knightResponse;
    }
    /**
     * 解除人员权限--未开通
     * @param requestBody
     * @return
     */
    public KnightResponse unregisteruser(UnRegisterUserDTO requestBody) {
        LOGGER.info("收到【解除人员权限】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = liFangHttpProvider.unregisteruser(param);
        //返回数据
        return knightResponse;
    }
    /**
     * 解除人员权限--通过数据库
     * @param requestBody
     * @return
     */
    public KnightResponse unregisteruserByDb(UnRegisterUserByDbDTO requestBody) {
        LOGGER.info("收到【解除人员权限】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = new KnightResponse();

        mjReguerService.unregisteruser(requestBody);
        knightResponse.setResult("200");
        knightResponse.setResultInfo("操作成功");
        //返回数据
        return knightResponse;
    }
    /**
     * 查询人员权限--通过数据库
     * @param requestBody
     * @return
     */
    public KnightResponse queryRegisteruserByDb(QueryRegisterUserByDbDTO requestBody) {
        LOGGER.info("收到【查询人员权限】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = new KnightResponse();

        PageInfo pageInfo = mjReguerService.queryRegisteruserByDb(requestBody);
        knightResponse.setObj(pageInfo);
        knightResponse.setResult("200");
        knightResponse.setResultInfo("操作成功");
        //返回数据
        return knightResponse;
    }


    /**
     * 获取全部设备信息
     * @param requestBody
     * @return
     */
    public KnightResponse getMjDeviceAll(QueryMjDeviceDTO requestBody) {
        LOGGER.info("收到【获取全部设备信息】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = liFangHttpProvider.getMjDeviceAll(param);
        //返回数据
        return knightResponse;
    }
    /**
     * 根据设备id获取设备信息
     * @param requestBody
     * @return
     */
    public KnightResponse getMjDeviceById(QueryMjDeviceDTO requestBody) {
        LOGGER.info("收到【根据设备id获取设备信息】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = liFangHttpProvider.getMjDeviceById(param);
        //返回数据
        return knightResponse;
    }
    /**
     * 根据设备id集合获取设备信息列表--通过数据库
     * @param requestBody
     * @return
     */
    public KnightResponse getMjDeviceByIdsDb(QueryMjDeviceDTO requestBody) {
        KnightResponse knightResponse = new KnightResponse();
        LOGGER.info("收到【根据设备ids获取设备信息】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        List<Station> stations=stationService.getMjDeviceByIdsDb(requestBody.getDeviceSysIds());
        knightResponse.setObj(stations);
        knightResponse.setResult("200");
        knightResponse.setResultInfo("操作成功");
        return knightResponse;
    }
    /**
     * 分页查询设备列表--通过数据库
     * @param requestBody
     * @return
     */
    public KnightResponse getMjDeviceByPageDb(QueryMjDeviceDTO requestBody) {
        KnightResponse knightResponse = new KnightResponse();
        LOGGER.info("收到【分页查询设备列表】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        PageInfo<Station> pageInfo=stationService.getMjDeviceByPageDb(requestBody.getCurPage(),requestBody.getPageSize());
        knightResponse.setObj(pageInfo);
        knightResponse.setResult("200");
        knightResponse.setResultInfo("操作成功");
        return knightResponse;
    }
    /**
     * 获取全部门信息分页查询
     * @param requestBody
     * @return
     */
    public KnightResponse getDoorInfoAll(QueryMjDoorDTO requestBody) {
        LOGGER.info("收到【获取全部门信息分页查询】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = liFangHttpProvider.getDoorInfoAll(param);
        //返回数据
        return knightResponse;
    }
    /**
     * 根据设备id获取设备信息
     * @param requestBody
     * @return
     */
    public KnightResponse getDoorInfoById(QueryMjDoorByIdDTO requestBody) {
        LOGGER.info("收到【根据门id获取门信息】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = liFangHttpProvider.getDoorInfoById(param);
        //返回数据
        return knightResponse;
    }
    /**
     * 门禁记录分页查询--通过数据库
     * @param requestBody
     * @return
     */
    public KnightResponse mjOpenDoorRecordByDb(QueryMjDoorOpenRecordDTO requestBody) {
        KnightResponse knightResponse = new KnightResponse();
        LOGGER.info("收到【门禁记录分页查询】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        PageInfo pageInfo = kqCardRecordService.mjOpenDoorRecordByDb(requestBody);
        knightResponse.setObj(pageInfo);
        knightResponse.setResult("200");
        knightResponse.setResultInfo("操作成功");
        return knightResponse;
    }
    /**
     * 门禁记录分页查询---太慢
     * @param requestBody
     * @return
     */
    public KnightResponse mjOpenDoorRecord(QueryMjDoorOpenRecordDTO requestBody) {
        LOGGER.info("收到【门禁记录分页查询】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = liFangHttpProvider.mjOpenDoorRecord(param);
        //返回数据
        return knightResponse;
    }
    /**
     * 门禁报警记录分页查询
     * @param requestBody
     * @return
     */
    public KnightResponse mJUrgentEventRecord(QueryMjUrgentEventRecordDTO requestBody) {
        LOGGER.info("收到【门禁报警记录分页查询】请求,{}", JSON.toJSONString(requestBody));
        String param = JSON.toJSONString(requestBody);
        KnightResponse knightResponse = liFangHttpProvider.mJUrgentEventRecord(param);
        //返回数据
        return knightResponse;
    }

}
