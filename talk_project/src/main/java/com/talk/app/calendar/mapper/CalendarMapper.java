package com.talk.app.calendar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.talk.app.admin.service.WelfareVO;

@Mapper
public interface CalendarMapper {
	public List<WelfareVO> selectCalendar();
	
	
}
