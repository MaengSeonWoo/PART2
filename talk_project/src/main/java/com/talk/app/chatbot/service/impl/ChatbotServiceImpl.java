package com.talk.app.chatbot.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.talk.app.chatbot.mapper.FAQMapper;
import com.talk.app.chatbot.service.ChatbotService;
import com.talk.app.chatbot.service.FAQVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatbotServiceImpl implements ChatbotService{
	
	private final FAQMapper faqMapper;

	@Override
	public String getResponse(String message) {
		// 메시지에서 키워드를 추출하거나, 직접 검색
        String keyword = extractKeyword(message);

        // 키워드에 따라 적절한 FAQ를 찾음
        List<FAQVO> faqs = faqMapper.findByKeyword(keyword);

     // 모든 관련 답변을 합쳐서 반환
        if (!faqs.isEmpty()) {
            return faqs.stream()
                       .map(FAQVO::getAnswer)
                       .collect(Collectors.joining("\n"));
        }

        // 메뉴 또는 다른 데이터 소스에서 검색
        // ...

        // 기본 응답
        return "죄송합니다, 이해하지 못했습니다.";
    }

    private String extractKeyword(String message) {
        // 간단한 키워드 추출 로직 (예: 공백을 기준으로 분리)
        // 실제로는 좀 더 정교한 자연어 처리 로직이 필요할 수 있음
        return message.split(" ")[0];
    }
}
