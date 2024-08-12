package com.talk.app.video.service;

import lombok.Data;

@Data
public class VideoVO {
	private Integer videoNo; // 동영상번호
	private String videoTitle; // 동영상제목
	private String link; // 동영상주소
	private String linkFull; // html 처리 동영상 iframe
	private String thumbNail; // 썸네일 이미지 링크
	private String videoCategory; // 동영상카테고리
	
	/*
	 * private String fURL = "https://img.youtube.com/vi/"; private String
	 * modifyLink = link.split("v=")[1]; private String bURL = "0.jpg"; private
	 * String thumbnail = fURL + modifyLink + bURL;
	 */
}
