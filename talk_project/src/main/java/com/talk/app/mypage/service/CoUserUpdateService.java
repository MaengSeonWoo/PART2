package com.talk.app.mypage.service;

import java.sql.Timestamp;
import java.util.Map;

import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;

public interface CoUserUpdateService {
	// 기업회원 단건조회
	public CoUserVO couserInfo(CoUserVO couserVO);
	
	// 기업회원정보 수정
	public Map<String, Object> updateCoUser(CoUserVO couserVO);
	
	// 기업회원 임시탈퇴
	public String deleteCoUser(String coUserId);
	
	// 기업회원 탈퇴취소
	Map<String, Object> cancelCoUser(CoUserVO couserVO);
	
	// 기업회원 탈퇴처리
	public void RealDelCoUser(Timestamp timestamp);
	
	// =======================================================================
	
	// 일반회원 단건조회
	public UserVO userInfo(UserVO userVO);
	
	//	UserVO getUserInfo(UserVO userVO); // 일반 회원 정보 조회
	
	// 일반회원정보 수정
	public Map<String, Object> updateUser(UserVO userVO);
	
	// 기업회원 임시탈퇴
	public String deleteUser(String userId);
	
	// 일반회원의 del_status를 조회하는 메서드
    int getUserDelStatus(String userId);
	
	// 기업회원 탈퇴취소
	Map<String, Object> cancelUser(UserVO userVO);
	
	// 기업회원 탈퇴처리
	public void RealDelUser(Timestamp timestamp);
}
