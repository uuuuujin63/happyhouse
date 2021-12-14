package com.happyhouse.vue.model.service;

import java.util.List;
import com.happyhouse.vue.model.QnADto;

public interface QnAService {
    
    public List<QnADto> selectQnAList();

    public QnADto selectQnA(int q_no);

    public List<QnADto> selectQnAById(String iduser);

    public boolean insertQuestion(QnADto qnaDto);

    public boolean updateQnA(QnADto qnaDto);

    public boolean deleteQnA(int q_no);
    
}
