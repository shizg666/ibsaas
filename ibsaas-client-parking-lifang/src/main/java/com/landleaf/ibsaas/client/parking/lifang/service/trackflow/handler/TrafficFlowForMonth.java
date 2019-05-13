package com.landleaf.ibsaas.client.parking.lifang.service.trackflow.handler;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.client.parking.lifang.enums.ChannelTypeEnum;
import com.landleaf.ibsaas.client.parking.lifang.service.IUsercrdtmService;
import com.landleaf.ibsaas.client.parking.lifang.service.trackflow.AbstractTrafficFlowHandler;
import com.landleaf.ibsaas.client.parking.lifang.service.trackflow.TrafficFlowAnnoation;
import com.landleaf.ibsaas.common.domain.parking.request.UsercrdtmInHistoryQueryDTO;
import com.landleaf.ibsaas.common.enums.parking.UsercrdtmInHistoryQueryTypeEnum;
import com.landleaf.ibsaas.common.utils.date.DateUtil;
import com.landleaf.ibsaas.common.utils.date.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * {@link UsercrdtmInHistoryQueryTypeEnum}
 */
@TrafficFlowAnnoation(code = "month")
@Component
public class TrafficFlowForMonth extends AbstractTrafficFlowHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(TrafficFlowForMonth.class);


    @Autowired
    private IUsercrdtmService usercrdtmService;

    @Override
    public List<UsercrdtmInHistoryQueryDTO> handle(String code, UsercrdtmInHistoryQueryDTO queryDTO) {
        List<UsercrdtmInHistoryQueryDTO> result = Lists.newArrayList();
        String startTime = queryDTO.getStartTime();
        String endTime = queryDTO.getEndTime();
        List<Date> monthList = DateUtils.getMonthList(startTime, endTime);
        //年的话查数量吧...
        for (Date date : monthList) {
            UsercrdtmInHistoryQueryDTO temp = new UsercrdtmInHistoryQueryDTO();
            BeanUtils.copyProperties(queryDTO, temp);
            temp.setCurrent(DateUtil.format(date, "yyyy-MM"));
            result.add(temp);
            Date startDateForMonth = DateUtils.getStartDateForMonth(date);
            Date endDateForMonth = DateUtils.getEndDateForMonth(date);
            String start = DateUtil.format(startDateForMonth);
            String end = DateUtil.format(endDateForMonth);
            int count = usercrdtmService.selectCountBetween(start, end, ChannelTypeEnum.IN.type);
            temp.setCount(count);
        }
        return result;
    }




}
