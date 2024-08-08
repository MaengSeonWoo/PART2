package com.talk.app.qna.mapper;

import java.util.List;

import com.talk.app.qna.service.qnaVO;

public interface QnAMapper {
	// 전체조회
	public List<qnaVO> selectQnAAll();
	
	// 단건조회
	public qnaVO selectQnAInfo(qnaVO qnavo);
	
	// 등록 
	public int insertQnAInfo(qnaVO qnavo);
	
	// 수정 
	public int updateQnAInfo(qnaVO qnavo);
	
	// 삭제 : 조건 - no
	public int deleteQnAInfo(int qnaNo);
	
}
