package com.happyhouse.vue.model.service;

import java.util.List;
import com.happyhouse.vue.model.NoticeDto;

public interface NoticeService {

    List<NoticeDto> listNotice(String key) throws Exception;

    boolean registerNotice(NoticeDto notice) throws Exception;

    NoticeDto detailNotice(NoticeDto noticeDto) throws Exception;

    boolean updateNotice(NoticeDto noticeDto) throws Exception;

    boolean removeNotice(NoticeDto noticeDto) throws Exception;

}
