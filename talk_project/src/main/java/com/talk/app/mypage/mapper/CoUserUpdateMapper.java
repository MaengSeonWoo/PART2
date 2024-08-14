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

	// 기업회원탈퇴 가능 여부 판단(모집중인 채용공고가 있는지 없는지)
	boolean checkPostingStatus(String coUserId);
	
	// 기업회원 탈퇴 상태(del_status = 1) 업데이트
    public void updateCoUserStatus(CoUserVO couserVO);
    
    // 기업회원 탈퇴 상태(del_status = 0) 업데이트
    public int cancelCoUserStatus(CoUserVO couserVO);
    
    // 일정시간이 지난 탈퇴 처리된 기업회원 목록 조회
    List<CoUserVO> findCoUser(Timestamp timestamp);

    // 기업회원의 나머지 컬럼을 공백으로 업데이트
    void updateCoUserBlank(Timestamp timestamp);
	
	// ========================================================
	
	// 일반회원 
	public UserVO selectUserInfo(UserVO userVO);
	
	// 일반회원정보 수정
	public int updateUserInfo(UserVO userVO);
	
	// 일반회원탈퇴 가능 여부 판단(제출한 이력서가 있는지 없는지)
	boolean checkResumeStatus(String userId);
	
	// 일반회원 탈퇴 상태(del_status = 1) 업데이트
    public void updateUserStatus(UserVO userVO);
    
    // 일반회원 탈퇴 상태(del_status = 0) 업데이트
    public int cancelUserStatus(UserVO userVO);
    
    // 일정시간이 지난 탈퇴 처리된 일반회원 목록 조회
    List<CoUserVO> findUser(Timestamp timestamp);

    // 일반회원의 나머지 컬럼을 공백으로 업데이트
    void updateUserBlank(Timestamp timestamp);
}
