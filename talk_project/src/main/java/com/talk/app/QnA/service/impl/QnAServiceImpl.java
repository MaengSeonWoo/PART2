package com.talk.app.QnA.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.QnA.mapper.QnAMapper;
import com.talk.app.QnA.service.QnAService;
import com.talk.app.QnA.service.qnaVO;

@Service
public class QnAServiceImpl implements QnAService {
	
	private QnAMapper qnaMapper;
	
	@Autowired
	QnAServiceImpl(QnAMapper qnaMapper){
		this.qnaMapper = qnaMapper;
	}
	
	@Override
	public List<qnaVO> qnaList() {
		return qnaMapper.selectQnAAll();
	}

	@Override
	public qnaVO qnaInfo(qnaVO qnavo) {
		return qnaMapper.selectQnAInfo(qnavo);
	}

	@Override
	public int insertQnA(qnaVO qnavo) {
		int result = qnaMapper.insertQnAInfo(qnavo);
		return result == 1 ? qnavo.getQnaNo() : -1;
	}
	
	@Override
	public Map<String, Object> updateQnA(qnaVO qnavo) {
		Map<String, Object> map = new HashMap<>();
		boolean is = false;
		
		int result = qnaMapper.updateQnAInfo(qnavo);
		if(result == 1) {
			is = true;
		}
		
		map.put("result",is);
		map.put("target", qnavo);
		return map;
	}


	@Override
	public int deleteQnA(int qnavo) {
		return qnaMapper.deleteQnAInfo(qnavo);
	}

	
}
