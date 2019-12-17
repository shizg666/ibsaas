package com.landleaf.ibsaas.client.meeting.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Lokiy
 * @date 2019/12/17 17:03
 * @description:
 */
public class MeetingDateUtil {


    public static String ldt2Str(LocalDateTime localDateTime, String pattern){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(localDateTime);
    }
}
