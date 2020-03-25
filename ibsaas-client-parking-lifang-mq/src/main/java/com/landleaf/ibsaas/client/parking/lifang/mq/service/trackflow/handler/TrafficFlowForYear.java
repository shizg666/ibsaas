package com.landleaf.ibsaas.client.parking.lifang.mq.service.trackflow.handler;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.client.parking.lifang.mq.enums.ChannelTypeEnum;
import com.landleaf.ibsaas.client.parking.lifang.mq.service.IUsercrdtmService;
import com.landleaf.ibsaas.client.parking.lifang.mq.service.trackflow.AbstractTrafficFlowHandler;
import com.landleaf.ibsaas.client.parking.lifang.mq.service.trackflow.TrafficFlowAnnoation;
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
@TrafficFlowAnnoation(code = "year")
@Component
public class TrafficFlowForYear extends AbstractTrafficFlowHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(TrafficFlowForYear.class);


    @Autowired
    private IUsercrdtmService usercrdtmService;

    @Override
    public List<UsercrdtmInHistoryQueryDTO> handle(String code, UsercrdtmInHistoryQueryDTO queryDTO) {
        List<UsercrdtmInHistoryQueryDTO> result = Lists.newArrayList();
        String startTime = queryDTO.getStartTime();
        String endTime = queryDTO.getEndTime();
        List<Date> yearList = DateUtils.getYearList(startTime, endTime);
        //年的话查数量吧...
        for (Date date : yearList) {
            UsercrdtmInHistoryQueryDTO temp = new UsercrdtmInHistoryQueryDTO();
            BeanUtils.copyProperties(queryDTO, temp);
            temp.setCurrent(DateUtil.format(date, "yyyy"));
            result.add(temp);
            Date startDateForYear = DateUtils.getStartDateForYear(date);
            Date endDateForYear = DateUtils.getEndDateForYear(date);
            String start = DateUtil.format(startDateForYear);
            String end = DateUtil.format(endDateForYear);
            int count = usercrdtmService.selectCountBetween(start, end, ChannelTypeEnum.IN.type);
            temp.setCount(count);
        }
        return result;
    }


}
