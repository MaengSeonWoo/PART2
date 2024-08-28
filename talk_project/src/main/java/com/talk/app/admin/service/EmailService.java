package com.talk.app.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.talk.app.admember.mapper.MemberMapper;
import com.talk.app.login.service.CoUserVO;


@Service
public class EmailService {
	
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    MemberMapper mapper;
    
    public void sendFailEmail(CoUserVO vo) {
    	  try {
		    	SimpleMailMessage message = new SimpleMailMessage();
		    	message.setFrom("syj6180@naver.com");
		    	message.setTo(vo.getMgrEmail()); // 메일 수신자
		    	message.setText("죄송하지만 가입 승인이 거절되었습니다"+ "\n\n" +
		    							"자세한 문의는 053-1234-5678로 문의 주세요"); // 메일 본문 내용, HTML 여부
		    	message.setSubject("[손말톡톡] 가입신청 결과 메일입니다."); // 메일 제목
		    	mailSender.send(message);
		    	mapper.selectMailUser(vo);
		    	System.out.println("메일 전송 성공!!");
          } catch (Exception e) {
             e.printStackTrace();
         }
    }
    
    
    
    
    
    

}
