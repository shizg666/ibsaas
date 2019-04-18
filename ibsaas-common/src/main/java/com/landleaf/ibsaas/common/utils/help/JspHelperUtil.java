package com.landleaf.ibsaas.common.utils.help;

import com.landleaf.ibsaas.common.utils.calculate.CalculateUtil;
import com.landleaf.ibsaas.common.utils.string.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 枚举转化
 *
 * @author Tony
 * @date 2015年11月19日下午4 :32:28
 */
public class JspHelperUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JspHelperUtil.class);
    /**
     * The constant ENUM_MAP.
     */
    public static Map<String, Map> ENUM_MAP = new HashMap<>();

    /**
     * 根据枚举类名和type值，获取枚举name
     *
     * @param enumname 枚举类名(大小写敏感)
     * @param type     枚举type
     * @return 枚举name
     * @author Tony
     * @date 2015年11月30日下午3 :24:49
     */
    public static String getText(String enumname, Integer type) {
        String path = "com.ksudi.skynet.dto.enums." + enumname;
        Map<Integer, String> enums = ENUM_MAP.get(enumname);
        if (MapUtils.isEmpty(enums)) {
            enums = new HashMap<>();
            Class<Object> clazz;
            try {
                clazz = (Class<Object>) Class.forName(path);
                Object[] items = clazz.getEnumConstants();
                for (Object item : items) {
                    Field field = item.getClass().getField("type");
                    int i = field.getInt(item);
                    Field field2 = item.getClass().getField("name");
                    String value = (String) field2.get(item);
                    enums.put(i, value);
                }
                ENUM_MAP.put(enumname, enums);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        String str = enums.get(type);
        return (null == str ? "" : str);
    }

    /**
     * 根据枚举类名和type值，获取枚举name
     *
     * @param path
     * @param type
     * @return text
     * @author Tony
     * @date 2017年05月23日 19:49
     */
    public static String getEnum(String path, Integer type) {
        String enumname = path.substring(path.lastIndexOf("."), path.length());
        Map<Integer, String> enums = ENUM_MAP.get(enumname);
        if (MapUtils.isEmpty(enums)) {
            enums = new HashMap<>();
            Class<Object> clazz;
            try {
                clazz = (Class<Object>) Class.forName(path);
                Object[] items = clazz.getEnumConstants();
                for (Object item : items) {
                    Field field = item.getClass().getField("type");
                    int i = field.getInt(item);
                    Field field2 = item.getClass().getField("name");
                    String value = (String) field2.get(item);
                    enums.put(i, value);
                }
                ENUM_MAP.put(enumname, enums);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        String str = enums.get(type);
        return (null == str ? "" : str);
    }
    
    /**
     * 根据枚举类名和type值，获取枚举下对应属性的值
     * @param path
     * @param type
     * @param fieldName
     * @return text
     * @author Peter
     * @date 2017年10月27日
     */
    public static String getEnumField(String path, Integer type, String fieldName) {
    	if(StringUtil.isEmpty(fieldName)){
    		return getEnum(path, type);
    	}
        String enumname = path.substring(path.lastIndexOf("."), path.length());
        Map<Integer, String> enums = new HashMap<Integer, String>();
        enums = new HashMap<>();
        Class<Object> clazz;
        try {
            clazz = (Class<Object>) Class.forName(path);
            Object[] items = clazz.getEnumConstants();
            for (Object item : items) {
                Field field = item.getClass().getField("type");
                int i = field.getInt(item);
                Field field2 = item.getClass().getField(fieldName);
                String value = (String) field2.get(item);
                enums.put(i, value);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        String str = enums.get(type);
        return (null == str ? "" : str);
    }

    /**
     * The entry point of application.
     *
     * @param args
     * @author Tony
     * @date 2017年05月23日 19:49
     */
    public static void main(String[] args) {
        System.out.println(getSignedMoney(2100, 501));
        System.out.println(getWeight(2100));
        System.out.println(getText("WorkTypeEnum", 600));
        System.out.println(getText("WorkTypeEnum", 500));
    }

    /**
     * 将13位时间戳转换为yyyy-MM-dd格式的字符串日期
     *
     * @param time 13位时间戳
     * @return yyyy -MM-dd格式的字符串日期
     * @author Tony
     * @date 2015年11月20日下午1 :26:19
     */
    public static String getDate(Long time) {
        if (time == null || 0 == time) {
            return "";
        }
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * 将13位时间戳转换为yyyy-MM-dd HH:mm:ss格式的字符串日期时间
     *
     * @param time 13位时间戳
     * @return yyyy -MM-dd HH:mm:ss格式的字符串日期时间
     * @author Tony
     * @date 2015年11月20日下午1 :26:19
     */
    public static String getDatetime(Long time) {
        if (null == time || 0 == time) {
            return "";
        }
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * 将单位是分的金额，根据交易类型，转化为带符号单位是元的金额
     *
     * @param money    单位是分的金额
     * @param worktype 交易类型
     * @return string 带符号单位是元的金额
     * @author Tony
     * @date 2016年04月22日 11:13
     */
    public static String getSignedMoney(Integer money, Integer worktype) {
        if (money == null) {
            return "";
        }
        if (worktype == null) {
            return "";
        }
        String formatMoney = CalculateUtil.divide(money, 100, 2).toString();
        if (PositiveOrNegativeUtil.isPositive(worktype)) {
            return formatMoney;
        }
        return "-" + formatMoney;
    }

    /**
     * 根据交易类型获取收支类型
     *
     * @param worktype 交易类型
     * @return string 收支类型（收入、支出）
     * @author Tony
     * @date 2016年04月22日 11:13
     */
    public static String getInOutType(Integer worktype) {
        if (worktype == null) {
            return "";
        }
        if (PositiveOrNegativeUtil.isPositive(worktype)) {
            return "收入";
        }
        return "支出";
    }

    /**
     * 将单位是分的金额，转化为单位是元的金额
     *
     * @param money 单位是分的金额
     * @return string 单位是元的金额
     * @author Tony
     * @date 2016年04月22日 11:13
     */
    public static String getMoney(Integer money) {
        if (money == null) {
            return "";
        }
        return CalculateUtil.divide(money, 100, 2).toString();
    }

    /**
     * 将单位是克的重量，转化为单位是千克的重量
     *
     * @param weight 单位是克的重量
     * @return string 单位是千克的重量
     * @author Tony
     * @date 2016年04月22日 11:13
     */
    public static String getWeight(Integer weight) {
        if (weight == null) {
            return "";
        }
        return CalculateUtil.divide(weight, 1000, 2).toString();
    }

    /**
     * 将单位是m，转化为单位是km
     *
     * @param distance 单位是克的重量
     * @return string 单位是千克的重量
     * @author Tony
     * @date 2017年03月24日 11:13
     */
    public static String getDistance(Integer distance) {
        if (distance == null) {
            return "";
        }
        return CalculateUtil.divide(distance, 1000, 1).toString();
    }

    /**
     * 将list转化为用逗号分隔的字符串
     *
     * @param list
     * @return string 逗号分隔的字符串
     * @author Tony
     * @date 2016年04月22日 11:13
     */
    public static String list2string(List<Object> list) {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isNotEmpty(list)) {
            for (Object o : list) {
                sb.append(o.toString()).append(",");
            }
        }
        String str = sb.toString();
        int len = str.length();
        if (len > 0) {
            str = str.substring(0, len - 1);
        }
        return str;
    }

    /**
     * 将13位时间戳转换为指定格式的日期
     *
     * @param time   13位时间戳
     * @param fromat 时间格式
     * @return 指定格式的日期 date
     * @author Tony
     * @date 2017年05月23日 19:49
     */
    public static String getFormatDate(Long time, String fromat) {
        if (0 == time) {
            return "";
        }
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(fromat);
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * 将13位时间戳转换为指定格式的日期
     *
     * @param method    方法（+、-、*、/）
     * @param value1    数值1
     * @param value2    数值2
     * @param precision 精度
     * @return 指定格式的日期
     * @author Tony
     * @date 2017年05月23日 19:49
     */
    public static String calculate(String method, String value1, String value2, int precision) {
        BigDecimal bigDecimal1 = new BigDecimal(value1);
        BigDecimal bigDecimal2 = new BigDecimal(value2);
        BigDecimal result;
        String method_ = method.trim();
        if ("+".equals(method_)) {
            result = bigDecimal1.add(bigDecimal2);
        } else if ("-".equals(method_)) {
            result = bigDecimal1.subtract(bigDecimal2);
        } else if ("*".equals(method_)) {
            result = bigDecimal1.multiply(bigDecimal2);
        } else {
            if ("0".equals(value2)) {
                result = BigDecimal.ZERO;
            } else {
                result = bigDecimal1.divide(bigDecimal2);
            }
        }
        return result.setScale(precision, BigDecimal.ROUND_HALF_UP).toString();
    }
}
