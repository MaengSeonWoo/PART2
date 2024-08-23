package com.talk.app.admember.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.talk.app.QnA.service.qnaVO;
import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;
import com.talk.app.posting.service.PostingVO;

@Mapper
public interface MemberMapper {

	//기업신청목록
	public List<CoUserVO> approveAll();
	//기업상세목록
	public CoUserVO approveDetail(CoUserVO vo);
	//회원목록
	public List<UserVO> userAll();
	//기업회원 승인
	public int updateCo(int coUserNo);
	//기업회원 미승인 목록
	public List<CoUserVO> couserApprove();
	//채용 미승인 목록
	public List<PostingVO> postingApprove();
	//일반회원 미답변목록
	public List<qnaVO> userAnswer();
	//기업회원 미답변목록
	public List<qnaVO> coAnswer();
	//채용전체
	public List<PostingVO> postList();
	//채용상세
	public PostingVO postDetail(PostingVO vo);
	//채용승인
	public int postUpdate(int postingNo);
	//채용거절
	public int postRefuse(int postingNo);
	//문자보낼 인원수
	public int sendCount(UserVO uvo);
	
}
