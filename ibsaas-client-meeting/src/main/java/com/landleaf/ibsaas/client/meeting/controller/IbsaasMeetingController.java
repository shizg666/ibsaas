package com.landleaf.ibsaas.client.meeting.controller;

import com.landleaf.ibsaas.client.meeting.service.MeetingClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lokiy
 * @date 2019/12/17 15:19
 * @description:
 */
@RestController
@RequestMapping("/ibsaas-client/meeting")
public class IbsaasMeetingController {

    @Autowired
    private MeetingClientService meetingClientService;

    @GetMapping("/test")
    public String test(){
        return "SUCCESS";
    }


    @GetMapping("/today-meeting-to-redis")
    public String todayMeeting2Redis(){
        meetingClientService.todayMeeting2Redis();
        return "SUCCESS";
    }
}
