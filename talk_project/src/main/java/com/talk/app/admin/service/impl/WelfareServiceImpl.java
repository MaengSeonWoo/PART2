package com.talk.app.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.admin.mapper.WelfareMapper;
import com.talk.app.admin.service.WelfareService;
import com.talk.app.admin.service.WelfareVO;

@Service
public class WelfareServiceImpl implements WelfareService{

	@Autowired
	private WelfareMapper mapper;

	@Override
	public List<WelfareVO> welfareList() {
		return mapper.getAllWelfareInfo();
	}


	

}
