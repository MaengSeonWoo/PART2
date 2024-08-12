package com.talk.app.calendar.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.talk.app.admin.service.WelfareVO;
import com.talk.app.common.service.SearchVO;

@Service
public interface CalendarService {
		
//	public List<WelfareVO> selectCalendar(Criteria cri);
	
	public int cntWelfare(SearchVO vo);
	
	public WelfareVO welfareDetail(WelfareVO vo);
	
//	public List<WelfareVO> categoryData(Criteria cir, String sido, List<WelfareVO> likeSubject);
	
	public List<WelfareVO> categoryData(SearchVO vo);
	
	public List<Map<String,Object>> calendar(SearchVO vo);

	
	
}
