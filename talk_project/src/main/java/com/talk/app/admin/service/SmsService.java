package com.talk.app.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.talk.app.admember.mapper.MemberMapper;
import com.talk.app.admin.mapper.WelfareMapper;

import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
public class SmsService {
		
	  private final DefaultMessageService messageService;
//	  private final WelfareMapper mapper;
	  
	  @Autowired
	  WelfareMapper mapper;

	    public SmsService(
	            @Value("${coolsms.api_key}") String apiKey,
	            @Value("${coolsms.api_secret}") String apiSecret,
	            MemberMapper memberMapper) {
	        this.messageService = net.nurigo.sdk.NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
	        this.mapper = mapper;
	    }

	    public void sendSmsToEligibleMembers(UserWelfareVO vo) {
	        // MyBatis를 사용하여 특정 조건에 맞는 회원 조회
	        List<UserWelfareVO> eligibleMembers = mapper.sendMsg(vo);

	        for (UserWelfareVO member : eligibleMembers) {
	            Message message = new Message();
	            message.setFrom("01012345678"); // 발신번호
	            message.setTo(member.getTel()); // 수신번호
	            message.setText(member.getServSummary()); // 메시지 내용

	            try {
	                SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
	                System.out.println("문자 전송 성공: " + response.getMessageId());
	            } catch (Exception e) {
	                System.out.println("문자 전송 실패: " + e.getMessage());
	            }
	        }
	    }
}
