package com.talk.app.sms.web;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.talk.app.sms.service.SmsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SmsController {
    
    private final SmsService smsService;
    
    @PostMapping("/send-success-sms")
    public ResponseEntity<String> sendSuccessSms(@RequestBody Map<String, String> payload) {
        String phoneNumber = payload.get("phoneNumber");
        return smsService.sendSuccessSms(phoneNumber);
    }

    @PostMapping("/send-failure-sms")
    public ResponseEntity<String> sendFailureSms(@RequestBody Map<String, String> payload) {
        String phoneNumber = payload.get("phoneNumber");
        return smsService.sendFailureSms(phoneNumber);
    }
}
