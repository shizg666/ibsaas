package com.landleaf.ibsaas.client.meeting.service;

import com.alibaba.fastjson.JSONObject;
import com.landleaf.ibsaas.client.meeting.config.RedisHandle;
import com.landleaf.ibsaas.client.meeting.dal.dao.meeting.custom.BookBooklistMapperCustom;
import com.landleaf.ibsaas.client.meeting.model.vo.BookListView;
import com.landleaf.ibsaas.client.meeting.model.vo.LgcMeeting;
import com.landleaf.ibsaas.client.meeting.util.MeetingDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lokiy
 * @date 2019/12/17 16:46
 * @description:
 */
@Service
public class MeetingClientService {

    @Autowired
    private RedisHandle redisHandle;

    @Autowired
    private BookBooklistMapperCustom bookBooklistMapperCustom;

    private static final String LGC_SCREEN_MEETING = "lgc_screen_meeting";


    /**
     * 当天数据刷入redis
     */
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
        List<BookListView> todayBookList = bookBooklistMapperCustom.getTodayBookList();
        return todayBookList.stream()
                .map(this::bookListView2LgcMeeting)
                .collect(Collectors.toList());
    }

    /**
     * bookListView转LgcMeeting
     * @param bookListView
     * @return
     */
    public LgcMeeting bookListView2LgcMeeting(BookListView bookListView){
        LgcMeeting temp = new LgcMeeting();

        String time = MeetingDateUtil.meetingStartTime(bookListView.getBeginTime())
                + "~" + MeetingDateUtil.meetingEndTime(bookListView.getEndTime());
        temp.setMeetingTime(time);
        temp.setMeetingRoom(bookListView.getRoomName());
        temp.setContent(bookListView.getSubject());
        return temp;
    }







    private LgcMeeting defaultLgcMeeting(){
        LgcMeeting lgcMeeting = new LgcMeeting();
        lgcMeeting.setMeetingTime("10-01 13:00~18:00");
        lgcMeeting.setMeetingRoom("SH 2F会议室四姑娘二峰");
        lgcMeeting.setContent("Ibsaas项目技术研讨会议");
        return lgcMeeting;
    }
}
