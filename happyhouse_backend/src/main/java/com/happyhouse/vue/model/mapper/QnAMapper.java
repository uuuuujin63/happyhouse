package com.happyhouse.vue.model.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.happyhouse.vue.model.QnADto;

@Mapper
public interface QnAMapper {
    
    public List<QnADto> selectQnAList();
    // QnA 목록을 뿌리기 위해 전체 리스트를 가져오기

    public QnADto selectQnA(int q_no);
    // q_id로 QnA 정보 가져오기

    public List<QnADto> selectQnAById(String iduser);
    // userid로 QnA 목록 가져오기

    public int insertQuestion(QnADto qnaDto);
    // 질문 등록

    public int updateQnA(QnADto qnaDto);
    // 질문 등록/수정, 답변 수정

    public int deleteQnA(int q_no);
    // QnA 삭제

}
