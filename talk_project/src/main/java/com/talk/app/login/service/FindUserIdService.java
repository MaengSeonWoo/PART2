package com.talk.app.login.service;

public interface FindUserIdService {
	public String findUserId(UserVO userVO);
	
	boolean sendTempPw(UserVO userVO);
}
