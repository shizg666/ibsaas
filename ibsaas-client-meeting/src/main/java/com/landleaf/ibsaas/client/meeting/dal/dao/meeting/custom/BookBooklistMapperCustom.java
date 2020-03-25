package com.landleaf.ibsaas.client.meeting.dal.dao.meeting.custom;


import com.landleaf.ibsaas.client.meeting.dal.dao.meeting.mbg.BookBooklistMapper;
import com.landleaf.ibsaas.client.meeting.model.vo.BookListView;

import java.util.List;

public interface BookBooklistMapperCustom extends BookBooklistMapper {


    /**
     * 获取当天的会议预定信息
     * @return
     */
    List<BookListView> getTodayBookList();
}