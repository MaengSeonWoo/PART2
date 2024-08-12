package com.talk.app.calendar.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.admin.service.WelfareVO;
import com.talk.app.calendar.mapper.CalendarMapper;
import com.talk.app.calendar.service.CalendarService;
import com.talk.app.common.service.SearchVO;

@Service
public class CalendarServiceImpl implements CalendarService{

	@Autowired
	CalendarMapper mapper;
	

	@Override
	public int cntWelfare(SearchVO vo) {
		return mapper.coutAll(vo);
	}

	@Override
	public WelfareVO welfareDetail(WelfareVO vo) {
		return mapper.selectInfo(vo);
	}

	@Override
	public List<WelfareVO> categoryData(SearchVO vo) {
		return mapper.categoryData(vo);
	}

	@Override
	public List<Map<String, Object>> calendar(SearchVO vo) {
		return mapper.calendar(vo);
	}



//	@Override
//	public List<WelfareVO> categoryData(Criteria cir,String sido, List<WelfareVO> likeSubject) {
//		return mapper.categoryData(cri, sido, likeSubject);
//	}






}
