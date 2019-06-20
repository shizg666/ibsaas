package com.landleaf.ibsaas.web.web.service.energyflow.handler.date;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.jingrui.common.domain.dto.web.statusplc.StatusPlcHistoryQueryDTO;
import com.landleaf.jingrui.common.domain.dto.web.statusplc.StatusPlcHistoryResponseDTO;
import com.landleaf.jingrui.common.domain.influxdb.StatusPLC;
import com.landleaf.jingrui.common.enums.web.StatusPlcHistoryQueryTypeEnum;
import com.landleaf.jingrui.common.service.historyflow.AbstractFlowHandler;
import com.landleaf.jingrui.common.service.historyflow.FlowAnnoation;
import com.landleaf.jingrui.common.service.historyflow.FlowHandlerSelector;
import com.landleaf.jingrui.common.service.influxdb.IStatusPlcService;
import com.landleaf.jingrui.common.utils.date.DateUtil;
import com.landleaf.jingrui.common.utils.date.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * {@link StatusPlcHistoryQueryTypeEnum}
 */
@FlowAnnoation(code = "hour")
@Component
public class EnergyFlowForHour extends AbstractFlowHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(EnergyFlowForHour.class);


    @Autowired
    private FlowHandlerSelector flowHandlerSelector;
    @Autowired
    private IStatusPlcService statusPlcService;

    @Override
    public Map<String, List<String>> handle(StatusPlcHistoryQueryDTO queryDTO, String addressId, int type) {
        String startTime = queryDTO.getStartTime();
        String endTime = queryDTO.getEndTime();
        String clientid = queryDTO.getClientid();
        String deviceid = queryDTO.getDeviceid();
        Map<String, List<String>> dataMap = Maps.newHashMap();
        List<StatusPlcHistoryResponseDTO> tmpList = Lists.newArrayList();
        String utcStartTime = startTime;
        String utcEndTime = endTime;
        try {
            utcStartTime = DateUtils.CSTToUTC(startTime);
            utcEndTime = DateUtils.CSTToUTC(endTime);
        } catch (ParseException e) {
            LOGGER.info("时间转换错误startTime：{},endTime:{}", startTime, endTime);
        }
        List<StatusPLC> statusPLCList = statusPlcService.selectAvgBetween(clientid, deviceid, addressId, utcStartTime, utcEndTime);
        List<Date> hourList = DateUtils.getHourList(startTime,endTime);
        String latestValue = "0";
        try {
            double dayAvag = statusPLCList.stream().mapToDouble(i -> {
                return Double.parseDouble(i.getAddressValue());
            }).average().getAsDouble();//平均值
            latestValue=new BigDecimal(dayAvag).divide(new BigDecimal(10),2).setScale(2, BigDecimal.ROUND_HALF_DOWN).toPlainString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Date date : hourList) {
            if(date.after(new Date())){
                continue;
            }
            StatusPlcHistoryResponseDTO temp = new StatusPlcHistoryResponseDTO();
            Date startDateFoHour = DateUtils.getStartDateForHour(date);
            Date endDateForHour = DateUtils.getEndDateForHour(date);
            double asDouble = 0d;
            OptionalDouble average = statusPLCList.stream().filter(i -> {
                if (DateUtils.convert(i.getTime()).getTime() >= startDateFoHour.getTime() &&
                        DateUtils.convert(i.getTime()).getTime() < endDateForHour.getTime()) {
                    return true;
                }
                return false;
            }).mapToDouble(i -> {
                return Double.parseDouble(i.getAddressValue());
            }).average();//平均值
            if (average != null && average.isPresent()) {
                asDouble = average.getAsDouble();
                try {
                    latestValue=new BigDecimal(asDouble).divide(new BigDecimal(10),2).setScale(2, BigDecimal.ROUND_HALF_DOWN).toPlainString();
                } catch (Exception e) {
                    continue;
                }
            }
            temp.setY(latestValue);
            temp.setX(DateUtil.format(date, "yyyy-MM-dd HH:mm"));
            tmpList.add(temp);
        }
        List<String> xList = Lists.newArrayList();
        List<String> yList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(tmpList)) {
            xList = tmpList.stream().map(StatusPlcHistoryResponseDTO::getX).collect(Collectors.toList());
            yList = tmpList.stream().map(StatusPlcHistoryResponseDTO::getY).collect(Collectors.toList());
        }
        dataMap.put("x", xList);
        dataMap.put("y", yList);
        return dataMap;
    }


    public static void main(String[] args) {
        List<Date> hourList = DateUtils.getHourList("2019-06-18 00:00:00","2019-06-18 23:59:59");
        for (Date date : hourList) {
            Date startDateFoHour = DateUtils.getStartDateForHour(date);
            Date endDateForHour = DateUtils.getEndDateForHour(date);
            System.out.println(startDateFoHour);
            System.out.println(endDateForHour);
        }
        System.out.println(hourList);

    }

}
