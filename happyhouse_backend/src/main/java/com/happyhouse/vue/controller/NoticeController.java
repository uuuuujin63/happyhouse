package com.happyhouse.vue.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
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
import com.happyhouse.vue.model.NoticeDto;
import com.happyhouse.vue.model.UserDto;
import com.happyhouse.vue.model.service.NoticeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    private static final Logger logger = LoggerFactory.getLogger(QnARestController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private NoticeService noticeService;

    @ApiOperation(value = "모든 공지글을 불러옴", response = List.class)
    @GetMapping
    public ResponseEntity<List<NoticeDto>> noticeList() throws Exception {
        logger.debug("noticeList - 호출");
        return new ResponseEntity<List<NoticeDto>>(noticeService.listNotice(null), HttpStatus.OK);
    }

    @ApiOperation(value = "검색어로 제목을 검색하여 공지 글 목록 반환", response = List.class)
    @GetMapping("/search/{title}")
    public ResponseEntity<List<NoticeDto>> searchByTitle(
            @PathVariable @ApiParam(value = "제목으로 검색할 검색어", required = true) String title) throws Exception {
        logger.debug("searchByTitle - 호출");
        return new ResponseEntity<List<NoticeDto>>(noticeService.listNotice(title), HttpStatus.OK);
    }

    @ApiOperation(value = "글 번호에 해당하는 공지 글 반환", response = NoticeDto.class)
    @GetMapping("{idx}")
    public ResponseEntity<NoticeDto> getNotice(
            @PathVariable @ApiParam(value = "글 번호", required = true) String idx) throws Exception {
        logger.debug("getNotice - 호출");
        NoticeDto noticeDto = new NoticeDto(idx);
        return new ResponseEntity<NoticeDto>(noticeService.detailNotice(noticeDto), HttpStatus.OK);
    }
//, HttpSession session
    @ApiOperation(value = "글 등록. 성공 여부에 따라 'success' 또는 'fail' 문자열 반환", response = String.class)
    @PostMapping
    public ResponseEntity<String> registerNotice(
            @RequestBody @ApiParam(value = "등록할 글 정보", required = true) NoticeDto noticeDto) throws Exception {
        logger.debug("registerNotice - 호출");
        //UserDto user = (UserDto) session.getAttribute("userinfo");
        //String id = user.getUserid();
        //noticeDto.setUserid(id);
        if (noticeService.registerNotice(noticeDto)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.OK);
    }

    @ApiOperation(value = "글 수정. 성공 여부에 따라 'success' 또는 'fail' 문자열 반환", response = String.class)
    @PutMapping
    public ResponseEntity<String> updateNotice(
            @RequestBody @ApiParam(value = "업데이트 할 글 정보", required = true) NoticeDto noticeDto) throws Exception {
        logger.debug("updateNotice - 호출");
        if (noticeService.updateNotice(noticeDto)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.OK);
    }

    @ApiOperation(value = "글 삭제. 성공 여부에 따라 'success' 또는 'fail' 문자열 반환", response = String.class)
    @DeleteMapping("{idx}")
    public ResponseEntity<String> removeNotice(
            @PathVariable @ApiParam(value = "삭제할 글 번호", required = true) String idx) throws Exception {
        logger.debug("updateNotice - 호출");
        NoticeDto noticeDto = new NoticeDto(idx);
        if (noticeService.removeNotice(noticeDto)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.OK);
    }

}
