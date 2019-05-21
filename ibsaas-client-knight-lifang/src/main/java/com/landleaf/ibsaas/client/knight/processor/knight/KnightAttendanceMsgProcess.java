package com.landleaf.ibsaas.client.knight.processor.knight;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.client.knight.processor.LiFangHttpProvider;
import com.landleaf.ibsaas.common.domain.knight.KnightResponse;
import com.landleaf.ibsaas.common.domain.knight.attendance.AddAttendanceRecordDTO;
import com.landleaf.ibsaas.common.domain.knight.attendance.QueryAttendanceRecordDTO;
import com.landleaf.ibsaas.common.domain.knight.attendance.QueryAttendanceResultDTO;
import com.landleaf.ibsaas.common.domain.knight.depart.AddDepartDTO;
import com.landleaf.ibsaas.common.domain.knight.depart.DeleteDepartDTO;
import com.landleaf.ibsaas.common.domain.knight.depart.QueryDepartDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 考勤处理
 */
@Component
public class KnightAttendanceMsgProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnightAttendanceMsgProcess.class);

    @Autowired
    private LiFangHttpProvider liFangHttpProvider;

    /**
     * 获取制定日期区间的考勤记录信息
     * @param requestBody
     * @return
     */
    public KnightResponse getAttendanceResult(QueryAttendanceResultDTO requestBody) {
        LOGGER.info("收到【获取制定日期区间的考勤记录信息】请求,{}", JSON.toJSONString(requestBody));
        Map<String,Object> map = Maps.newHashMap();
        map.put("BgnDate",requestBody.getBgnDate());
        map.put("EndDate",requestBody.getEndDate());
        map.put("IgnoreSysNo",requestBody.getIgnoreSysNo());
        map.put("IgnoreDptId",requestBody.getIgnoreDptId());
        map.put("curPage",requestBody.getCurPage());
        map.put("pageSize",requestBody.getPageSize());
        String param = JSON.toJSONString(map);
        KnightResponse knightResponse = liFangHttpProvider.getAttendanceResult(param);
        //返回数据
        return knightResponse;
    }
    /**
     * 考勤打卡记录分页查询
     * @param requestBody
     * @return
     */
    public KnightResponse getAttendanceRecord(QueryAttendanceRecordDTO requestBody) {
        LOGGER.info("收到【考勤打卡记录分页查询】请求,{}", JSON.toJSONString(requestBody));
        Map<String,Object> map = Maps.newHashMap();
        map.put("BgnDate",requestBody.getBgnDate());
        map.put("EndDate",requestBody.getEndDate());
        map.put("CurPage",requestBody.getCurPage());
        map.put("PageSize",requestBody.getPageSize());
        String param = JSON.toJSONString(map);
        KnightResponse knightResponse = liFangHttpProvider.getAttendanceRecord(param);
        //返回数据
        return knightResponse;
    }
    /**
     * 添加打卡记录
     * @param requestBody
     * @return
     */
    public KnightResponse setAttendanceRecord(AddAttendanceRecordDTO requestBody) {
        LOGGER.info("收到【添加打卡记录】请求,{}", JSON.toJSONString(requestBody));
        Map<String,Object> map = Maps.newHashMap();
        map.put("Serial",requestBody.getSerial());
        map.put("DoorId",requestBody.getDoorId());
        map.put("CardTime",requestBody.getCardTime());
        map.put("Describe",requestBody.getDescribe());
        String param = JSON.toJSONString(map);
        KnightResponse knightResponse = liFangHttpProvider.setAttendanceRecord(param);
        //返回数据
        return knightResponse;
    }

}
