package com.talk.app.qnaReply.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.qnaReply.mapper.QnAReplyMapper;
import com.talk.app.qnaReply.service.QnAReplyService;
import com.talk.app.qnaReply.service.QnAReplyVO;

@Service
public class QnAReplyServiceImpl implements QnAReplyService {
	
	private QnAReplyMapper replyMapper;
	
	@Autowired
	QnAReplyServiceImpl(QnAReplyMapper replyMapper){
		this.replyMapper = replyMapper;
	}
	
	@Override
	public QnAReplyVO replyInfo(QnAReplyVO replyVO) {
		return replyMapper.selectQnAInfo(replyVO);
	}

	@Override
	public int insertReply(QnAReplyVO replyVO) {
		int result = replyMapper.insertReplyInfo(replyVO);
		return result == 1 ? replyVO.getQnaNo() : -1;
	}

	@Override
	public Map<String, Object> updateReploy(QnAReplyVO replyVO) {
		Map<String, Object> map = new HashMap<>();
		boolean is = false;
		
		int result = replyMapper.updateReplyInfo(replyVO);
		if(result == 1) {
			is = true;
		}
		
		map.put("result", is);
		map.put("target", replyVO);
		return map;
	}

	@Override
	public int deleteReply(int replyVO) {
		return replyMapper.deleteReplyInfo(replyVO);
	}

	@Override
	public List<QnAReplyVO> replyList() {
		return replyMapper.replyList();
	}

}
