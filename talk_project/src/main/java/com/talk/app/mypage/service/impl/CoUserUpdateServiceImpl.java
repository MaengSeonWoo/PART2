package com.talk.app.mypage.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;
import com.talk.app.mypage.mapper.CoUserUpdateMapper;
import com.talk.app.mypage.service.CoUserUpdateService;

@Service
public class CoUserUpdateServiceImpl implements CoUserUpdateService{
	
	private CoUserUpdateMapper couserupdateMapper;
	
	@Autowired
	public CoUserUpdateServiceImpl(CoUserUpdateMapper couserupdateMapper) {
		this.couserupdateMapper = couserupdateMapper;
	}
	
	// 기업회원 정보수정
	@Override
	public Map<String, Object> updateCoUser(CoUserVO couserVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		
		
		int result = couserupdateMapper.updateCoUserInfo(couserVO);
		if(result == 1) {
			isSuccessed = true;
		}
		map.put("result", isSuccessed);
		map.put("target", couserVO);
		
		return map;
	}
	
	// 기업회원 단건조회
	@Override
	public CoUserVO couserInfo(CoUserVO couserVO) {
		return couserupdateMapper.selectCoUserInfo(couserVO);
	}
	
	// 기업회원 탈퇴
	@Override
	public String deleteCoUser(String coUserId) {
		// 탈퇴가 가능한지 확인
        boolean canDelete = couserupdateMapper.checkPostingStatus(coUserId);

        if (canDelete == true) {
            return "채용공고가 모집중인 상태에서는 탈퇴가 불가능합니다.";
        }

        // 탈퇴 상태로 업데이트
        CoUserVO couserVO = new CoUserVO();
        couserVO.setCoUserId(coUserId);
//      couserVO.setDelStatus(1); // 탈퇴 상태로 설정
        couserupdateMapper.updateCoUserStatus(couserVO);
        return "탈퇴 처리 완료";
    }
	
	@Override
	public Map<String, Object> cancelCoUser(CoUserVO couserVO) {
	    Map<String, Object> map = new HashMap<>();
	    boolean isSuccessed = false;

	    // del_status를 0으로 설정하여 탈퇴 취소
	    couserVO.setDelStatus(0);
	    int rowsAffected = couserupdateMapper.cancelCoUserStatus(couserVO); // 수정된 메서드 호출

	    isSuccessed = (rowsAffected > 0); // 업데이트 성공 여부 확인
	    map.put("result", isSuccessed);

	    return map;
	}
	
	// ===================================================================================
	
	// 일반회원 단건조회
	@Override
	public UserVO userInfo(UserVO userVO) {
		// TODO Auto-generated method stub
		return couserupdateMapper.selectUserInfo(userVO);
	}
	
	@Override
	public Map<String, Object> updateUser(UserVO userVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		
		int result = couserupdateMapper.updateUserInfo(userVO);
		if(result == 1) {
			isSuccessed = true;
		}
		map.put("result", isSuccessed);
		map.put("target", userVO);
		
		return map;
	}

	@Override
	public UserVO getUserInfo(UserVO userVO) {
		// TODO Auto-generated method stub
		return null;
	}


	
	
}
