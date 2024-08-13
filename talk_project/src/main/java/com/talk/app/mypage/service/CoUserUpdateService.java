package com.talk.app.mypage.service;

import java.util.Map;

import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;

public interface CoUserUpdateService {
	// 기업회원 단건조회
	public CoUserVO couserInfo(CoUserVO couserVO);
	
	// 기업회원정보 수정
	public Map<String, Object> updateCoUser(CoUserVO couserVO);
	
	// 기업회원 탈퇴
	public String deleteCoUser(String coUserId);
	
	// 기업회원 탈퇴취소
	Map<String, Object> cancelCoUser(CoUserVO couserVO);
	
	// =======================================================================
	
	// 일반회원 단건조회
	public UserVO userInfo(UserVO userVO);
	
	// 일반회원정보 수정
	public Map<String, Object> updateUser(UserVO userVO);
}
