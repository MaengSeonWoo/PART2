package com.talk.app.admin.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.talk.app.QnA.service.qnaVO;
import com.talk.app.admember.service.MemberService;
import com.talk.app.admin.service.EmailService;
import com.talk.app.admin.service.SmsService;
import com.talk.app.admin.service.UserWelfareVO;
import com.talk.app.admin.service.WelfareService;
import com.talk.app.admin.service.WelfareVO;
import com.talk.app.common.service.UploadFileVO;
import com.talk.app.common.service.UploadService;
import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;
import com.talk.app.posting.service.PostingVO;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Balance;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.MessageListRequest;
import net.nurigo.sdk.message.response.MessageListResponse;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	final DefaultMessageService messageService;

	public AdminController(
			@Value("${coolsms.api_key}") String apiKey,
			@Value("${coolsms.api_secret}") String apiSecret) {
		// 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
		this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
	}

	@Autowired
	SmsService sms;

	@Autowired
	WelfareService service;
	
	@Autowired
	MemberService mservice;

	@Autowired
	UploadService uservice;
	
	@Autowired
    EmailService eservice;
	

	// 메인
	@GetMapping("")
	// @PreAuthorize(hasRole("Admin")) 
	// 기업가입목록, 채용승인목록, 일반회원, 기업회원
	public String main(Model model, CoUserVO cvo,PostingVO pvo, qnaVO qvo) {
		List<CoUserVO> clist = mservice.couserApprove();
		List<PostingVO> plist = mservice.postingApprove();
		List<qnaVO> ulist = mservice.userAnswer();
		List<qnaVO> colist = mservice.coAnswer();
		model.addAttribute("cuser",clist);
		model.addAttribute("posting",plist);
		model.addAttribute("ulist",ulist);
		model.addAttribute("colist",colist);
		return "admin/main";
	}
	
	// 복지목록
	@GetMapping("welfare")
	public String welfare(Model model) {
		List<WelfareVO> list = service.welfareList();
		model.addAttribute("welfare", list);
		return "admin/welfare";
	}

	// 복지상세
	@GetMapping("detail")
	public String welfareDetail(WelfareVO vo, Model model, UserVO uvo) {
		List<UploadFileVO> images = uservice.selectFilesByDomain("Welfare", (long) vo.getWid());
		model.addAttribute("file", images);

		WelfareVO findvo = service.welfareDetail(vo);
		model.addAttribute("detail", findvo);
		
		 uvo.setSido(findvo.getSido());
		 uvo.setLikeSubject(findvo.getLikeSubject());
		 uvo.setHousehold(findvo.getHousehold());
		
		int count = service.sendCount(uvo, vo.getWid());
		model.addAttribute("count",count);
		return "admin/info";
	}

	// 복지 첨부보기
	@GetMapping("pdf")
	public String pdffile(Model model, WelfareVO vo, UploadFileVO fvo, @RequestPart MultipartFile[] uploadFiles) {
		WelfareVO findvo = service.welfareDetail(vo);
		model.addAttribute("pdfFile", findvo);
		String pdf = uservice.pdfData("Welfare", (long) vo.getWid(), fvo.getFilePath());
		model.addAttribute("file", pdf);
		return "admin/pdfview";
	}

	// 새글페이지
	@GetMapping("new")
	public String newPost() {
		return "admin/write";
	}

	// 새글입력
	@PostMapping("new")
	public String newPosting(WelfareVO vo, @RequestPart MultipartFile[] uploadFiles) {
		int wid = service.welfareInsert2(vo);
		uservice.imageUpload(uploadFiles, "Welfare", (long) wid);
		return "redirect:detail?wid=" + wid;
	}

	// 수정페이지
	@GetMapping("update")
	public String updatePost(WelfareVO vo, Model model) {
		WelfareVO findVO = service.welfareDetail(vo);
		model.addAttribute("welfare", findVO);
		return "admin/update";
	}

	// 수정처리
	@PostMapping("update")
	@ResponseBody
	public Map<String, Object> updatePosting(WelfareVO vo, @RequestPart MultipartFile[] uploadFiles) {
		uservice.imageUpdate(uploadFiles, "Welfare", (long) vo.getWid());
		return service.welfareUpdate(vo);
	}

	// 삭제
	@GetMapping("delete")
	public String welfareDelete(@RequestParam Integer wid) {
		uservice.deleteFiles("Notice", (long) wid);
		service.welfareDelete(wid);
		return "redirect:welfare";
	}

	@GetMapping("sendmsg")
	public String msgSend(Model model, UserWelfareVO vo) {
		List<UserWelfareVO> list = service.userMsg(vo);
		model.addAttribute("user", list);
		return "redirect:welfare";
	}

	/**
	 * 메시지 조회 예제
	 */
	@GetMapping("/messagelist")
	public String getMessageList(Model model) {
		MessageListRequest request = new MessageListRequest();

		// 검색할 건 수, 값 미지정 시 20건 조회, 최대 500건 까지 설정 가능
		request.setLimit(100);

		MessageListResponse response = this.messageService.getMessageList(request);
//        System.out.println(response);
		model.addAttribute("smslist", response);
		return "admin/getlist";
	}

	/**
	 * 잔액 조회 예제
	 */
	@GetMapping("/cost")
	public String getBalance(Model model) {
		Balance balance = this.messageService.getBalance();
//        MessageListResponse msglist = this.messageService.getMessageList();
		System.out.println(balance);
		model.addAttribute("cost", balance);
		return "admin/cost";
	}

	//문자 전송하기
	@GetMapping("/sendmany")
	public String sendResult(UserWelfareVO vo, Model model,UserVO uvo) {
		UserWelfareVO list = sms.sendSmsToEligibleMembers(vo);
		int wid = list.getWid();
		List<UserWelfareVO> list2 = service.msgResult(vo, wid);
		model.addAttribute("sends", list2);
		return "admin/sendlist";
	}

	/**
	 * 여러 메시지 발송 예제 한 번 실행으로 최대 10,000건 까지의 메시지가 발송 가능합니다.
	 */
	@PostMapping("/sendmany")
	public MultipleDetailMessageSentResponse sendMany() {
		MessageListRequest request = new MessageListRequest();
		request.setLimit(10);
		MessageListResponse response = this.messageService.getMessageList(request);
		ArrayList<Message> messageList = new ArrayList<>();
		return null;
	}


}
