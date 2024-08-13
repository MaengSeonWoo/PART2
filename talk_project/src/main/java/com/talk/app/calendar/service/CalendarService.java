package com.talk.app.calendar.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.talk.app.admin.service.WelfareVO;
import com.talk.app.common.service.SearchVO;

@Service
public interface CalendarService {
		
	
	public int cntWelfare(SearchVO vo);
	
	public WelfareVO welfareDetail(WelfareVO vo);
	
	
	public List<WelfareVO> categoryData(SearchVO vo);
	
	public List<Map<String,Object>> calendar(SearchVO vo);


	
	
}
