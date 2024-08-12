package com.talk.app.mypage.service;

import java.util.Map;

import com.talk.app.login.service.CoUserVO;

public interface CoUserUpdateService {
	// 기업회원 단건조회
	public CoUserVO couserInfo(CoUserVO couserVO);
	
	// 회원정보 수정
	public Map<String, Object> updateCoUser(CoUserVO couserVO);
}
