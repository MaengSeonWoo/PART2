package com.talk.app.stt;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SttController {

	@Autowired
	KomoranService service;
	
	 @GetMapping("/analyze")
	 public String getAnalyzePage() {
	     return "stt/annyang"; // GET 요청 시 반환할 페이지 또는 데이터
	 }
	 
	 
	 @ResponseBody
	 @PostMapping("/analyze")
	    public List<String> analyzeText(@RequestBody Map<String, String> payload) {
	        return service.analyzeText(payload.get("text"));
	    }
	 
}
