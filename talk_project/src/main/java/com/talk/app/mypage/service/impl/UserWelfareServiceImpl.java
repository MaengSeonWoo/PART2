package com.talk.app.mypage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.admin.service.WelfareVO;
import com.talk.app.mypage.mapper.UserWelfareMapper;
import com.talk.app.mypage.service.UserWelfareService;

@Service
public class UserWelfareServiceImpl implements UserWelfareService{
	
	private UserWelfareMapper userwelfareMapper;
	
	@Autowired
	public UserWelfareServiceImpl(UserWelfareMapper userwelfareMapper) {
		this.userwelfareMapper = userwelfareMapper;
	}
	
	@Override
	public List<WelfareVO> userWelfareList(String userId) {
		return userwelfareMapper.selectWelfareAll(userId);
	}

}
