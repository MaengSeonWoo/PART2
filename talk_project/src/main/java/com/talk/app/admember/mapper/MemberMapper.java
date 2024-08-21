package com.talk.app.admember.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;

@Mapper
public interface MemberMapper {

	//기업신청목록
	public List<CoUserVO> approveAll();
	//기업상세목록
	public CoUserVO approveDetail(CoUserVO vo);
	//회원목록
	public List<UserVO> userAll();
	
	public int updateCo(int coUserNo);
	
}
