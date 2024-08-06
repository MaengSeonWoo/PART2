package com.talk.app.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.talk.app.admin.service.WelfareVO;

@Mapper
public interface WelfareMapper {
	//아이디만 입력
//	public int insertServId(WelfareVO servId);
	
	//리스트받기
	public List<WelfareVO> getAllWelfareInfo();
	
	//데이터 전체 입력
	public int insertWelfareInfo(WelfareVO vo);
	
	
	
}
