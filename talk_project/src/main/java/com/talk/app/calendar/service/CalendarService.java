package com.talk.app.calendar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talk.app.admin.service.WelfareVO;

@Service
public interface CalendarService {
		
	public List<WelfareVO> selectCalendar();
}
