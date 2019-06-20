package com.landleaf.ibsaas.common.utils.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Lokiy
 * @date 2019/6/19 16:49
 * @description: 日期处理帮助类
 */
public class CalendarUtil {


    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";


    /**
     * date转str
     * @param date
     * @return
     */
    public static String date2Str(Date date){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
        return df.format(date2LocalDateTime(date));
    }

    /**
     * str转date
     * @param dateStr
     * @return
     */
    public static Date str2Date(String dateStr){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, df);
        return localDateTime2Date(localDateTime);
    }

    /**
     * date转localDateTime
     * @param date
     * @return
     */
    public static LocalDateTime date2LocalDateTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    /**
     * localDateTime转date
     * @param localDateTime
     * @return
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zoneId).toInstant();
        return Date.from(instant);
    }


    /**
     * date转localDate
     * @param date
     * @return
     */
    public static LocalDate date2LocalDate(Date date){
        return date2LocalDateTime(date).toLocalDate();
    }

    /**
     * localDate转date
     * @param localDate
     * @return
     */
    public static Date localDate2Date(LocalDate localDate){
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zoneId).toInstant();
        return Date.from(instant);
    }


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
        return date2LocalDateTime(date).getYear();
    }


    /**
     * 获取日期的月份
     * @param date
     * @return
     */
    public static int month(Date date){
        return date2LocalDateTime(date).getMonthValue();
    }

    /**
     * 获取日
     * @param date
     * @return
     */
    public static int day(Date date){
        return date2LocalDateTime(date).getDayOfMonth();
    }


    /**
     * 获取年月字符串
     * @param year
     * @param month
     * @return
     */
    public static String getYearAndMonth(int year, int month){
        return year + "-" + (month > 9? month : "0" + month);
    }

    /**
     * 获取年月字符串
     * @param date
     * @return
     */
    public static String getYearAndMonth(Date date){
        LocalDateTime localDateTime = date2LocalDateTime(date);
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        return year + "-" + (month > 9? month : "0" + month);
    }


    public static Date offsetDate(Date date, long amountToAdd, TemporalUnit unit){
        return localDateTime2Date(date2LocalDateTime(date).plus(amountToAdd, unit));
    }


    /**
     * 前一小时
     * @param date
     * @return
     */
    public static Date prevHour(Date date){
        return offsetDate(date, -1 , ChronoUnit.HOURS);
    }

    /**
     * 后一小时
     * @param date
     * @return
     */
    public static Date nextHour(Date date){
        return offsetDate(date, 1, ChronoUnit.HOURS);
    }




    /**
     * 前一天
     * @param date
     * @return
     */
    public static Date prevDay(Date date){
        return offsetDate(date, -1 , ChronoUnit.DAYS);
    }

    /**
     * 后一天
     * @param date
     * @return
     */
    public static Date nextDay(Date date){
        return offsetDate(date, 1, ChronoUnit.DAYS);
    }


    /**
     * 前一月
     * @param date
     * @return
     */
    public static Date prevMonth(Date date){
        return offsetDate(date, -1, ChronoUnit.MONTHS);
    }

    /**
     * 后一月
     * @param date
     * @return
     */
    public static Date nextMonth(Date date){
        return offsetDate(date, 1, ChronoUnit.MONTHS);
    }


    /**
     * 前一月
     * @param date
     * @return
     */
    public static Date prevYear(Date date){
        return offsetDate(date, -1, ChronoUnit.YEARS);
    }

    /**
     * 后一月
     * @param date
     * @return
     */
    public static Date nextYear(Date date){
        return offsetDate(date, 1, ChronoUnit.YEARS);
    }

}
