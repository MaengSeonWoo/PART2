package com.talk.app.sms.service;

import org.springframework.http.ResponseEntity;

public interface SmsService {
    ResponseEntity<String> sendSuccessSms(String to);
    ResponseEntity<String> sendFailureSms(String to);
}
