package com.talk.app.mypage.web;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.talk.app.common.service.PublicCodeService;
import com.talk.app.common.service.UploadService;
import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.LoginUserVO;
import com.talk.app.login.service.UserVO;
import com.talk.app.mypage.service.CoUserUpdateService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CoUserUpdateController {

    private static final Logger log = LoggerFactory.getLogger(CoUserUpdateController.class);

    private final CoUserUpdateService couserupdateService;
    private final UploadService uploadService;
    private final PublicCodeService publiccodeService;
    
    

 // 기업회원 요약정보
    @GetMapping("CoUserMain")
    @PreAuthorize("isAuthenticated()")
    public String couserInfo(Model model, Principal principal) {
        String coUserId = principal.getName(); // 로그인된 유저의 아이디
        CoUserVO couserVO = new CoUserVO();
        couserVO.setCoUserId(coUserId);

        CoUserVO findVO = couserupdateService.couserInfo(couserVO);
        model.addAttribute("company_user", findVO);
        model.addAttribute("coUserId", coUserId); // coUserId를 모델에 추가
        
     // 현재 인증된 사용자의 권한 정보를 모델에 추가
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof LoginUserVO) {
            LoginUserVO loginUser = (LoginUserVO) auth.getPrincipal();
            UserVO user = loginUser.getUserVO();
            model.addAttribute("userAuthority", user.getAuthority()); // 사용자 권한을 모델에 추가
        }
        
        

        return "mypage/couserMain";
    }

    // 기업회원 수정 페이지
    @GetMapping("CoUserUpdate")
    public String CoUserUpdateForm(Principal principal, Model model) {
    	String coUserId = principal.getName(); // 로그인된 유저의 아이디
        CoUserVO couserVO = new CoUserVO();
        couserVO.setCoUserId(coUserId);

        CoUserVO findVO = couserupdateService.couserInfo(couserVO);
        model.addAttribute("couserInfo", findVO);
        
     // 현재 인증된 사용자의 권한 정보를 모델에 추가
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof LoginUserVO) {
            LoginUserVO loginUser = (LoginUserVO) auth.getPrincipal();
            UserVO user = loginUser.getUserVO();
            model.addAttribute("userAuthority", user.getAuthority()); // 사용자 권한을 모델에 추가
        }

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
        log.info("uploadfile length = {}",uploadFiles.length);
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
    
    // 탈퇴 페이지를 보여주는 메소드
    @GetMapping("/couserdelete")
    @PreAuthorize("isAuthenticated()")
    public String showDeleteAccountPage(Model model, Principal principal) {
        String coUserId = principal.getName(); // 로그인된 유저의 아이디
        model.addAttribute("coUserId", coUserId);
        return "mypage/couserDelete";
    }
    
    // 회원탈퇴 시 상태 변경
    @PostMapping("/couserdelete")
    @ResponseBody
    public String deleteCoUser(@RequestParam String coUserId) {
        String result = couserupdateService.deleteCoUser(coUserId);
        // Result에 따라 응답을 결정
        if ("success".equals(result)) {
            return "success"; // 성공 시 "success" 반환
        } else {
            return "failure"; // 실패 시 "failure" 반환
        }
    }
    
    // 탈퇴 취소 페이지를 보여주는 GET 메서드
    @GetMapping("/cancelDel")
    public String showCancelDelPage(Model model, @AuthenticationPrincipal LoginUserVO loginUser) {
        // 로그인된 사용자의 ID를 가져옵니다.
        String coUserId = loginUser.getUsername(); 

        // 모델에 사용자 ID를 추가하여 Thymeleaf 템플릿에서 사용할 수 있도록 합니다.
        model.addAttribute("coUserId", coUserId);

        // 탈퇴 취소 페이지를 반환합니다.
        return "mypage/cancelDel";
    }
    
    // 탈퇴 취소 처리
    @PostMapping("/cancelDel")
    public String cancelDelete(@RequestParam String coUserId, HttpServletRequest request, HttpServletResponse response) {
        // 탈퇴 취소 처리
        CoUserVO couserVO = new CoUserVO();
        couserVO.setCoUserId(coUserId);

        Map<String, Object> result = couserupdateService.cancelCoUser(couserVO);

        if ((boolean) result.get("result")) {
            // 로그아웃 처리
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
            // 메인 페이지로 리다이렉트
            return "redirect:/";
        } else {
            // 탈퇴 취소 실패 시 메시지와 함께 메인 페이지로 리다이렉트
            return "redirect:/?error=탈퇴%20취소에%20실패했습니다.";
        }
        
        
    }
    
    @Scheduled(fixedRate = 600000)
    public void processScheduledDeletion() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 10 * 30 * 1000); 
        couserupdateService.RealDelCoUser(timestamp);
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
        
     // 현재 인증된 사용자의 권한 정보를 모델에 추가
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof LoginUserVO) {
            LoginUserVO loginUser = (LoginUserVO) auth.getPrincipal();
            UserVO user = loginUser.getUserVO();
            model.addAttribute("userAuthority", user.getAuthority()); // 사용자 권한을 모델에 추가
        }

        return "mypage/userMain";
    }
        
     // 일반회원 수정 페이지
        @GetMapping("userupdate")
        public String UserUpdateForm(Principal principal, Model model) {
        	String userId = principal.getName(); // 로그인된 유저의 아이디
            UserVO userVO = new UserVO();
            userVO.setUserId(userId);

            UserVO findVO = couserupdateService.userInfo(userVO);
            model.addAttribute("userInfo", findVO);
            model.addAttribute("sidoCode", publiccodeService.selectCode("0G")); // codeRule이 0G인 지역 코드를 조회하고, 이를 모델에 담아 화면에 전달
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof LoginUserVO) {
                LoginUserVO loginUser = (LoginUserVO) auth.getPrincipal();
                UserVO user = loginUser.getUserVO();
                model.addAttribute("userAuthority", user.getAuthority()); // 사용자 권한을 모델에 추가
            }


        return "mypage/userUpdate";
    }
        @PostMapping("userupdate")
        @ResponseBody
        public Map<String, Object> userUpdateProcess(
        	@ModelAttribute UserVO userVO,
            @RequestPart(value = "uploadFiles", required = false) MultipartFile[] uploadFiles) {

            // 파일 업로드 처리 및 사용자 정보 업데이트 로직 수행
            return couserupdateService.updateUser(userVO);
        }
        
     // 탈퇴 페이지를 보여주는 메소드
        @GetMapping("userdelete")
        @PreAuthorize("isAuthenticated()")
        public String showDeletePage(Model model, Principal principal) {
            String userId = principal.getName(); // 로그인된 유저의 아이디
            model.addAttribute("userId", userId);
            return "mypage/userDelete";
        }
        
        // 회원탈퇴 시 상태 변경
        @PostMapping("/userdelete")
        @ResponseBody
        public String deleteUser(@RequestParam String userId) {
            return couserupdateService.deleteUser(userId);
        }
        
        // 탈퇴 취소 페이지를 보여주는 GET 메서드
        @GetMapping("/canceluserdel")
        public String showCancelPage(Model model, @AuthenticationPrincipal LoginUserVO loginUser) {
            // 로그인된 사용자의 ID를 가져옵니다.
            String userId = loginUser.getUsername(); 

            // 모델에 사용자 ID를 추가하여 Thymeleaf 템플릿에서 사용할 수 있도록 합니다.
            model.addAttribute("userId", userId);

            // 탈퇴 취소 페이지를 반환합니다.
            return "mypage/cancelUserDel";
        }
        
        // 탈퇴 취소 처리
        @PostMapping("/canceluserdel")
        public String cancelUserDelete(@RequestParam String userId, HttpServletRequest request, HttpServletResponse response) {
            // 탈퇴 취소 처리
            UserVO userVO = new UserVO();
            userVO.setUserId(userId);

            Map<String, Object> result = couserupdateService.cancelUser(userVO);

            if ((boolean) result.get("result")) {
                // 로그아웃 처리
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null) {
                    new SecurityContextLogoutHandler().logout(request, response, auth);
                }
                // 메인 페이지로 리다이렉트
                return "redirect:/";
            } else {
                // 탈퇴 취소 실패 시 메시지와 함께 메인 페이지로 리다이렉트
                return "redirect:/?error=탈퇴%20취소에%20실패했습니다.";
            }
            
            
        }
        
        @Scheduled(fixedRate = 600000)
        public void ScheduledDeletion() {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 30 * 1000); 
            couserupdateService.RealDelUser(timestamp);
        }
}
