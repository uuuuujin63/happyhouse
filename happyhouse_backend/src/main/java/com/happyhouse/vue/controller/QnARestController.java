package com.happyhouse.vue.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.happyhouse.vue.model.QnADto;
import com.happyhouse.vue.model.service.QnAService;
import io.swagger.annotations.ApiOperation;

// CrossOrigin : CORS설정
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/qna")
public class QnARestController {
    private static final Logger logger = LoggerFactory.getLogger(QnARestController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private QnAService qnaService;

    @ApiOperation(value = "모든 QnA 정보 반환, response로 받았으면 response.data 안에 있음!!", response = List.class)
    @GetMapping
    public ResponseEntity<List<QnADto>> selectQnAList() throws Exception {
        logger.debug("selectQnAList - 호출");
        return new ResponseEntity<List<QnADto>>(qnaService.selectQnAList(), HttpStatus.OK);
    }

    @ApiOperation(value = "글번호에 해당하는 QnA 정보 반환", response = QnADto.class)
    @GetMapping("/search/no/{q_id}")
    public ResponseEntity<QnADto> selectQnA(@PathVariable("q_id") int q_id) {
        logger.debug("selectQnA - 호출");
        return new ResponseEntity<QnADto>(qnaService.selectQnA(q_id), HttpStatus.OK);
    }

    @ApiOperation(value = "유저아이디에 해당하는 QnA 정보 반환", response = QnADto.class)
    @GetMapping("/search/id/{iduser}")
    public ResponseEntity<List<QnADto>> selectQnAById(@PathVariable("iduser") String iduser) {
        logger.debug("selectQnAById - 호출");
        return new ResponseEntity<List<QnADto>>(qnaService.selectQnAById(iduser), HttpStatus.OK);
    }

    @ApiOperation(value = "새로운 질문 입력, DB 입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환", response = String.class)
    @PostMapping
    public ResponseEntity<String> insertQuestion(@RequestBody QnADto qnaDto) {
        logger.debug("insertQuestion - 호출");
        if (qnaService.insertQuestion(qnaDto)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "질문 수정, 질문에 대한 답변 입력, 답변 수정.  DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PutMapping("{q_no}")
    public ResponseEntity<String> updateQnA(@RequestBody QnADto qnaDto) {
        logger.debug("updateQnA - 호출");
        if (qnaService.updateQnA(qnaDto)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "질문 삭제.  DB 삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @DeleteMapping("{q_no}")
    public ResponseEntity<String> deleteQnA(@PathVariable int q_no) {
        logger.debug("deleteQnA - 호출");
        if (qnaService.deleteQnA(q_no)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }
}
