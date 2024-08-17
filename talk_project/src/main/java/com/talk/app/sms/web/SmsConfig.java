package com.talk.app.sms.web;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {

    @Bean
    public DefaultMessageService defaultMessageService() {
        // API Key와 API Secret Key를 환경 변수나 설정 파일로부터 읽어오는 것이 좋습니다.
        String apiKey = ""; // 카톡에 저장된 apikey 사용
        String apiSecretKey = ""; // 카톡에 저장된 secretkey사용
        String apiUrl = ""; // 카톡에 저장됨

        return NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, apiUrl);
    }
}