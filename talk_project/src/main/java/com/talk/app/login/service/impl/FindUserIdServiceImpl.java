package com.talk.app.login.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.login.mapper.FindUserIdMapper;
import com.talk.app.login.service.FindUserIdService;
import com.talk.app.login.service.UserVO;

@Service
public class FindUserIdServiceImpl implements FindUserIdService{
	
	@Autowired
	private FindUserIdMapper findIdMapper;

	@Override
	public String findUserId(UserVO userVO) {
		// TODO Auto-generated method stub
		return findIdMapper.FindUserIdInfo(userVO);
	}




}
