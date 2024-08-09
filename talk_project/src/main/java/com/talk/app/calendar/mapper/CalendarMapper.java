package com.talk.app.calendar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.talk.app.admin.service.WelfareVO;
import com.talk.app.common.service.Criteria;

@Mapper
public interface CalendarMapper {
	public List<WelfareVO> selectAll(Criteria cri);
	
	public int coutAll();
	
	public WelfareVO selectInfo(WelfareVO vo);
}
