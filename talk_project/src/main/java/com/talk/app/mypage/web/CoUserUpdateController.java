package com.talk.app.mypage.web;

import java.security.Principal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.talk.app.common.service.UploadService;
import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;
import com.talk.app.mypage.service.CoUserUpdateService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CoUserUpdateController {

    private static final Logger log = LoggerFactory.getLogger(CoUserUpdateController.class);

    private final CoUserUpdateService couserupdateService;
    private final UploadService uploadService;

 // 기업회원 요약정보
    @GetMapping("CoUserMain")
    public String couserInfo(Model model, Principal principal) {
        String coUserId = principal.getName(); // 로그인된 유저의 아이디
        CoUserVO couserVO = new CoUserVO();
        couserVO.setCoUserId(coUserId);

        CoUserVO findVO = couserupdateService.couserInfo(couserVO);
        model.addAttribute("company_user", findVO);
        model.addAttribute("coUserId", coUserId); // coUserId를 모델에 추가

        return "mypage/couserMain";
    }

    // 기업회원 수정 페이지
    @GetMapping("CoUserUpdate/{coUserId}")
    public String CoUserUpdateForm(@PathVariable String coUserId, Model model) {
        CoUserVO couserVO = new CoUserVO();
        couserVO.setCoUserId(coUserId);

        CoUserVO findVO = couserupdateService.couserInfo(couserVO);
        model.addAttribute("couserInfo", findVO);

        return "mypage/couserUpdate";
    }

    // 기업회원 수정 및 이미지 
    @PostMapping("CoUserUpdate")
    @ResponseBody
    public Map<String, Object> couserUpdate(
            @ModelAttribute CoUserVO couserVO,
            @RequestPart(value = "uploadFiles") MultipartFile[] uploadFiles) {

    	// 기업회원 수정 처리
        Map<String, Object> updateResult;
        
        log.info("ddd={}" ,couserVO.getCoUserNo());
        // 파일 수정 처리
        log.info("uploadfile length = {}",uploadFiles.length  );
        log.info("uploadfile name = {}",uploadFiles[0].getOriginalFilename()  );
        if (uploadFiles[0].getSize() != 0  && uploadFiles.length == 1) {
                // 파일 업로드 서비스 호출
        	for (MultipartFile multipartFile : uploadFiles) {
        		log.info("업로드 파일 수: {}", multipartFile.getSize());
        		log.info("파일 이름: {}, 파일 크기: {}", multipartFile.getOriginalFilename(), multipartFile.getSize());
        		couserVO.setLogoImg(multipartFile.getOriginalFilename());
			}
        	String imageUpdate = uploadService.imageUpdate(uploadFiles, "COUSER", (long)couserVO.getCoUserNo());
        	imageUpdate = imageUpdate.replace("\\", "/");
            
        	couserVO.setLogoImg(imageUpdate);
        	
        	log.info("dddd={}", imageUpdate);
                // 예: uploadService.imageUpdate(uploadFiles, "BOARD", couserVO.getCoUserNo());
        	updateResult = couserupdateService.updateCoUser(couserVO);
        } else {
        	updateResult = couserupdateService.updateCoUser(couserVO);
            log.info("업로드된 파일이 없습니다.");
        }

        return updateResult;
    }
    
    // ======================================================================================================
    
    // 일반회원 요약정보
    @GetMapping("userMain")
    public String userInfo(Model model, Principal principal) {
        String userId = principal.getName(); // 로그인된 유저의 아이디
        UserVO userVO = new UserVO();
        userVO.setUserId(userId);
        log.info(userId);

        UserVO findVO = couserupdateService.userInfo(userVO);
        model.addAttribute("users", findVO);
        model.addAttribute("userId", userId); // userId를 모델에 추가

        return "mypage/userMain";
    }
        
     // 일반회원 수정 페이지
        @GetMapping("userUpdate/{userId}")
        public String UserUpdateForm(@PathVariable String userId, Model model) {
            UserVO userVO = new UserVO();
            userVO.setUserId(userId);

            UserVO findVO = couserupdateService.userInfo(userVO);
            model.addAttribute("userInfo", findVO);


        return "mypage/userUpdate";
    }
        @PostMapping("userUpdate")
        @ResponseBody
        public Map<String, Object> userUpdateProcess(
        	@ModelAttribute UserVO userVO,
            @RequestPart(value = "uploadFiles", required = false) MultipartFile[] uploadFiles) {

            // 파일 업로드 처리 및 사용자 정보 업데이트 로직 수행
            return couserupdateService.updateUser(userVO);
        }
}
