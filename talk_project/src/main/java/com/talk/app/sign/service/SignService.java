package com.talk.app.sign.service;

import com.talk.app.login.service.UserVO;

public interface SignService {
	// 회원가입
	public int insertSign(UserVO userVO);
}
