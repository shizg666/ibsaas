package com.landleaf.ibsaas.client.meeting.service;

import com.alibaba.fastjson.JSONObject;
import com.landleaf.ibsaas.client.meeting.config.RedisHandle;
import com.landleaf.ibsaas.client.meeting.model.vo.LgcMeeting;
import com.landleaf.ibsaas.client.meeting.util.MeetingDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/12/17 16:46
 * @description:
 */
@Service
public class MeetingClientService {

    @Autowired
    private RedisHandle redisHandle;

    private static final String LGC_SCREEN_MEETING = "lgc_screen_meeting";





    public void todayMeeting2Redis(){
        LocalDateTime now = LocalDateTime.now();
        List<LgcMeeting> allLgcMeeting = getAllLgcMeeting();
        String today = MeetingDateUtil.ldt2Str(now, "yyyy-MM-dd");
        redisHandle.addMap(LGC_SCREEN_MEETING, today, JSONObject.toJSONString(allLgcMeeting));
    }


    /**
     * 获取所有的会议列表
     * @return
     */
    public List<LgcMeeting> getAllLgcMeeting(){
        List<LgcMeeting> meetings = new ArrayList<>();
        meetings.add(defaultLgcMeeting());
        return meetings;
    }



    private LgcMeeting defaultLgcMeeting(){
        LgcMeeting lgcMeeting = new LgcMeeting();
        lgcMeeting.setMeetingTime(new Date());
        lgcMeeting.setMeetingRoom("SH 2F会议室四姑娘二峰");
        lgcMeeting.setContent("Ibsaas项目技术研讨会议");
        return lgcMeeting;
    }
}
