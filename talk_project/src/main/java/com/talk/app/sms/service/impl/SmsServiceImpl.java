package com.talk.app.sms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.talk.app.sms.service.SmsService;

import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
public class SmsServiceImpl implements SmsService {
    private final DefaultMessageService messageService;

    @Autowired
    public SmsServiceImpl(DefaultMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public ResponseEntity<String> sendSuccessSms(String to) {
        return sendSms(to, "축하합니다! 합격을 알립니다.");
    }

    @Override
    public ResponseEntity<String> sendFailureSms(String to) {
        return sendSms(to, "안타깝지만, 불합격을 알립니다.");
    }

    private ResponseEntity<String> sendSms(String to, String text) {
        Message message = new Message();
        message.setFrom("01075728258");
        message.setTo(to);
        message.setText(text);

        try {
            SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
            return ResponseEntity.ok("{\"message\": \"문자 발송 성공: " + response.getMessageId() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("문자 발송 실패: " + e.getMessage());
        }
    }
}
