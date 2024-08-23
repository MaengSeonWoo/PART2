
package com.talk.app.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.admin.mapper.WelfareMapper;
import com.talk.app.admin.service.UserWelfareVO;
import com.talk.app.admin.service.WelfareService;
import com.talk.app.admin.service.WelfareVO;
import com.talk.app.login.service.UserVO;

@Service
public class WelfareServiceImpl implements WelfareService{

	@Autowired
	private WelfareMapper mapper;

	@Override
	public List<WelfareVO> welfareList() {
		return mapper.getAllWelfareInfo();
	}

	
	@Override
	public int welfareInsert(WelfareVO vo) {
		return mapper.insertWelfareInfo(vo);
	}


	@Override
	public int welfareInsert2(WelfareVO vo) {
		int result = mapper.insertWelfareDetail(vo);
		return result == 1 ? vo.getWid() : -1;
	}


	@Override
	public WelfareVO welfareDetail(WelfareVO vo) {
		return mapper.selectDetail(vo);
	}


	@Override
	public Map<String, Object> welfareUpdate(WelfareVO vo) {
		Map<String,Object> map = new HashMap<String, Object>();
		boolean isSuccessed =false;
		
		int result = mapper.updateWelfare(vo);
		
		if(result ==1) {
			isSuccessed = true;
		}
		
		map.put("result", isSuccessed);
		map.put("target", vo );
		return map;
	}


	@Override
	public int welfareDelete(int wid) {
		return mapper.deleteWelfare(wid);
	}


	@Override
	public List<UserWelfareVO> userMsg(UserWelfareVO vo) {
		return mapper.sendMsg(vo);
	}


	@Override
	public int welfareDetail2(int wid) {
		int result = mapper.selectDetail2(wid);
		return result;
	}


	@Override
	public List<UserWelfareVO> msgResult(UserWelfareVO vo, int wid) {
		List<UserWelfareVO> result = mapper.sendMsg2(vo, wid);
		return result;
	}


	@Override
	public int sendCount(UserVO vo, int wid) { 
		return mapper.sendCount(vo, wid);
	}


	@Override
	public UserVO userDetail(UserVO vo) {
		return mapper.userDetail(vo);
	}


	
	

}




