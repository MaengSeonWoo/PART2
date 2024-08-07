package com.talk.app.sign.mapper;

import com.talk.app.login.service.UserVO;

public interface SignMapper {
	// ȸ������
	public int insertSignInfo(UserVO userVO);
	
	public UserVO selectCheckUser(String userId);
}
