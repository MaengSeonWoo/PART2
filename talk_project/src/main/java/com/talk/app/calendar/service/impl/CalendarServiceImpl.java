package com.talk.app.calendar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.admin.service.WelfareVO;
import com.talk.app.calendar.mapper.CalendarMapper;
import com.talk.app.calendar.service.CalendarService;
import com.talk.app.common.service.Criteria;

@Service
public class CalendarServiceImpl implements CalendarService{

	@Autowired
	CalendarMapper mapper;

	@Override
	public List<WelfareVO> selectCalendar(Criteria cri) {
		return mapper.selectAll(cri);
	}

	@Override
	public int cntWelfare() {
		return mapper.coutAll();
	}

	@Override
	public WelfareVO welfareDetail(WelfareVO vo) {
		return mapper.selectInfo(vo);
	}



}
