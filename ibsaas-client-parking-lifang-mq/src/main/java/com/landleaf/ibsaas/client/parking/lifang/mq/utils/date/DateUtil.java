package com.landleaf.ibsaas.client.parking.lifang.mq.utils.date;

import com.landleaf.ibsaas.client.parking.lifang.mq.utils.number.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
    public static final String FORMAT_DATE_SHORT = "yyyyMMdd";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_TIMEMILLI = "yyyyMMddHHmmssSSS";
    public static final String START_SUF_TIME = " 00:00:00";
    public static final String VAL_SUF_TIME = " 23:59:59";

    public static final SimpleDateFormat FORMAT_DATE_OBJ = new SimpleDateFormat(FORMAT_DATE);

    /**
     * 将日期设置为传入日期的00:00:01
     *
     * @param date
     * @return
     * @author sourny.yang
     * @date 2015年12月4日
     */
    public static Date getDateByThatDayFirstMilliSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 1);
        calendar.set(Calendar.MILLISECOND, 1);
        return calendar.getTime();
    }

    /**
     * 将日期设置为传入日期的00:00:00
     *
     * @param date
     * @return
     * @author Jason.Li
     * @date 2016年3月29日下午1:47:27
     */
    public static Long getLongByThatDayZeroMilliSecond(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 说明: 得到当天的23:59:59 的long值
     * 参数: @param date
     * 参数: @return
     * 时间: 2016年8月10日下午4:26:05
     * 作者: fishZhang
     */
    public static Long getLongByThatDayLastMilliSecond(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * 将日期设置为传入日期的23:59:59
     *
     * @param date
     * @return
     * @author sourny.yang
     * @date 2015年12月4日
     */
    public static Date getDateByThatDayLastMilliSecond(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 返回第一个时间的当天的00:00:00.1距离1970-01-01 08:00:00的毫秒数和
     * 第二个时间的当天的23:59:59.999距离1970-01-01 08:00:00的毫秒数 只传入firstDate
     * 则返回当天的00:00:00的毫秒数和当天23:59:59的毫秒数 只传入lastDate
     * 则返回当月1号00:00:00的毫秒数和当天23:59:59的毫秒数 都不传 返回今天的00:00:00的毫秒数和当天23:59:59的毫秒数
     *
     * @param firstDate
     * @param lastDate
     * @return
     * @author sourny.yang
     * @date 2015年12月11日
     */
    public static List<Long> getDatesByThatDayFirstAndLastMilliSecond(Date firstDate, Date lastDate) {
        List<Long> resultList = new ArrayList<>();
        Calendar instance = Calendar.getInstance();
        if (firstDate == null && lastDate == null) {
            Date time = instance.getTime();
            resultList.add(getDateByThatDayFirstMilliSecond(time).getTime());
            resultList.add(getDateByThatDayLastMilliSecond(time).getTime());
        } else if (firstDate == null) {
            instance.set(Calendar.DAY_OF_MONTH, 1);
            resultList.add(getDateByThatDayFirstMilliSecond(instance.getTime()).getTime());
            resultList.add(getDateByThatDayLastMilliSecond(lastDate).getTime());
        } else if (lastDate == null) {
            resultList.add(getDateByThatDayFirstMilliSecond(firstDate).getTime());
            resultList.add(getDateByThatDayLastMilliSecond(firstDate).getTime());
        } else {
            resultList.add(getDateByThatDayFirstMilliSecond(firstDate).getTime());
            resultList.add(getDateByThatDayLastMilliSecond(lastDate).getTime());
        }
        return resultList;
    }

    /**
     * 根据模板格式化日期
     *
     * @param date 日期
     * @param type 模板(类似:yyyy-MM-dd HH:mm:ss)
     * @return
     * @author Jason.Li
     * @date 2015年11月2日下午3:51:26
     */
    public static String dateToString(Date date, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        String strdate = sdf.format(date);
        return strdate;
    }

    public static String chargeFormat(String src, String aim, String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(src);
            SimpleDateFormat sdfAim = new SimpleDateFormat(aim);
            Date date = sdf.parse(time);
            String strdate = sdfAim.format(date.getTime());
            return strdate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 日期字符串转换日期格式
     *
     * @param text 日期格式： yyyy-MM-dd HH:mm:ss 2015年1月10日
     * @author run
     */
    public static Date stringToDate(String text) {
        try {
            String eL = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";// 时间格式正则
            Pattern p = Pattern.compile(eL);
            Matcher m = p.matcher(text);// 字符串匹配
            if (!m.matches()) {// 是否匹配
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                return sdf.parse(text);
            }
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIME);
            return sdf.parse(text);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 日期字符串转换日期格式
     *
     * @param text 日期格式： yyyy-MM-dd HH:mm:ss 2015年1月10日
     * @author run
     */
    public static Date stringToDate(String text, String type) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(type);
            return sdf.parse(text);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前月第一天和最后一天
     *
     * @return
     * @author Jason.Li
     * @date 2015年11月2日下午3:55:53
     */
    public static String[] getThisMtStartEnd() {
        String[] startEnd = new String[2];
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
        // 获取当前月第一天：
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.MONTH, 0);
        // 设置为1号,当前日期既为本月第一天
        c1.set(Calendar.DAY_OF_MONTH, 1);
        startEnd[0] = format.format(c1.getTime());
        // 获取当前月最后一天
        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.DAY_OF_MONTH, c2.getActualMaximum(Calendar.DAY_OF_MONTH));
        startEnd[1] = format.format(c2.getTime());
        return startEnd;
    }

    /**
     * 剥去时间，剩下日期，转到前一天
     *
     * @return 返回时间的毫秒
     * @author justin.li
     * @date 2016年11月4日
     */
    public static long stripTimeLast2Long() {
        long today = stripTime(new Date()).getTime();
        long yesterday = today - (24 * 3600 * 1000);
        return yesterday;
    }

    /**
     * 剥去时间，剩下日期
     *
     * @return 返回时间的毫秒
     * @author justin.li
     * @date 2016年11月4日
     */
    public static long stripTime2Long() {
        return stripTime(new Date()).getTime();
    }

    /**
     * 剥去时间，剩下日期
     *
     * @param date
     * @return 返回时间的毫秒
     * @author justin.li
     * @date 2016年11月4日
     */
    public static long stripTime2Long(Long date) {
        if (date == null) {
            return 0;
        }
        return stripTime(new Date(date)).getTime();
    }

    /**
     * 剥去时间，剩下日期
     *
     * @param date
     * @return 返回时间
     * @author justin.li
     * @date 2016年11月4日
     */
    public static Date stripTime(Date date) {
        String dateStr = FORMAT_DATE_OBJ.format(date.getTime());
        try {
            return FORMAT_DATE_OBJ.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 对时间的加减
     *
     * @return 2015年4月11日
     * @author run
     */
    public static Date addDate(int i, int type) {
        Calendar c = Calendar.getInstance();
        c.add(type, i);
        return c.getTime();
    }

    /**
     * 对传入的时间进行加减
     *
     * @param i
     * @param type
     * @param date
     * @return 2015年7月8日
     * @author run
     */
    public static Date addDate(int i, int type, Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(type, i);
        return c.getTime();
    }

    /**
     * String类型的时间获得毫秒数
     *
     * @param text String类型的时间
     * @param type 模板(类似:yyyy-MM-dd HH:mm:ss)
     * @return
     * @throws ParseException
     * @author Jason.Li
     * @date 2015年11月2日下午3:58:51
     */
    public static long strDateToLong(String text, String type) {
        Date date = stringToDate(text, type);
        return null == date ? 0l : date.getTime();
    }

    /**
     * 毫秒数转日期
     *
     * @param times 毫秒数
     * @return
     * @author Jason.Li
     * @date 2015年11月2日下午4:12:41
     */
    public static Date longToDate(long times) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(times);
        return c.getTime();
    }

    /**
     * 毫秒数转 String 日期
     *
     * @param times 毫秒数
     *              模板(类似:yyyy-MM-dd HH:mm:ss)
     * @return
     * @author Jason.Li
     * @date 2015年11月2日下午4:12:41
     */
    public static String longToStringDate(Long times) {
        if (NumberUtils.isEmpty(times)) {
            return "";
        }
        Date date = longToDate(times);
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
        return sdf.format(date);
    }

    /**
     * 毫秒数转 String 日期
     *
     * @param times 毫秒数
     *              模板(类似:yyyy-MM-dd HH:mm:ss)
     * @return
     * @author Jason.Li
     * @date 2015年11月2日下午4:12:41
     */
    public static String longToStringTime(Long times) {
        if (NumberUtils.isEmpty(times)) {
            return "";
        }
        Date date = longToDate(times);
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIME);
        return sdf.format(date);
    }

    /**
     * 毫秒数转 String 日期
     *
     * @param times 毫秒数
     * @param type  模板(类似:yyyy-MM-dd HH:mm:ss)
     * @return
     * @author Jason.Li
     * @date 2015年11月2日下午4:12:41
     */
    public static String longToStringDate(Long times, String type) {
        if (NumberUtils.isEmpty(times)) {
            return "";
        }
        Date date = longToDate(times);
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        return sdf.format(date);
    }

    /**
     * 获取短日期
     *
     * @param date 日期
     * @return
     * @author Jason.Li
     * @date 2015年11月2日下午4:12:41
     */
    public static String getShortDay(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_SHORT);
        return sdf.format(date);
    }

    /**
     * 获取短日期
     *
     * @param date 日期
     * @return
     * @author Jason.Li
     * @date 2015年11月2日下午4:12:41
     */
    public static int getShortDayInt(Date date) {
        if (date == null) {
            return 0;
        }
        return Integer.valueOf(getShortDay(date));
    }

    /**
     * 把年转为1位
     *
     * @author meff
     */
    public static int getYear() {
        Calendar cal = Calendar.getInstance();
        int iYear = cal.get(Calendar.YEAR);

        if (iYear == 2015)
            return 1;
        if (iYear == 2016)
            return 2;
        if (iYear == 2017)
            return 3;
        if (iYear == 2018)
            return 4;
        if (iYear == 2019)
            return 5;
        if (iYear == 2020)
            return 6;
        if (iYear == 2021)
            return 7;
        if (iYear == 2022)
            return 8;
        if (iYear == 2023)
            return 9;
        else
            return 0;
    }

    /**
     * 返回当期日期,插入mongodb数据库专用
     *
     * @return Date
     * @author meff
     */
    public static Date getCurrentDateTimeForMongo() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIME);
        String currentDate = sdf.format(date);
        sdf.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT+8")));
        try {
            return sdf.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回当期日期,插入mongodb数据库专用
     *
     * @return Date
     * @author meff
     */
    public static Date getCurrentDateTimeForMongo(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIME);
        String currentDate = sdf.format(date);
        sdf.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT+8")));
        try {
            return sdf.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以long精确到秒的格式表示当前日期时间
     *
     * @return
     * @author meff
     */
    public static long getCurrentDateTimeLong() {
        Date dt = new Date();
        return dt.getTime() / 1000;
    }

    /**
     * 查询指定时间的毫秒数
     *
     * @param amount 变更天数 ,整数:1,-1,0等
     * @param time   时间点字符串,格式为:" 03:00:00"
     * @return 毫秒数
     * @author jin
     * @date 2016年3月30日
     */
    public static Long getChangeTime(int amount, String time) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, amount);
        String yesterday = new SimpleDateFormat(FORMAT_DATE).format(cal.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIME);
        Date date = null;
        try {
            date = sdf.parse(yesterday + time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 转换字符串为日期类型
     *
     * @param date
     * @return
     * @throws ParseException
     * @author Douglas Luo
     * @date 2016年4月7日 下午2:46:31
     */
    public static Date parseDate(String date) {
        try {
            return DateUtils.parseDate(date, DATE_PATTERN);
        } catch (ParseException e) {
            logger.warn("{}", e.getMessage());
        }
        return null;
    }

    /**
     * 将字符串形式的时间转成long类型的毫秒
     *
     * @param date 时间字符串，例： 2017-04-11 15:18:28
     * @return
     * @author justin.li
     * @date 2017年4月11日
     */
    public static long parseDate2long(String date) {
        try {
            return DateUtils.parseDate(date, DATE_PATTERN).getTime();
        } catch (ParseException e) {
            logger.warn("{}", e.getMessage());
        }
        return 0L;
    }

    /**
     * 转换字符串为日期类型
     *
     * @param date 时间，例：20161207
     * @return
     * @throws ParseException
     * @author justin.li
     * @date 2016年4月7日 下午2:46:31
     */
    public static Date parseShortDate(int date) {
        try {
            return DateUtils.parseDate(date + "", FORMAT_DATE_SHORT);
        } catch (ParseException e) {
            logger.warn("{}", e.getMessage());
        }
        return null;
    }

    /**
     * 转换字符串为日期类型
     *
     * @param date 时间，例：20161207
     * @return
     * @throws ParseException
     * @author justin.li
     * @date 2016年4月7日 下午2:46:31
     */
    public static Date parseShortDate(String date) {
        try {
            return DateUtils.parseDate(date, FORMAT_DATE_SHORT);
        } catch (ParseException e) {
            logger.warn("{}", e.getMessage());
        }
        return null;
    }

    /**
     * 转换字符串为时间类型
     *
     * @return
     * @throws ParseException
     * @author Douglas Luo
     * @date 2016年4月7日 下午4:56:55
     */
    public static Date parseTime(String timestamp) {
        try {
            return DateUtils.parseDate(timestamp, TIME_PATTERN);
        } catch (ParseException e) {
            logger.warn("{}", e.getMessage());
        }
        return null;
    }

    /**
     * 转换日期成字符串格式
     *
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     * @author Douglas Luo
     * @date 2016年4月7日 下午2:46:53
     */
    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 转换日期成字符串格式 格式 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     * @throws ParseException
     * @author Douglas Luo
     * @date 2016年4月7日 下午2:46:53
     */
    public static String format(Long date) {
        return format(new Date(date), PLAIN_DATE_TIME);
    }

    /**
     * 转换日期成字符串格式 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     * @throws ParseException
     * @author Douglas Luo
     * @date 2016年4月7日 下午4:38:58
     */
    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        return format(date, PLAIN_DATE_TIME);
    }

    /**
     * 转换日期成字符串格式 HH:mm:ss
     *
     * @param date
     * @return
     * @throws ParseException
     * @author Douglas Luo
     * @date 2016年4月7日 下午4:39:02
     */
    public static String formatPlainTime(Long date) {
        return format(new Date(date), PLAIN_TIME);
    }

    /**
     * 转换日期成字符串格式 HH:mm:ss
     *
     * @param date
     * @return
     * @throws ParseException
     * @author Douglas Luo
     * @date 2016年4月7日 下午4:39:02
     */
    public static String formatPlainTime(Date date) throws ParseException {
        return format(date, PLAIN_TIME);
    }


    /**
     * 判断两个时间是否在同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
        return isSameDate;
    }

    public static Long getCommonTime(int amount, String time) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, amount);
        String yesterday = new SimpleDateFormat(FORMAT_DATE).format(cal.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIME);
        Date date = null;
        try {
            date = sdf.parse(yesterday + time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    private static final String PLAIN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    private static final String PLAIN_TIME = "HH:mm:ss";

    private static final String[] TIME_PATTERN = new String[]{"HH:mm:ss"};
    private static final String[] DATE_PATTERN = new String[]{"yyyy-MM", "yyyyMM", "yyyy/MM", "yyyyMMdd",
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd HH:mm:ss"};

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static void main(String[] args) {
        System.out.println(longToStringDate(System.currentTimeMillis(), FORMAT_TIMEMILLI));
    }
}
