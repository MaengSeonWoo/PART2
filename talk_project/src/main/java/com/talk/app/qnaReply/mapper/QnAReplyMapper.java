package com.talk.app.qnaReply.mapper;

import java.util.List;

import com.talk.app.qnaReply.service.QnAReplyVO;

public interface QnAReplyMapper  {
	
	public List<QnAReplyVO> replyList(); 
	
	// 단건조회
	public QnAReplyVO selectQnAInfo(QnAReplyVO replyVO);
	
	// 등록 
	public int insertReplyInfo(QnAReplyVO replyVO);
	
	// 수정 
	public int updateReplyInfo(QnAReplyVO replyVO);
	
	// 삭제 : 조건 - no
	public int deleteReplyInfo(int replyNo);
}
