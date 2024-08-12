package com.talk.app.mypage.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.login.service.CoUserVO;
import com.talk.app.mypage.mapper.CoUserUpdateMapper;
import com.talk.app.mypage.service.CoUserUpdateService;

@Service
public class CoUserUpdateServiceImpl implements CoUserUpdateService{
	
	private CoUserUpdateMapper couserupdateMapper;
	
	@Autowired
	public CoUserUpdateServiceImpl(CoUserUpdateMapper couserupdateMapper) {
		this.couserupdateMapper = couserupdateMapper;
	}
	
	// 기업회원 정보수정
	@Override
	public Map<String, Object> updateCoUser(CoUserVO couserVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		
		int result = couserupdateMapper.updateUserInfo(couserVO);
		if(result == 1) {
			isSuccessed = true;
		}
		map.put("result", isSuccessed);
		map.put("target", couserVO);
		
		return map;
	}
	
	// 기업회원 단건조회
	@Override
	public CoUserVO couserInfo(CoUserVO couserVO) {
		return couserupdateMapper.selectCoUserInfo(couserVO);
	}
	
}
