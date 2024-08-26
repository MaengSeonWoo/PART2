package com.talk.app.admember.service;

import java.util.List;

import com.talk.app.QnA.service.qnaVO;
import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;
import com.talk.app.posting.service.PostingVO;

public interface MemberService {
	//기업신청전체
	public List<CoUserVO> approveAll();
	//회원전체
	public List<UserVO> userAll();
	//상세
	public CoUserVO coDetail(CoUserVO vo);
	//승인시키기
	public int coUpdate(int coUserNo);
	//가입 거절
	public int coUserNo(int coUserNo);
	//기업회원 미승인 목록
	public List<CoUserVO> couserApprove();
	//채용 미승인 목록
	public List<PostingVO> postingApprove();
	//일반회원 미답변목록
	public List<qnaVO> userAnswer();
	//기업회원 미답변목록
	public List<qnaVO> coAnswer();
	//채용 전체
	public List<PostingVO> postAll();
	//채용상세
	public PostingVO postdetail(PostingVO vo);
	//채용승인
	public int postUpdate(int postingNo);
	//채용거절
	public int postRefuse(int postingNo);
	//문자보낼사람수
	public int sendCount(UserVO vo);
	
	
	
}
