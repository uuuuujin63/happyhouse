package com.happyhouse.vue.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.happyhouse.vue.model.CommunityDto;
import com.happyhouse.vue.model.CommunityReplyDto;
import com.happyhouse.vue.model.service.CommunityService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/community")
public class CommunityController {
	private static final Logger logger = LoggerFactory.getLogger(CommunityController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private CommunityService communityService;

	@ApiOperation(value = "모든 커뮤니티글을 불러옴", response = List.class)
	@GetMapping
	public ResponseEntity<List<CommunityDto>> communityList(@RequestParam String addr) throws Exception {
		logger.debug("communityList - 호출");
		return new ResponseEntity<List<CommunityDto>>(communityService.listCommunity(null, addr), HttpStatus.OK);
	}
	
	@ApiOperation(value = "검색어로 제목을 검색하여 커뮤니티글 목록 반환", response = List.class)
    @GetMapping("/search")
    public ResponseEntity<List<CommunityDto>> searchByTitle(
    		@RequestParam String title, @RequestParam String addr) throws Exception {
        logger.debug("searchByTitle - 호출");
        return new ResponseEntity<List<CommunityDto>>(communityService.listCommunity(title, addr), HttpStatus.OK);
    }

    @ApiOperation(value = "글 번호에 해당하는 커뮤니티글 반환", response = CommunityDto.class)
    @GetMapping("{idx}")
    public ResponseEntity<CommunityDto> getCommunity(
            @PathVariable @ApiParam(value = "글 번호", required = true) String idx) throws Exception {
        logger.debug("getCommunity - 호출");
        return new ResponseEntity<CommunityDto>(communityService.getCommunity(idx), HttpStatus.OK);
    }
//, HttpSession session
    @ApiOperation(value = "글 등록. 성공 여부에 따라 'success' 또는 'fail' 문자열 반환", response = String.class)
    @PostMapping
    public ResponseEntity<String> registerCommunity(
            @RequestBody @ApiParam(value = "등록할 글 정보", required = true) CommunityDto communityDto) throws Exception {
        logger.debug("registerCommunity - 호출");
        //UserDto user = (UserDto) session.getAttribute("userinfo");
        //String id = user.getUserid();
        //noticeDto.setUserid(id);
        if (communityService.registerCommunity(communityDto)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.OK);
    }

    @ApiOperation(value = "글 수정. 성공 여부에 따라 'success' 또는 'fail' 문자열 반환", response = String.class)
    @PutMapping
    public ResponseEntity<String> updateCommunity(
            @RequestBody @ApiParam(value = "업데이트 할 글 정보", required = true) CommunityDto communityDto) throws Exception {
        logger.debug("updateCommunity - 호출");
        if (communityService.updateCommunity(communityDto)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.OK);
    }

    @ApiOperation(value = "글 삭제. 성공 여부에 따라 'success' 또는 'fail' 문자열 반환", response = String.class)
    @DeleteMapping("{idx}")
    public ResponseEntity<String> removeCommunity(
            @PathVariable @ApiParam(value = "삭제할 글 번호", required = true) String idx) throws Exception {
        logger.debug("updateNotice - 호출");
        CommunityDto communityDto = new CommunityDto(idx);
        if (communityService.removeCommunity(communityDto)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.OK);
    } //getDongName
    
    @ApiOperation(value = "동코드로 동네임 검색", response = List.class)
    @GetMapping("/dongsearch/{dongCode}")
    public ResponseEntity<String> getDongName(@PathVariable("dongCode") String dongCode) throws Exception {
        logger.debug("getDongName - 호출");
        System.out.println("동코드는 : "+dongCode);
        System.out.println(communityService);
        String dongName = communityService.getDongName(dongCode);
        System.out.println("동이름은 : "+dongName);
        return new ResponseEntity<String>(dongName, HttpStatus.OK);
    }
    
    @ApiOperation(value = "댓글 등록. 성공 여부에 따라 'success' 또는 'fail' 문자열 반환", response = String.class)
    @PostMapping("/reply")
    public ResponseEntity<String> registerReply(
            @RequestBody @ApiParam(value = "등록할 글 정보", required = true) CommunityReplyDto communityReplyDto) throws Exception {
        logger.debug("CommunityReplyDto - 호출");
        //UserDto user = (UserDto) session.getAttribute("userinfo");
        //String id = user.getUserid();
        //noticeDto.setUserid(id);
        if (communityService.registerReply(communityReplyDto)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.OK);
    }
    
    @ApiOperation(value = "댓글 삭제. 성공 여부에 따라 'success' 또는 'fail' 문자열 반환", response = String.class)
    @DeleteMapping("/reply/{no}")
    public ResponseEntity<String> removeReply(
            @PathVariable @ApiParam(value = "삭제할 글 번호", required = true) int no) throws Exception {
        logger.debug("removeReply - 호출");
        if (communityService.removeReply(no)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.OK);
    }
}
