package com.talk.app.notice.service.iml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.app.notice.mapper.NoticeMapper;
import com.talk.app.notice.service.NoticeService;
import com.talk.app.notice.service.NoticeVO;

@Service
public class NoticeServiceImpl implements NoticeService {

	private NoticeMapper noticeMapper;
	
	@Autowired
	NoticeServiceImpl(NoticeMapper noticeMapper){
		this.noticeMapper = noticeMapper;
	}
	
	@Override
	public List<NoticeVO> noticeList() {
		return noticeMapper.selectNoticeAll();
	}

	@Override
	public NoticeVO noticeInfo(NoticeVO noticeVO) {
		return noticeMapper.selectNoticeInfo(noticeVO);
	}

	@Override
	public int insertNotice(NoticeVO noticeVO) {
		int result = noticeMapper.insertNoticeInfo(noticeVO);
		
		return result == 1 ? noticeVO.getNoticeNo() : -1;
	}

	@Override
	public Map<String, Object> updateNotice(NoticeVO noticeVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		
		int result = noticeMapper.updateNoticeInfo(noticeVO);
		if(result == 1) {
			isSuccessed = true;
		}
		
		map.put("result", isSuccessed);
		map.put("target", noticeVO);
		return map;
	}

	@Override
	public int deleteNotice(int noticeVO) {
		return noticeMapper.deleteNoticeInfo(noticeVO);
	}

	@Override
	public int plusViewCnt(int viewCnt) {
		return noticeMapper.plusViewCnt(viewCnt);
	}


}
