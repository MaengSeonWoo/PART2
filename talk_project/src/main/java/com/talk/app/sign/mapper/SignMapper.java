package com.talk.app.sign.mapper;

import com.talk.app.login.service.UserVO;

public interface SignMapper {
	// 회원가입
	public int insertSignInfo(UserVO userVO);
}
