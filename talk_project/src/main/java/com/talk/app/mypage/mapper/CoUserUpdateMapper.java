package com.talk.app.mypage.mapper;

import com.talk.app.login.service.CoUserVO;

public interface CoUserUpdateMapper {
	// 기업회원
	public CoUserVO selectCoUserInfo(CoUserVO couserVO);
	
	// 회원정보 수정
	public int updateUserInfo(CoUserVO couserVO);
}
