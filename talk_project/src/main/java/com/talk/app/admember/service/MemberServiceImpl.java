package com.talk.app.admember.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.QnA.service.qnaVO;
import com.talk.app.admember.mapper.MemberMapper;
import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;
import com.talk.app.posting.service.PostingVO;

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

	@Override
	public List<CoUserVO> couserApprove() {
		return mapper.couserApprove();
	}

	@Override
	public List<PostingVO> postingApprove() {
		return mapper.postingApprove();
	}

	@Override
	public List<qnaVO> userAnswer() {
		return mapper.userAnswer();
	}

	@Override
	public List<qnaVO> coAnswer() {
		return mapper.coAnswer();
	}

	@Override
	public List<PostingVO> postAll() {
		return mapper.postList();
	}

	@Override
	public PostingVO postdetail(PostingVO vo) {
		return mapper.postDetail(vo);
	}

	@Override
	public int postUpdate(int postingNo) {
		return mapper.postUpdate(postingNo);
	}

	@Override
	public int sendCount(UserVO vo) {
		return mapper.sendCount(vo);
	}


	




}
