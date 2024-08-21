package com.talk.app.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.talk.app.admin.service.UserWelfareVO;
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
	
	//상세2
	public int selectDetail2(int wid);
	
	//데이터 전체 입력
	public int insertWelfareDetail(WelfareVO vo);
	
	public int updateWelfare(WelfareVO vo);
	
	public int deleteWelfare(int wid);

	public int updateWelfareDetails(@Param("servId") String servId,
						            @Param("startDate") String startDate,
						            @Param("endDate") String endDate,
						            @Param("appWay") String appWay,
						            @Param("supTarget") String supTarget,
						            @Param("salServ") String salServ,
						            @Param("selStandard") String selStandard);

	public List<String> getAllServIds();
	
	public List<UserWelfareVO> sendMsg(UserWelfareVO vo);

	public List<UserWelfareVO> sendMsg2(int wid);
	
}
