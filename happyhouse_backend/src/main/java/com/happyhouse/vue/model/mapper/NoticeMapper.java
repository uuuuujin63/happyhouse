package com.happyhouse.vue.model.mapper;

import java.util.List;
import com.happyhouse.vue.model.NoticeDto;

public interface NoticeMapper {

    List<NoticeDto> listNotice(String key) throws Exception;

    int registerNotice(NoticeDto notice) throws Exception;

    NoticeDto detailNotice(NoticeDto noticeDto) throws Exception;

    int updateNotice(NoticeDto noticeDto) throws Exception;

    int removeNotice(NoticeDto noticeDto) throws Exception;

}
