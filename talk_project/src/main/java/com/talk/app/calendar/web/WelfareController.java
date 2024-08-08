package com.talk.app.calendar.web;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talk.app.admin.service.WelfareVO;
import com.talk.app.calendar.service.CalendarService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/calendar")
@RequiredArgsConstructor
@Controller
public class WelfareController {
	
	@Autowired
	CalendarService service;
	
	@GetMapping("")
	@ResponseBody
	public String welfaremain() {
		 List<WelfareVO> listAll = service.selectCalendar();
		 
	        JSONObject jsonObj = new JSONObject();
	        JSONArray jsonArr = new JSONArray();
	 
	        HashMap<String, Object> hash = new HashMap<>();
	 
	        for (int i = 0; i < listAll.size(); i++) {
	            hash.put("id", listAll.get(i).getWid());
	            hash.put("servId", listAll.get(i).getServId());
	        	hash.put("title", listAll.get(i).getServName());
	            hash.put("start", listAll.get(i).getStartDate());
	            hash.put("end",listAll.get(i).getEndDate());
	            hash.put("sido", listAll.get(i).getSido());
	            hash.put("sgg", listAll.get(i).getSgg());
	            hash.put("like", listAll.get(i).getLikeSubject());
	            
	 
	            jsonObj = new JSONObject(hash);
	            jsonArr.put(jsonObj);
	        }
	        
	        return jsonArr.toString();
	}
	
	@GetMapping("list")
	public String json(){
		return "calendar/list";
	}
	
	
}
