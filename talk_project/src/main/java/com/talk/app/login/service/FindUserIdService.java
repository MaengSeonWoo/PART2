package com.talk.app.login.service;

import java.util.List;

public interface FindUserIdService {
	public String findUserId(UserVO userVO);
	
	boolean sendTempPw(UserVO userVO);
}
