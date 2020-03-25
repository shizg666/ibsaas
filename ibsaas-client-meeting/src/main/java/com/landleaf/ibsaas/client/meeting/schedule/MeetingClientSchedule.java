package com.landleaf.ibsaas.client.meeting.schedule;

import com.landleaf.ibsaas.client.meeting.service.MeetingClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MeetingClientService meetingClientService;

    @Scheduled(cron = "0 0/5 * * * *")
    public void meetingList2Redis(){
        meetingClientService.todayMeeting2Redis();
        log.info(">>>>>>>>>>>>>>>>>>>>每5分钟会议室数据刷入redis<<<<<<<<<<<<<<<<<<<<");
    }
}
