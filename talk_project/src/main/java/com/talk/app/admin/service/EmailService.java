package com.talk.app.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {
	
    @Autowired
    private JavaMailSender mailSender;
    
    public void sendEmail(String toEmail, String subject, String body) {
    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setFrom("coolcat.yj@gmail.com");
    	message.setTo(toEmail); // 메일 수신자
    	message.setText(body); // 메일 본문 내용, HTML 여부
    	message.setSubject(subject); // 메일 제목
    	mailSender.send(message);
    	
    	System.out.println("메일 전송 성공!!");
    }
    

}
