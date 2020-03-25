package com.landleaf.ibsaas.screen.util;

import java.math.BigDecimal;

/**
 * @author Lokiy
 * @date 2019/12/24 14:22
 * @description: 大屏数据规整
 */
public class ScreenValueUtil {


    /**
     * 保留digit位小数
     * @param oriValue
     * @param digit
     * @return
     */
    public static String retainDecimals(String oriValue, int digit){
        BigDecimal temp = new BigDecimal(oriValue);
        return temp.setScale(digit, BigDecimal.ROUND_HALF_UP).toString();
    }
}
