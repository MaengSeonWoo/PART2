package com.talk.app.mypage.service;

import java.util.List;

import com.talk.app.admin.service.WelfareVO;

public interface UserWelfareService {
	// 정책/복지 조회
	public List<WelfareVO> userWelfareList(String userId);
}
