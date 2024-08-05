package com.talk.app.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.talk.app.admin.welfare.vo.WelfareVO;

@Mapper
public interface WelfareMapper {
	//占쎈툡占쎌뵠占쎈탵筌랃옙 占쎌뿯占쎌젾
	public int insertServId(WelfareVO servId);
	
	//�뵳�딅뮞占쎈뱜獄쏆룄由�
	public List<WelfareVO> getAllServId();
	
	//占쎈쑓占쎌뵠占쎄숲 占쎌읈筌ｏ옙 占쎌뿯占쎌젾
	public int updateDetailInfo(WelfareVO vo);
}
