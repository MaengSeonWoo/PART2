package com.talk.app.QnA.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.QnA.mapper.QnAMapper;
import com.talk.app.QnA.service.QnAService;
import com.talk.app.QnA.service.QnAVO;

@Service
public class QnAServiceImpl implements QnAService {
	
	private QnAMapper qnaMapper;
	
	@Autowired
	QnAServiceImpl(QnAMapper qnaMapper){
		this.qnaMapper = qnaMapper;
	}
	
	@Override
	public List<QnAVO> qnaList() {
		return qnaMapper.selectQnAAll();
	}

	@Override
	public QnAVO qnaInfo(QnAVO qnaVO) {
		return qnaMapper.selectQnAInfo(qnaVO);
	}

	@Override
	public int insertQnA(QnAVO qnaVO) {
		return qnaMapper.insertQnAInfo(qnaVO);
	}

	@Override
	public Map<String, Object> updateQnA(QnAVO qnaVO) {
		Map<String, Object> map = new HashMap<>();
		boolean bo = false;
		
		int result = qnaMapper.updateQnAInfo(qnaVO);
		if(result == 1) {
			bo = true;
		}
		
		map.put("result", bo);
		map.put("target", qnaVO);
		return map;
	}

	@Override
	public int deleteQnA(QnAVO qnaVO) {
		return qnaMapper.deleteQnAInfo(qnaVO);
	}

	
}
