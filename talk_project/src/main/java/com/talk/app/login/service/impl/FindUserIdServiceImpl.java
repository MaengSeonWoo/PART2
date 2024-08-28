package com.talk.app.login.service.impl;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.talk.app.login.mapper.FindUserIdMapper;
import com.talk.app.login.service.FindUserIdService;
import com.talk.app.login.service.UserVO;

@Service
public class FindUserIdServiceImpl implements FindUserIdService{
	
	@Autowired
	private FindUserIdMapper findIdMapper;
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String findUserId(UserVO userVO) {
		// TODO Auto-generated method stub
		return findIdMapper.FindUserIdInfo(userVO);
	}

	@Override
	public boolean sendTempPw(UserVO userVO) {
		// 임시 비밀번호 생성
        String tempPassword = generateTemporaryPassword();
        
        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(tempPassword);
        
        // 이메일 전송
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(userVO.getEmail());
            message.setSubject("임시 비밀번호 발급 안내");
            message.setText("안녕하세요, " + userVO.getUserName() + "님.\n\n" +
                            "임시 비밀번호는 다음과 같습니다: " + tempPassword + "\n\n" +
                            "로그인 후 비밀번호를 변경해주시기 바랍니다.");
            mailSender.send(message);
            // 임시 비밀번호를 암호화하여 데이터베이스에 저장
            userVO.setUserPw(encryptedPassword); 
            findIdMapper.updateUserPassword(userVO);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 임시 비밀번호 생성 메서드
    private String generateTemporaryPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
}
