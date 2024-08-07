package com.talk.app.sign.service;

import com.talk.app.login.service.CoUserVO;

public interface CoSignService {
	public int insertCoSign(CoUserVO couserVO);
	
	public CoUserVO selectCheckCoUser(String CoUserId);
}
