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

	public AdminController(@Value("${coolsms.api_key}") String apiKey,
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
    EmailService emailService;
	

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
		// 검색 조건이 있는 경우에 MessagListRequest를 초기화 하여 getMessageList 함수에 파라미터로 넣어서 검색할 수
		// 있습니다!.
		// 수신번호와 발신번호는 반드시 -,* 등의 특수문자를 제거한 01012345678 형식으로 입력해주셔야 합니다!
		MessageListRequest request = new MessageListRequest();

		// 검색할 건 수, 값 미지정 시 20건 조회, 최대 500건 까지 설정 가능
		request.setLimit(500);

		// 조회 후 다음 페이지로 넘어가려면 조회 당시 마지막의 messageId를 입력해주셔야 합니다!
		// request.setStartKey("메시지 ID");

		// request.setTo("검색할 수신번호");
		// request.setFrom("검색할 발신번호");

		// 메시지 상태 검색, PENDING은 대기 건, SENDING은 발송 중,COMPLETE는 발송완료, FAILED는 발송에 실패한 모든
		// 건입니다.
		/*
		 * request.setStatus(MessageStatusType.PENDING);
		 * request.setStatus(MessageStatusType.SENDING);
		 * request.setStatus(MessageStatusType.COMPLETE);
		 * request.setStatus(MessageStatusType.FAILED);
		 */

		// request.setMessageId("검색할 메시지 ID");

		// 검색할 메시지 목록
		/*
		 * ArrayList<String> messageIds = new ArrayList<>();
		 * messageIds.add("검색할 메시지 ID"); request.setMessageIds(messageIds);
		 */

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
//    	List<UserWelfareVO> list = sms.sendSmsToEligibleMembers(vo);
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

		/*
		 * for (int i = 0; i < 2; i++) { Message message = new Message(); // 발신번호 및
		 * 수신번호는 반드시 01012345678 형태로 입력되어야 합니다. message.setFrom("01025193424");
		 * message.setTo("01025193424");
		 * message.setText("한글 45자, 영자 90자 이하 입력되면 자동으로 SMS타입의 메시지가 추가됩니다." + i);
		 * 
		 * // 메시지 건건 마다 사용자가 원하는 커스텀 값(특정 주문/결제 건의 ID를 넣는등)을 map 형태로 기입하여 전송 후 확인해볼 수
		 * 있습니다! HashMap<String, String> map = new HashMap<>();
		 * 
		 * map.put("키 입력", "값 입력"); message.setCustomFields(map);
		 * 
		 * messageList.add(message); } try { // send 메소드로 단일 Message 객체를 넣어도 동작합니다! // 세
		 * 번째 파라미터인 showMessageList 값을 true로 설정할 경우 MultipleDetailMessageSentResponse에서
		 * MessageList를 리턴하게 됩니다! MultipleDetailMessageSentResponse resp =
		 * this.messageService.send(messageList, false, true);
		 * 
		 * // 중복 수신번호를 허용하고 싶으실 경우 위 코드 대신 아래코드로 대체해 사용해보세요!
		 * //MultipleDetailMessageSentResponse response =
		 * this.messageService.send(messageList, true); System.out.println(response);
		 * System.out.println(resp); return resp; } catch
		 * (NurigoMessageNotReceivedException exception) {
		 * System.out.println(exception.getFailedMessageList());
		 * System.out.println(exception.getMessage()); } catch (Exception exception) {
		 * System.out.println(exception.getMessage()); }
		 */
		return null;
	}
	
	/*
	 * @PostMapping("/sendEmail")
	 * 
	 * @ResponseBody public String sendEmail(@RequestParam String to) {
	 * emailService.sendSimpleMessage(to); return "Email sent to " + to; }
	 */
	
	
	
	
	
	
	

}
