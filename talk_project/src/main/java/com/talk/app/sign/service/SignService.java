package com.talk.app.sign.service;

import com.talk.app.login.service.UserVO;

public interface SignService {
	// ȸ������
	public int insertSign(UserVO userVO);
	
	public UserVO selectCheckUser(String userId);
}
