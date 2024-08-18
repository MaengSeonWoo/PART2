package com.talk.app.mypage.mapper;

import java.util.List;

import com.talk.app.admin.service.WelfareVO;

public interface UserWelfareMapper {
	public List<WelfareVO> selectWelfareAll(String userId);
}
