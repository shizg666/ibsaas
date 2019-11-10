package com.landleaf.ibsaas.common.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Seconds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**日期时间工具类
 * @author dongzhiyong
 *
 */
public class DateUtil {

	/**joda时间转换为java时间
	 * @param jodaTime
	 * @return
	 */
	public static Date toJavaDate(DateTime jodaTime)
	{
		return jodaTime.toDate();
	}
	
	public static Date toJavaDate(String strDate)
	{
		return toJavaDate(toJodaDate(strDate));
	}
	
	/**java时间转换为joda时间
	 * @param javaDate
	 * @return
	 */
	public static DateTime toJodaDate(Date javaDate)
	{
		//转换为joda格式
    	return (new DateTime(javaDate));
	}
	
	/**java时间转换为joda时间
	 * @param strDate
	 * @return
	 */
	public static DateTime toJodaDate(String strDate)
	{
		return( new DateTime(strDate));
	}
	/**格式化joda时间
	 * @param jodaTime
	 * @return
	 */
	public static String formatJodaDate(DateTime jodaTime)
	{
		 return (jodaTime.toString("yyyy-MM-dd HH:mm:ss.SSSS"));    
	}
	
	public static String formatJodaDate2(DateTime jodaTime)
	{
		 return (jodaTime.toString("yyyy-MM-dd_HH-mm-ss"));    
	}

	/**格式化java时间：yyyy-MM-dd HH:mm:ss.SSSS
	 * @param javaDate
	 * @return
	 */
	public static String formatJavaDate(Date javaDate)
	{
		DateTime dt = toJodaDate(javaDate);
		return formatJodaDate(dt);
	}
	
	/**格式化java时间：yyyy-MM-dd hh:mm:ss
	 * @param javaDate
	 * @return
	 */
	public static String formatJavaDate2(Date javaDate)
	{
		java.text.DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//二十四小时制的格式
		return format.format(javaDate);
	}
	/**格式化java时间：yyyy-MM-dd
	 * @param javaDate
	 * @return
	 */
	public static String formatJavaDate3(Date javaDate)
	{
		java.text.DateFormat format = new SimpleDateFormat("yyyy-MM-dd");//二十四小时制的格式
		return format.format(javaDate);
	}
	/**获得系统当前时间(joda格式)
	 * @return
	 */
	public static DateTime getCurrentDate()
	{
		return(new DateTime());
	}
	
	/**获得系统当前时间(java格式)
	 * @return
	 */
	public static Date getCurrentJavaDate()
	{
		return(toJavaDate(new DateTime()));
	}
	
	public static String getFormatCurrentDate()
	{
		return formatJodaDate2(new DateTime());
	}
	
	public static String getFormatCurrentDate1()
	{
		return formatJodaDate(new DateTime());
	}
	
