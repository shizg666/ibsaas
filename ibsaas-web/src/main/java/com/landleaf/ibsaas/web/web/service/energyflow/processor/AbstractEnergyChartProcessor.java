package com.landleaf.ibsaas.web.web.service.energyflow.processor;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyReportQueryVO;
import com.landleaf.ibsaas.common.utils.date.DateUtil;
import com.landleaf.ibsaas.common.utils.date.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class AbstractEnergyChartProcessor  {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractEnergyChartProcessor.class);


    public List<String> getDateList(EnergyReportQueryVO requestBody) {
        List<String> result = Lists.newArrayList();
        String startTime = requestBody.getStartTime();
        String endTime = requestBody.getEndTime();
        Integer dateType = requestBody.getDateType();
        List<Date> dateList = Lists.newArrayList();
        //根据维度生成
        switch (dateType) {
            case 1:
                //时
                dateList = DateUtils.getHourList(startTime, endTime);
                for (Date date : dateList) {
                    result.add(DateUtil.format(date, "yyyy-MM-dd HH:mm"));
                }
                break;
            case 2:
                //日
                dateList = DateUtils.getDayList(startTime, endTime);
                for (Date date : dateList) {
                    result.add(DateUtil.format(date, "yyyy-MM-dd"));
                }
                break;
            case 3:
                //月
                dateList = DateUtils.getMonthList(startTime, endTime);
                for (Date date : dateList) {
                    result.add(DateUtil.format(date, "yyyy-MM"));
                }
                break;
            case 4:
                //年
                dateList = DateUtils.getYearList(startTime, endTime);
                for (Date date : dateList) {
                    result.add(DateUtil.format(date, "yyyy"));
                }
                break;
        }
        return result;
    }


}
