package com.happyhouse.vue.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;
import com.happyhouse.vue.model.FileInfoDto;
import com.happyhouse.vue.model.UserDto;
import com.happyhouse.vue.model.service.JwtServiceImpl;
import com.happyhouse.vue.model.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(QnARestController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "로그인",
            notes = "성공하면 토큰('access-token')을 발급하여 'success' 메세지('message')와 함께 맵으로 반환, 실패하면 'fail' 메세지를 반환",
            response = String.class)
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDto userDto) {
        logger.debug("login - 호출");
        System.out.println(userDto);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            UserDto loginUser = userService.login(userDto);
            if (loginUser != null && loginUser.getUserid() != null) {
                String token = jwtService.create("userid", loginUser.getUserid(), "access-token");// key, data, subject
                logger.debug("로그인 토큰정보 : {}", token);
                resultMap.put("access-token", token);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.ACCEPTED;
            } else {
                resultMap.put("message", FAIL);
                status = HttpStatus.ACCEPTED;
            }
        } catch (Exception e) {
            logger.error("로그인 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "회원 정보 불러옴",
            notes = "토큰이 유효하면 유저 정보('userInfo')를 'success' 메세지('message')와 함께 맵으로 반환, 실패하면 'fail' 메세지를 반환",
            response = UserDto.class)
    @GetMapping("/info/{userid}")
    public ResponseEntity<Map<String, Object>> getUserInfo(
            @PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userid,
            HttpServletRequest request) {
        logger.debug("getUserInfo - 호출");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;

        if (jwtService.isUsable(request.getHeader("access-token"))) {
            logger.info("사용 가능한 토큰!!!");
            try {
                // 로그인 사용자 정보.
                UserDto userDto = userService.mypageUser(userid);
                resultMap.put("userInfo", userDto);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.ACCEPTED;
            } catch (Exception e) {
                logger.error("정보조회 실패 : {}", e);
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            logger.error("사용 불가능 토큰!!!");
            resultMap.put("message", FAIL);
            status = HttpStatus.ACCEPTED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "비밀번호 정보 불러옴",
            notes = "토큰이 유효하면 유저 정보('userInfo')를 'success' 메세지('message')와 함께 맵으로 반환, 실패하면 'fail' 메세지를 반환",
            response = UserDto.class)
    @GetMapping("/find")
    public ResponseEntity<Map<String, Object>> getUserPwd(@RequestParam String userid, @RequestParam String findpwdkey)
            throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        UserDto udo = new UserDto();
        UserDto userDto = new UserDto();
        userDto.setUserid(userid);
        userDto.setFindpwdkey(findpwdkey);
        udo = userService.getUserPwd(userDto);
        if (udo != null) {
            resultMap.put("userInfo", udo);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } else {
            logger.error("정보조회 실패");
            resultMap.put("message", FAIL);
            status = HttpStatus.ACCEPTED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "회원 등록", notes = "성공 여부에 따라 'success' 또는 'fail' 문자열 반환", response = String.class)
    @PostMapping
    public ResponseEntity<String> registerUser(
            @RequestBody @ApiParam(value = "회원 정보 UserDto", required = true) UserDto userDto) throws Exception {
        logger.debug("registerUser - 호출");
        if (userService.registerMember(userDto)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.OK);
    }

    @ApiOperation(value = "회원 수정", notes = "비밀번호가 일치해야 수정할 수 있음. 성공 여부에 따라 'success' 또는 'fail' 문자열 반환",
            response = String.class)
    @PutMapping
    public ResponseEntity<String> updateUser(
            @RequestBody @ApiParam(value = "회원 정보 UserDto", required = true) UserDto userDto) throws Exception {
        logger.debug("updateUser - 호출");
        if (userService.updateUser(userDto)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.OK);
    }

    @ApiOperation(value = "회원 삭제", notes = "성공 여부에 따라 'success' 또는 'fail' 문자열 반환", response = String.class)
    @DeleteMapping("/delete/{userid}")
    public ResponseEntity<String> removeUser(@PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.") String userid)
            throws Exception {
        logger.debug("removeUser - 호출");
        if (userService.removeUser(userid)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.OK);
    }

    @ApiOperation(value = "파일 업로드", notes = "성공 여부에 따라 'success' 또는 'fail' 문자열 반환", response = String.class)
    @PostMapping("/file")
    public ResponseEntity<String> uploadImage(@RequestBody MultipartFile file) throws Exception {
        FileInfoDto fileInfoDto = new FileInfoDto();
        if (file != null) {
            String realPath = servletContext.getRealPath("/upload");
            String today = new SimpleDateFormat("yyMMdd").format(new Date());
            String saveFolder = realPath + File.separator + today;
            File folder = new File(saveFolder);
            if (!folder.exists())
                folder.mkdirs();
            System.out.println(file);
            String originalFileName = file.getOriginalFilename();
            if (!originalFileName.isEmpty()) {
                String saveFileName =
                        UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
                fileInfoDto.setSaveFolder(today);
                fileInfoDto.setOriginFile(originalFileName);
                fileInfoDto.setSaveFile(saveFileName);
                System.out.println(fileInfoDto);
                logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", file.getOriginalFilename(), saveFileName);
                file.transferTo(new File(folder, saveFileName));
            }
        }
        if (userService.uploadAuthFile(fileInfoDto)) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.OK);
    }

//    @ApiOperation(value = "파일 다운로드", notes = "관리자 페이지 만들어지면 수정할게요,,", response = String.class)
//    @GetMapping("/file/{userid}")
//    public ResponseEntity<Resource> getFile(@PathVariable String userid) throws Exception {
//        String filePath = servletContext.getRealPath("/upload") + File.separator + fileInfoDto.getSaveFolder() + File.separator + fileInfoDto.getSaveFile();
//        File target = new File(filePath);
//        HttpHeaders header = new HttpHeaders();
//        Resource rs = null;
//        if(target.exists()) {
//            try {
//                String mimeType = Files.probeContentType(Paths.get(target.getAbsolutePath()));
//                if(mimeType == null) {
//                    mimeType = "apllication/download; charset=UTF-8";
//                }
//                rs = new UrlResource(target.toURI());
//                String userAgent = request.getHeader("User-Agent");
//                boolean isIE = userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1;
//                String fileName = null;
//                String originalFile = fileInfoDto.getOriginFile();
//                // IE는 다르게 처리
//                if (isIE) {
//                    fileName = URLEncoder.encode(originalFile, "UTF-8").replaceAll("\\+", "%20");
//                } else {
//                    fileName = new String(originalFile.getBytes("UTF-8"), "ISO-8859-1");
//                }
//                header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ fileName +"\"");
//                header.setCacheControl("no-cache");
//                header.setContentType(MediaType.parseMediaType(mimeType));
//            }catch(Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return new ResponseEntity<Resource>(rs, header, HttpStatus.OK);
//    }

}
