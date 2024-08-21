package com.talk.app.QnA.mapper;

import java.util.List;

import com.talk.app.QnA.service.qnaVO;
import com.talk.app.login.service.UserVO;

public interface QnAMapper {
	// 전체조회
	public List<qnaVO> selectQnAAll(String role);
	
	// 본인 전체조회
	/* public List<qnaVO> myQnaAll(qnaVO qnavo); */
	
	// 단건조회
	public qnaVO selectQnAInfo(qnaVO qnavo);
	
	// 등록 
	public int insertQnAInfo(qnaVO qnavo);
	
	// 수정 
	public int updateQnAInfo(qnaVO qnavo);
	
	// 삭제 : 조건 - no
	public int deleteQnAInfo(int qnaNo);

	// 유저정보 받기
	public UserVO userInfo(String userId);
	
	
}
