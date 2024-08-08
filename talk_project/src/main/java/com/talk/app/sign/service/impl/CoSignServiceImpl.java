package com.talk.app.sign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.login.service.CoUserVO;
import com.talk.app.sign.mapper.CoSignMapper;
import com.talk.app.sign.service.CoSignService;

@Service
public class CoSignServiceImpl implements CoSignService{
	@Autowired
	private CoSignMapper cosignMapper;
	
	@Override
	public int insertCoSign(CoUserVO couserVO) {
		int result = cosignMapper.insertCoSignInfo(couserVO);
		
		return result == 1 ? couserVO.getCoUserNo() : -1;
	}

	@Override
	public CoUserVO selectCheckCoUser(String CoUserId) {
		return cosignMapper.selectCheckCoUser(CoUserId);
	}
	
}
