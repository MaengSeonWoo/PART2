package com.talk.app.sign.mapper;

import com.talk.app.login.service.CoUserVO;

public interface CoSignMapper {
	public int insertCoSignInfo(CoUserVO couserVO);
	
	public CoUserVO selectCheckCoUser(String CoUserId);
}
