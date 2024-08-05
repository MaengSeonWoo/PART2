package com.talk.app.sign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.login.service.UserVO;
import com.talk.app.sign.mapper.SignMapper;
import com.talk.app.sign.service.SignService;

@Service
public class SignServiceImpl implements SignService{
	@Autowired
	private SignMapper signMapper;
	
	@Override
	public int insertSign(UserVO userVO) {
		int result = signMapper.insertSignInfo(userVO);
		
		return result == 1 ? userVO.getUserNo() : -1;
	}
	
}
