package com.talk.app.admember.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.admember.mapper.MemberMapper;
import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberMapper mapper;
	

	@Override
	public List<CoUserVO> approveAll() {
		return mapper.approveAll();
	}

	@Override
	public List<UserVO> userAll() {
		return mapper.userAll();
	}

	@Override
	public CoUserVO coDetail(CoUserVO vo) {
		return mapper.approveDetail(vo);
	}

	@Override
	public int coUpdate(int coUserNo) {
		return mapper.updateCo(coUserNo);
	}


	




}
