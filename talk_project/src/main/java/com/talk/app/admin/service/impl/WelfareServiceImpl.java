package com.talk.app.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.admin.mapper.WelfareMapper;
import com.talk.app.admin.service.WelfareService;
import com.talk.app.admin.service.WelfareVO;

@Service
public class WelfareServiceImpl implements WelfareService{

	@Autowired
	WelfareMapper mapper;
	
//	@Override
//	public int welfareIdInsert(WelfareVO vo) {
//		int result = mapper.insertServId(vo);
//		return result;
//	}
	
	@Override
	public int welfareIdInsert(WelfareVO vo) {
		int result = mapper.insertServId(vo);
		return result;
	}

}
