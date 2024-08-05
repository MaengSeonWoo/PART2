package com.talk.app.admin.welfare.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.talk.app.admin.welfare.vo.WelfareVO;

@Mapper
public interface WelfareMapper {
	//�븘�씠�뵒留� �엯�젰
	public int insertServId(WelfareVO servId);
	
	//由ъ뒪�듃諛쏄린
	public List<WelfareVO> getAllServId();
	
	//�뜲�씠�꽣 �쟾泥� �엯�젰
	public int updateDetailInfo(WelfareVO vo);
	
	
	
}
