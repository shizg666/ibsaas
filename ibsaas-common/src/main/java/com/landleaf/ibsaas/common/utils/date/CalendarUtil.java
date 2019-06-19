package com.landleaf.ibsaas.common.utils.date;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Lokiy
 * @date 2019/6/19 16:49
 * @description: 日期处理帮助类
 */
public class CalendarUtil {


    /**
     * 获取Calendar对象
     * @param date
     * @return
     */
    public static Calendar calendar(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 获取日期的年份
     * @param date
     * @return
     */
    public static int year(Date date){
        return calendar(date).get(Calendar.YEAR);
    }


    /**
     * 获取日期的月份
     * @param date
     * @return
     */
    public static int month(Date date){
        return calendar(date).get(Calendar.MONTH) + 1;
    }
}
