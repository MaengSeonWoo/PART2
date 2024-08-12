package com.talk.app.mypage.mapper;

import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;

public interface CoUserUpdateMapper {
	// 기업회원
	public CoUserVO selectCoUserInfo(CoUserVO couserVO);
	
	// 기업회원정보 수정
	public int updateCoUserInfo(CoUserVO couserVO);
	
	// ========================================================
	
	// 일반회원 
	public UserVO selectUserInfo(UserVO userVO);
	
	// 일반회원정보 수정
	public int updateUserInfo(UserVO userVO);
}
