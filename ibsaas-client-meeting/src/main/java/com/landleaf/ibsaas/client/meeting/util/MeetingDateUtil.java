package com.landleaf.ibsaas.client.meeting.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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


    /**
     * 会议开始时间 MM-dd HH:mm
     * @param start
     * @return
     */
    public static String meetingStartTime(Date start){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd HH:mm");
        Instant instant = start.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return df.format(localDateTime);
    }

    /**
     * 会议结束时间 HH:mm
     * @param end
     * @return
     */
    public static String meetingEndTime(Date end){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
        Instant instant = end.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return df.format(localDateTime);
    }
}
