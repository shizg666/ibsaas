package com.landleaf.ibsaas.common.utils;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.utils.date.CalendarUtil;
import com.landleaf.ibsaas.common.utils.date.DateUtil;
import com.landleaf.ibsaas.common.utils.date.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/12/16 11:40
 * @description:
 */
public class HlVlUtil {


    public static List<String> getXs(Date start, Date end, Integer dateType){
        List<String> result = Lists.newArrayList();
        String startTime = CalendarUtil.date2Str(start);
        String endTime = CalendarUtil.date2Str(end);
        List<Date> dateList;
        //根据维度生成
        switch (dateType) {
            case 1:
                //时
                dateList = DateUtils.getHourList(startTime, endTime);
                for (Date date : dateList) {
                    result.add(com.landleaf.ibsaas.common.utils.date.DateUtil.format(date, "yyyy-MM-dd HH:mm"));
                }
                break;
            case 2:
                //日
                dateList = DateUtils.getDayList(startTime, endTime);
                for (Date date : dateList) {
                    result.add(com.landleaf.ibsaas.common.utils.date.DateUtil.format(date, "yyyy-MM-dd"));
                }
                break;
            case 3:
                //月
                dateList = DateUtils.getMonthList(startTime, endTime);
                for (Date date : dateList) {
                    result.add(com.landleaf.ibsaas.common.utils.date.DateUtil.format(date, "yyyy-MM"));
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
