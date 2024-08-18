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
    	String apiKey = "NCSQRKAPNVAMDRBB"; 
        String apiSecretKey = "UY6ZGS3NPUU6JKCWXDER6K96GXX5UNP6"; 
        String apiUrl = "https://api.coolsms.co.kr"; 

        return NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, apiUrl);
    }
}