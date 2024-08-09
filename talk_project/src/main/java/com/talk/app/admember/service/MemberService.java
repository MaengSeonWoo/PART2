package com.talk.app.admember.service;

import java.util.List;

import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;

public interface MemberService {

	public List<CoUserVO> approveAll();
	
	public List<UserVO> userAll();
	
}
