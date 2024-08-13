package com.talk.app.admin.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talk.app.admin.service.WelfareService;
import com.talk.app.admin.service.WelfareVO;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	WelfareService service;
	
	//메인
	@GetMapping("")
	//@PreAuthorize(hasRole("Admin"))
	public String main(Model model) {
		return "admin/main";
	}
	//복지목록
	@GetMapping("welfare")
	public String welfare(Model model) {
		List<WelfareVO> list = service.welfareList();
		model.addAttribute("welfare",list);
		return "admin/welfare";
	}
	//복지상세
	@GetMapping("detail")
	public String welfareDetail(WelfareVO vo, Model model) {
		WelfareVO findvo = service.welfareDetail(vo);
		model.addAttribute("detail",findvo);
		return "admin/info";
	}
	//새글페이지
	@GetMapping("new")
	public String newPost() {
		return "admin/write";
	}
	//새글입력
	@PostMapping("new")
	public String newPosting(WelfareVO vo) {
		int wid = service.welfareInsert2(vo);
		return "redirect:detail?wid=" + wid;
	}
	//수정페이지
	@GetMapping("update")
	public String updatePost(WelfareVO vo, Model model) {
		WelfareVO findVO = service.welfareDetail(vo);
		model.addAttribute("welfare",findVO);
		return "admin/update";
	}
	//수정처리
	@PostMapping("update")
	@ResponseBody
	public Map<String,Object> updatePosting(@RequestBody WelfareVO vo) {
		return service.welfareUpdate(vo);
	}
	//삭제
	@GetMapping("delete")
	public String welfareDelete(@RequestParam Integer wid) {
		service.welfareDelete(wid);
		return "redirect:welfare";
	}
	

	
}