	public static void main(String[] args) {
		
		// 获取当前时间
		Date currentDate = DateUtil.getCurrentJavaDate();
		Date t1;
		int seconds;
		try {
			t1 = strToJavaDate("2017-09-01 12:03:43.2580");
			seconds = getDiffSeconds(t1,currentDate);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//生成过期时间
		Date oldDate = DateUtil.getPreviousZhengDianDatebyMonth(currentDate,13);
		System.out.println(javaDateToStr(oldDate));
		
		Date oldDate1 = DateUtil.getPreviousZhengDianDatebyDay(currentDate,3);
		System.out.println(javaDateToStr(oldDate1));
		
		Date d = toZhengDianHourTime(new Date());
		System.out.println(javaDateToStr(d));
		
		DateTime dt1 = new DateTime(
				  2015, //year
				  9,    // month
				  9,    // day
				  12,    // hour (midnight is zero)
				  1,    // minute
				  0,    // second
				  0     // milliseconds
				);
		
		DateTime dt2 = new DateTime(
				  2015, //year
				  9,    // month
				  9,    // day
				  16,    // hour (midnight is zero)
				  1,    // minute
				  0,    // second
				  0     // milliseconds
				);
		ArrayList<Date> list = getAllZhengDianHour(dt2.toDate(),dt1.toDate());
		
	}
	
	
	/**将指定时间转换为整点时间（小时）,如2015-09-09 13:33:00 转换为2015-09-09 13:00:00 
	 * @param javaDate1
	 * @return
	 */
	public static Date toZhengDianHourTime(Date javaDate1)
	{
		DateTime dt = toJodaDate(javaDate1);
		int year = dt.getYear();
		int month = dt.getMonthOfYear();
		int day = dt.getDayOfMonth();
		int hour = dt.getHourOfDay();
		DateTime dt1 = new DateTime(
				  year, //year
				  month,    // month
				  day,    // day
				  hour,    // hour (midnight is zero)
				  0,    // minute
				  0,    // second
				  0     // milliseconds
				);
		//转换为为java日期
		return dt1.toDate();		
	}
	
	/**获得指定日期往前dayNums的时间，比如javaDate=2015-12-25 13:33:00，monthNum=13,则返回值为2014-11-25
	 * @param javaDate
	 * @param dayNums
	 * @return
	 */
	public static Date getPreviousZhengDianDatebyMonth(Date javaDate,int monthNum)
	{
		DateTime dt = toJodaDate(javaDate);
		
		int year = dt.getYear();
		int month = dt.getMonthOfYear();
		int day = dt.getDayOfMonth();	
		
		int year1 = monthNum/12;
		int month1 = monthNum%12;
		
		DateTime dt1 = new DateTime(
				  year-year1, //year
				  month-month1,    // month
				  day,    // day
				  0,    // hour (midnight is zero)
				  0,    // minute
				  0,    // second
				  0     // milliseconds
				);
		//转换为为java日期
				return dt1.toDate();	
	}
	
	/**获得指定日期往前dayNums的时间，比如javaDate=2015-12-25 13:33:00，dayNum=2,则返回值为2014-12-23
	 * @param javaDate
	 * @param dayNums
	 * @return
	 */
	public static Date getPreviousZhengDianDatebyDay(Date javaDate,int dayNum)
	{
		DateTime dt = toJodaDate(javaDate);
		DateTime dt1 = dt.minusDays(dayNum);
		
		//转换为为java日期
		return dt1.toDate();	
	}
	
	/**将指定时间转换为整点时间（天）,如2015-09-09 13:33:00 转换为2015-09-09 00:00:00 
	 * @param javaDate1
	 * @return
	 */
	public static Date toZhengDianDayTime(Date javaDate1)
	{
		DateTime dt = toJodaDate(javaDate1);
		int year = dt.getYear();
		int month = dt.getMonthOfYear();
		int day = dt.getDayOfMonth();		
		DateTime dt1 = new DateTime(
				  year, //year
				  month,    // month
				  day,    // day
				  0,    // hour (midnight is zero)
				  0,    // minute
				  0,    // second
				  0     // milliseconds
				);
		//转换为为java日期
		return dt1.toDate();		
	}
	

	
	/**获得两个时间之间的整点小时时间
	 * @param javaDate1
	 * @param javaDate2
	 * @return
	 */
	public static ArrayList<Date> getAllZhengDianHour(Date javaDate1,Date javaDate2)
	{
		DateTime dt1;
		DateTime dt2;
		ArrayList<Date> list = new ArrayList<Date> ();
		
		if (isBefore(javaDate1,javaDate2))
		{
			dt1 = new DateTime(toZhengDianHourTime(javaDate1));
			dt2 = new DateTime(toZhengDianHourTime(javaDate2));
		}
		else
		{
			dt1 = new DateTime(toZhengDianHourTime(javaDate2));
			dt2 = new DateTime(toZhengDianHourTime(javaDate1));
		}
		
		
		DateTime dest = dt1;
		while (dest.isBefore(dt2)|| dest.isEqual(dt2))
		{
			list.add(dest.toDate());
			dest = dest.plusHours(1);
		}
		return list;
		
	}
	/**判断javaDate1是否早于javaDate2
	 * @param javaDate1
	 * @param javaDate2
	 * @return
	 */
	public static boolean isBefore(Date javaDate1,Date javaDate2)
	{
		DateTime jodaDate1 = DateUtil.toJodaDate(javaDate1);
		DateTime jodaDate2 = DateUtil.toJodaDate(javaDate2);
		if (jodaDate1.isBefore(jodaDate2))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**判断javaDate1是否晚于javaDate2
	 * @param javaDate1
	 * @param javaDate2
	 * @return
	 */
	public static boolean isAfter(Date javaDate1,Date javaDate2)
	{
		DateTime jodaDate1 = DateUtil.toJodaDate(javaDate1);
		DateTime jodaDate2 = DateUtil.toJodaDate(javaDate2);
		if (jodaDate1.isAfter(jodaDate2))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**判断dest是否在javaDate1和javaDate2之间
	 * @param javaDate1
	 * @param javaDate2
	 * @param javaDest
	 * @return
	 */
	public static boolean isBetween(Date javaDate1,Date javaDate2,Date javaDest)
	{
		DateTime jodaDate1 = DateUtil.toJodaDate(javaDate1);
		DateTime jodaDate2 = DateUtil.toJodaDate(javaDate2);
		DateTime jodaDest = DateUtil.toJodaDate(javaDest);
		if((jodaDest.isAfter(jodaDate1) && jodaDest.isBefore(jodaDate2))||
		   (jodaDest.isAfter(jodaDate2) && jodaDest.isBefore(jodaDate1)))
		{
			return true;
		}
		else if (jodaDest.isEqual(jodaDate1) || jodaDest.isEqual(jodaDate2))
		{
			return true;
		}
		else
		{
		    return false;	
		}	
		
	}
	
	/**获得两个时间的差值(单位：秒)
	 * @param javaDateStart()
	 * @param javaDateEnd
	 * @return
	 */
	public static int getDiffSeconds(Date javaDateStart,Date javaDateEnd)
	{
		DateTime jodaDate1 = DateUtil.toJodaDate(javaDateStart);
		DateTime jodaDate2 = DateUtil.toJodaDate(javaDateEnd);
		
		return Seconds.secondsBetween(jodaDate1, jodaDate2).getSeconds();
		
	}
	
	/**获得两个时间的差值(单位：小时)
	 * @param javaDateStart()
	 * @param javaDateEnd
	 * @return
	 */
	public static int getDiffHour(Date javaDateStart,Date javaDateEnd)
	{
		DateTime jodaDate1 = DateUtil.toJodaDate(javaDateStart);
		DateTime jodaDate2 = DateUtil.toJodaDate(javaDateEnd);
		
		return Hours.hoursBetween(jodaDate1, jodaDate2).getHours()+1;//包括起始时间和结束时间
		
	}
	
	/**获得两个时间的差值(单位：小时)
	 * @param javaDateStart()
	 * @param javaDateEnd
	 * @return
	 */
	public static int getDiffDay(Date javaDateStart,Date javaDateEnd)
	{
		DateTime jodaDate1 = DateUtil.toJodaDate(javaDateStart);
		DateTime jodaDate2 = DateUtil.toJodaDate(javaDateEnd);
		
		return Days.daysBetween(jodaDate1, jodaDate2).getDays()+1;//包括起始时间和结束时间
		
	}
	/**
	* 字符串转换成Java日期
	* @param str
	* @return date
	 * @throws ParseException 
	*/
	public static Date strToJavaDate2(String str) {
	  
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
		    date = format.parse(str);
		   } catch (ParseException e) {
		    e.printStackTrace();
		}
		return date;
	}
	
	/**
	* 字符串转换成Java日期
	* @param str
	* @return date
	 * @throws ParseException 
	*/
	public static Date strToJavaDate(String str) throws ParseException {
	  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   DateTime date = toJodaDate(format.parse(str));//1970的时间
	   
	   DateTime now = getCurrentDate();
	  // int years = Years.yearsBetween(date, now).getYears();
	   int days = Days.daysBetween(date, now).getDays();
	   DateTime newDate = date.plusDays(days);
	   
	   return newDate.toDate();
	}

	/**
	* Java日期转换成字符串
	* @param date 
	* @return str
	*/
	public static String javaDateToStr(Date date) {
	  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String str = format.format(date);
	   return str;
	} 

    /**date减去minutes分钟
     * @param date
     * @param minutes
     * @return
     */
    public static Date subMinutes(Date date,int minutes)
    {
    	DateTime jodaDate1 = DateUtil.toJodaDate(date);
    	DateTime jodaDate2 = jodaDate1.minusMinutes(minutes);
    	return jodaDate2.toDate();
    }
    
    // 判断字符串是否为日期格式:yyyy-mm-dd
    /**
     * @param sDate
     * @return
     */
    public static boolean isValidDate(String sDate) {
        String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
        String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
                + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
                + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
                + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        if ((sDate != null)) {
            Pattern pattern = Pattern.compile(datePattern1);
            Matcher match = pattern.matcher(sDate);
            if (match.matches()) {
                pattern = Pattern.compile(datePattern2);
                match = pattern.matcher(sDate);
                return match.matches();
            } else {
                return false;
            }
        }
        return false;
    }
    
    /**判断字符串是否为时间格式
     * @param sDate
     * @return
     */
    public static boolean isValidDateTime(String sDate)
    {
    	if (isValidDateTime_yyyy_mm_dd_hh_mm_ss(sDate))
    	{
    	    return true;	
    	}
    	else if (isValidDateTime_yyyy_mm_dd_hh_mm(sDate))
    	{
    		return true;
    	}
    	else if (isValidDateTime_yyyy_mm_dd_hh(sDate))
    	{
    		return true;
    	}
    	else if (isValidDateTime_yyyy_mm_dd(sDate))
    	{
    		return true;
    	}
    	else if (isValidDateTime_yyyy_mm(sDate))
    	{
    		return true;
    	}
    	else if (isValidDateTime_yyyy(sDate))
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    	
    }
    /**判断字符串是否为时间格式:"yyyy-MM-dd HH:mm:ss
     * @param startTime
     * @param endTime
     * @return
     */
	public static boolean isValidDateTime_yyyy_mm_dd_hh_mm_ss(String sDate) {
		StringBuilder year_mounth_date_hour_minute_second = new StringBuilder();
		// [1-9]{1}[0-9]{3}:匹配年1000-9999
		year_mounth_date_hour_minute_second.append("[1-9]{1}[0-9]{3}").append("-");
		// ([1-9]|[0]{1}[1-9]{1}|[1]{1}[0-2]{1})匹配月:1-12或01-12或
		year_mounth_date_hour_minute_second.append("([1-9]|[0]{1}[1-9]{1}|[1]{1}[0-2]{1})").append("-");
		// ([1-9]|[0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-1]{1})匹配日:1-31或01-31
		year_mounth_date_hour_minute_second.append("([1-9]|[0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-1]{1})").append("\\s*");
		// ([0-9]|[0-1]{1}[0-9]{1}|[2]{1}[0-3]{1}):匹配小时:00-23或0-23
		year_mounth_date_hour_minute_second.append("([0-9]|[0-1]{1}[0-9]{1}|[2]{1}[0-3]{1}):");
		// ([0-9]|[0-5]{1}[0-9]{1}):匹配分钟:00-59或0-59
		year_mounth_date_hour_minute_second.append("([0-9]|[0-5]{1}[0-9]{1}):");
		// ([0-9]|[0-5]{1}[0-9]{1}):匹配秒:00-59或0-59
		year_mounth_date_hour_minute_second.append("([0-9]|[0-5]{1}[0-9]{1})");
		// 匹配的字符串
		String regTime = year_mounth_date_hour_minute_second.toString();

		if (sDate.indexOf("/") != -1) {
			sDate = sDate.replace("/", "-");
		}

		Pattern pattern = Pattern.compile(regTime);
		boolean isValid = pattern.matcher(sDate).matches();
		return isValid;
	}

	/**判断字符串是否为时间格式:"yyyy-MM-dd HH:mm
     * @param startTime
     * @param endTime
     * @return
     */
	public static boolean isValidDateTime_yyyy_mm_dd_hh_mm(String sDate) {
		StringBuilder year_mounth_date_hour_minute_second = new StringBuilder();
		// [1-9]{1}[0-9]{3}:匹配年1000-9999
		year_mounth_date_hour_minute_second.append("[1-9]{1}[0-9]{3}").append("-");
		// ([1-9]|[0]{1}[1-9]{1}|[1]{1}[0-2]{1})匹配月:1-12或01-12或
		year_mounth_date_hour_minute_second.append("([1-9]|[0]{1}[1-9]{1}|[1]{1}[0-2]{1})").append("-");
		// ([1-9]|[0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-1]{1})匹配日:1-31或01-31
		year_mounth_date_hour_minute_second.append("([1-9]|[0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-1]{1})").append("\\s*");
		// ([0-9]|[0-1]{1}[0-9]{1}|[2]{1}[0-3]{1}):匹配小时:00-23或0-23
		year_mounth_date_hour_minute_second.append("([0-9]|[0-1]{1}[0-9]{1}|[2]{1}[0-3]{1}):");
		// ([0-9]|[0-5]{1}[0-9]{1}):匹配分钟:00-59或0-59
		year_mounth_date_hour_minute_second.append("([0-9]|[0-5]{1}[0-9]{1})");
		
		// 匹配的字符串
		String regTime = year_mounth_date_hour_minute_second.toString();

		if (sDate.indexOf("/") != -1) {
			sDate = sDate.replace("/", "-");
		}

		Pattern pattern = Pattern.compile(regTime);
		boolean isValid = pattern.matcher(sDate).matches();
		return isValid;
	}
	
	/**判断字符串是否为时间格式:"yyyy-MM-dd HH
     * @param startTime
     * @param endTime
     * @return
     */
	public static boolean isValidDateTime_yyyy_mm_dd_hh(String sDate) {
		StringBuilder year_mounth_date_hour_minute_second = new StringBuilder();
		// [1-9]{1}[0-9]{3}:匹配年1000-9999
		year_mounth_date_hour_minute_second.append("[1-9]{1}[0-9]{3}").append("-");
		// ([1-9]|[0]{1}[1-9]{1}|[1]{1}[0-2]{1})匹配月:1-12或01-12或
		year_mounth_date_hour_minute_second.append("([1-9]|[0]{1}[1-9]{1}|[1]{1}[0-2]{1})").append("-");
		// ([1-9]|[0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-1]{1})匹配日:1-31或01-31
		year_mounth_date_hour_minute_second.append("([1-9]|[0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-1]{1})").append("\\s*");
		// ([0-9]|[0-1]{1}[0-9]{1}|[2]{1}[0-3]{1}):匹配小时:00-23或0-23
		year_mounth_date_hour_minute_second.append("([0-9]|[0-1]{1}[0-9]{1}|[2]{1}[0-3]{1})");
		
		
		// 匹配的字符串
		String regTime = year_mounth_date_hour_minute_second.toString();

		if (sDate.indexOf("/") != -1) {
			sDate = sDate.replace("/", "-");
		}

		Pattern pattern = Pattern.compile(regTime);
		boolean isValid = pattern.matcher(sDate).matches();
		return isValid;
	}
	
	/**判断字符串是否为时间格式:"yyyy-MM-dd 
     * @param startTime
     * @param endTime
     * @return
     */
	public static boolean isValidDateTime_yyyy_mm_dd(String sDate) {
		StringBuilder year_mounth_date_hour_minute_second = new StringBuilder();
		// [1-9]{1}[0-9]{3}:匹配年1000-9999
		year_mounth_date_hour_minute_second.append("[1-9]{1}[0-9]{3}").append("-");
		// ([1-9]|[0]{1}[1-9]{1}|[1]{1}[0-2]{1})匹配月:1-12或01-12或
		year_mounth_date_hour_minute_second.append("([1-9]|[0]{1}[1-9]{1}|[1]{1}[0-2]{1})").append("-");
		// ([1-9]|[0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-1]{1})匹配日:1-31或01-31
		year_mounth_date_hour_minute_second.append("([1-9]|[0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-1]{1})").append("\\s*");
				
		// 匹配的字符串
		String regTime = year_mounth_date_hour_minute_second.toString();

		if (sDate.indexOf("/") != -1) {
			sDate = sDate.replace("/", "-");
		}

		Pattern pattern = Pattern.compile(regTime);
		boolean isValid = pattern.matcher(sDate).matches();
		return isValid;
	}
	
	/**判断字符串是否为时间格式:"yyyy-MM
     * @param startTime
     * @param endTime
     * @return
     */
	public static boolean isValidDateTime_yyyy_mm(String sDate) {
		StringBuilder year_mounth_date_hour_minute_second = new StringBuilder();
		// [1-9]{1}[0-9]{3}:匹配年1000-9999
		year_mounth_date_hour_minute_second.append("[1-9]{1}[0-9]{3}").append("-");
		// ([1-9]|[0]{1}[1-9]{1}|[1]{1}[0-2]{1})匹配月:1-12或01-12或
		year_mounth_date_hour_minute_second.append("([1-9]|[0]{1}[1-9]{1}|[1]{1}[0-2]{1})");
						
		// 匹配的字符串
		String regTime = year_mounth_date_hour_minute_second.toString();

		if (sDate.indexOf("/") != -1) {
			sDate = sDate.replace("/", "-");
		}

		Pattern pattern = Pattern.compile(regTime);
		boolean isValid = pattern.matcher(sDate).matches();
		return isValid;
	}
	
	/**判断字符串是否为时间格式:"yyyy
     * @param startTime
     * @param endTime
     * @return
     */
	public static boolean isValidDateTime_yyyy(String sDate) {
		StringBuilder year_mounth_date_hour_minute_second = new StringBuilder();
		// [1-9]{1}[0-9]{3}:匹配年1000-9999
		year_mounth_date_hour_minute_second.append("[1-9]{1}[0-9]{3}");
								
		// 匹配的字符串
		String regTime = year_mounth_date_hour_minute_second.toString();

		if (sDate.indexOf("/") != -1) {
			sDate = sDate.replace("/", "-");
		}

		Pattern pattern = Pattern.compile(regTime);
		boolean isValid = pattern.matcher(sDate).matches();
		return isValid;
	}
}
