package com.happyhouse.vue.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.happyhouse.vue.model.QnADto;
import com.happyhouse.vue.model.mapper.QnAMapper;

@Service
public class QnAServiceImpl implements QnAService {

    @Autowired
    private QnAMapper qnaDao;

    @Override
    public List<QnADto> selectQnAList() {
        return qnaDao.selectQnAList();
    }

    @Override
    public List<QnADto> selectQnAById(String iduser) {
        return qnaDao.selectQnAById(iduser);
    }

    @Override
    public boolean insertQuestion(QnADto qnaDto) {
        return qnaDao.insertQuestion(qnaDto) == 1;
    }

    @Override
    public boolean deleteQnA(int q_no) {
        return qnaDao.deleteQnA(q_no) == 1;
    }

    @Override
    public boolean updateQnA(QnADto qnaDto) {
        return qnaDao.updateQnA(qnaDto) == 1;
    }

    @Override
    public QnADto selectQnA(int q_no) {
        return qnaDao.selectQnA(q_no);
    }

}
