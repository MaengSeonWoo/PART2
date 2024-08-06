package com.talk.app.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.talk.app.admin.service.WelfareVO;

@Mapper
public interface WelfareMapper {
	//아이디만 입력
//	public int insertServId(WelfareVO servId);
	
	//데이터 전체 입력(크롤링)
	public int insertWelfareInfo(WelfareVO vo);

	//리스트받기
	public List<WelfareVO> getAllWelfareInfo();
	
	//상세
	public WelfareVO selectDetail(WelfareVO vo);

	//데이터 전체 입력
	public int insertWelfareDetail(WelfareVO vo);
	
	public int updateWelfare(WelfareVO vo);
	
	public int deleteWelfare(int wid);
	
}
