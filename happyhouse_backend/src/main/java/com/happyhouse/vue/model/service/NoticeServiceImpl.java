package com.happyhouse.vue.model.service;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.happyhouse.vue.model.NoticeDto;
import com.happyhouse.vue.model.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<NoticeDto> listNotice(String key) throws Exception {
        return sqlSession.getMapper(NoticeMapper.class).listNotice(key);
    }

    @Override
    public boolean registerNotice(NoticeDto notice) throws Exception {
        return sqlSession.getMapper(NoticeMapper.class).registerNotice(notice) == 1;
    }

    @Override
    public NoticeDto detailNotice(NoticeDto noticeDto) throws Exception {
        return sqlSession.getMapper(NoticeMapper.class).detailNotice(noticeDto);
    }

    @Override
    public boolean updateNotice(NoticeDto noticeDto) throws Exception {
        return sqlSession.getMapper(NoticeMapper.class).updateNotice(noticeDto) == 1;
    }

    @Override
    public boolean removeNotice(NoticeDto noticeDto) throws Exception {
        return sqlSession.getMapper(NoticeMapper.class).removeNotice(noticeDto) == 1;
    }

}
