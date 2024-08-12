package com.talk.app.admember.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.talk.app.login.service.CoUserVO;
import com.talk.app.login.service.UserVO;

@Mapper
public interface MemberMapper {

	public List<CoUserVO> approveAll();
	
	public List<UserVO> userAll();
	
}
