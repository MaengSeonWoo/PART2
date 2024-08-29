package com.talk.app.admember.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.talk.app.admember.service.MemberService;
import com.talk.app.admin.service.EmailService;
import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;
import com.talk.app.posting.service.PostingVO;

@Controller
@RequestMapping("admin/approve")
public class MemberController {

	@Autowired
	MemberService service;
	
	@Autowired
    EmailService eservice;
	
	//기업회원 승인목록(기업목록 전체 신청desc처리)
	@GetMapping("")
	public String approveAll(Model model, CoUserVO vol) {
		List<CoUserVO> list = service.approveAll();
		model.addAttribute("list",list);
		return "admember/approvelist";
	}
	
	//기업회원 가입신청 상세
	@GetMapping("detail")
	public String approveDetail(Model model, CoUserVO vo, UserVO uvo) {
		CoUserVO findVO = service.coDetail(vo);
		eservice.sendFailEmail(vo);
		model.addAttribute("detail", findVO);
	    model.addAttribute("user",vo);
		return "admember/coDetail";
	}
	
	// 메일 발송
	@GetMapping("/sendMail")
	public String mail(CoUserVO vo,Model model) {
		CoUserVO covo = new CoUserVO();
		vo.setCoUserId(covo.getCoUserId());
		vo.setMgrEmail(covo.getMgrEmail());
		vo.setCoName(covo.getCoName());
	    eservice.sendFailEmail(vo);
	    model.addAttribute("user",vo);
	    return "admin/mail";
	} 

	//기업회원 승인 => 
	@GetMapping("confirm")
	public String updateCo(int coUserNo) {
		int wid = service.coUpdate(coUserNo);
		return "redirect:/admin/approve";
	}
	
	//기업회원 가입거절(승인상태변경,데이터 지우기)
	@GetMapping("refusejoin")
	public String refuseCo(Model model, int coUserNo) {
		int refuse = service.coUserNo(coUserNo);
		model.addAttribute("refuse",refuse);
		return "redirect:/admin/approve";
	}
	
	
	//채용등록 리스트
	@GetMapping("postlist")
	public String postlist(Model model, PostingVO vo) {
		List<PostingVO> list = service.postAll();
		model.addAttribute("post",list);
		return "admember/postlist";
	}
	
	//채용등록 상세
	@GetMapping("postdetail")
	public String detailPost(Model model, PostingVO vo) {
		PostingVO findVO = service.postdetail(vo);
		model.addAttribute("detail",findVO);
		return "admember/postdetail";
	}
	
	//채용등록 승인
	@GetMapping("postingok")
	public String updatePost(int postingNo) {
		int pid = service.postUpdate(postingNo);
		return "redirect:/admin/approve/postlist";
	}
	
	//채용등록 거절
	@GetMapping("noposting")
	public String refusePost(Model model, int postingNo) {
		int refuse = service.postRefuse(postingNo);
		model.addAttribute("refuse",refuse);
		return "redirect:/admin/approve/postlist";
	}
	
	//일반회원전체
	@GetMapping("user")
	public String userAll(Model model, UserVO vo) {
		List<UserVO> list = service.userAll();
		model.addAttribute("ulist",list);
		return "admember/user";
	}
	

	
}
