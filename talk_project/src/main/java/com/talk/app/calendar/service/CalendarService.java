package com.talk.app.calendar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talk.app.admin.service.WelfareVO;
import com.talk.app.common.service.Criteria;

@Service
public interface CalendarService {
		
	public List<WelfareVO> selectCalendar(Criteria cri);
	
	public int cntWelfare();
	
	public WelfareVO welfareDetail(WelfareVO vo);
}
