package com.talk.app.chatbot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// 클라이언트가 메시지를 받을 수 있는 간단한 브로커를 활성화
        // "/topic", "/queue" 경로로 메시지를 구독할 수 있게 한다.
		config.enableSimpleBroker("/topic", "/queue");
		// 도착경로에 대한 prefix를 설정 => /app 이라 설정하면 /topic/hello 라는 토픽에 대해 구독 신청했을때 실제 경로는 /app/topic/hello 가 된다.
		config.setApplicationDestinationPrefixes("/app");
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 연결할 소켓 엔드포인트 지정하는 코드.  
		registry.addEndpoint("/chatbot-websocket")
//				.setAllowedOriginPatterns("*") // cors 허용을 위해 꼭 설정해주어야 한다.
		        .withSockJS(); //웹소켓을 지원하지 않는 브라우저는 sockJS를 사용하도록 한다.;
	}
}