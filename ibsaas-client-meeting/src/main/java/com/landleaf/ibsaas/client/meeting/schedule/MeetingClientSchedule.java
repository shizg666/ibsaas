package com.landleaf.ibsaas.client.meeting.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Lokiy
 * @date 2019/12/17 16:27
 * @description:
 */
@Component
@EnableScheduling
@Slf4j
public class MeetingClientSchedule {



    @Scheduled(cron = "0 0/5 * * * *")
    public void meetingList2Redis(){

        log.info(">>>>>>>>>>>>>>>>>>>>每5分钟会议室数据刷入redis<<<<<<<<<<<<<<<<<<<<");
    }
}
