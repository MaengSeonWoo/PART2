package com.talk.app.calendar.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.talk.app.admin.service.WelfareVO;
import com.talk.app.common.service.SearchVO;

@Mapper
public interface CalendarMapper {
//	public List<WelfareVO> selectAll(Criteria cri);
	
	public int coutAll(SearchVO vo);
	
	public WelfareVO selectInfo(WelfareVO vo);
	
	public List<WelfareVO> categoryData(SearchVO vo);
	
	public List<Map<String,Object>> calendar(SearchVO vo);

}
