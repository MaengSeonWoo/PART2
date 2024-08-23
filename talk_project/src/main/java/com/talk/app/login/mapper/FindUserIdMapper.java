package com.talk.app.login.mapper;

import java.util.List;

import com.talk.app.login.service.UserVO;

public interface FindUserIdMapper {
	// 아이디 찾기
	public String FindUserIdInfo(UserVO userVO);
	
	// 임시 비밀번호 발급
	
}
