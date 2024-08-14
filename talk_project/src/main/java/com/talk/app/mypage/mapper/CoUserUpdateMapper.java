package com.talk.app.mypage.mapper;

import java.sql.Timestamp;
import java.util.List;

import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;

public interface CoUserUpdateMapper {
	// 기업회원
	public CoUserVO selectCoUserInfo(CoUserVO couserVO);
	
	// 기업회원정보 수정
	public int updateCoUserInfo(CoUserVO couserVO);

	// 회원탈퇴 가능 여부 판단
	boolean checkPostingStatus(String coUserId);
	
	// 기업회원 탈퇴 상태(del_status = 1) 업데이트
    public void updateCoUserStatus(CoUserVO couserVO);
    
    // 기업회원 탈퇴 상태(del_status = 0) 업데이트
    public int cancelCoUserStatus(CoUserVO couserVO);
    
    // 10분이 지난 탈퇴 처리된 사용자 목록 조회
    List<CoUserVO> findCoUser(Timestamp timestamp);

    // 나머지 필드를 공백으로 업데이트
    void updateCoUserBlank(Timestamp timestamp);
	
	// ========================================================
	
	// 일반회원 
	public UserVO selectUserInfo(UserVO userVO);
	
	// 일반회원정보 수정
	public int updateUserInfo(UserVO userVO);
}
