package com.talk.app.chatbot.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.talk.app.chatbot.mapper.FAQMapper;
import com.talk.app.chatbot.service.ChatbotService;
import com.talk.app.chatbot.service.FAQVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatbotServiceImpl implements ChatbotService {

    private final FAQMapper faqMapper;

    @Override
    public String getResponse(String message) {
        // 사용자가 입력한 메시지에서 키워드를 추출
        List<String> extractedKeywords = extractKeywordsFromMessage(message);

        // 추출된 키워드 리스트를 순회하면서 각 키워드로 FAQ를 조회
        for (String keyword : extractedKeywords) {
            // 키워드 리스트에서 각 키워드를 이용해 FAQ 항목을 검색
            List<FAQVO> faqs = faqMapper.findByKeywords(Arrays.asList(keyword));

            // 검색된 FAQ 항목이 존재하는 경우
            if (!faqs.isEmpty()) {
                // 첫 번째 FAQ 항목의 답변을 반환
                return faqs.get(0).getAnswer();
            }
        }

        // 키워드가 매칭되지 않거나 FAQ가 없는 경우 기본 응답을 반환
        return "죄송합니다, 이해하지 못했습니다.";
    }

    // 메시지에서 키워드를 추출하는 메서드
    private List<String> extractKeywordsFromMessage(String message) {
        // 모든 FAQ 항목을 가져와 키워드 리스트를 생성
        // FAQ 항목의 키워드 필드를 쉼표(,)로 분리하여 각 키워드를 리스트에 추가
        List<FAQVO> allFAQs = faqMapper.findAll();
        List<String> keywords = allFAQs.stream()
                // 각 FAQ의 키워드를 쉼표로 분리하여 스트림 생성
                .flatMap(faq -> Arrays.stream(faq.getKeyword().split(",")))
                // 키워드 앞뒤 공백을 제거
                .map(String::trim)
                // 중복된 키워드 제거
                .distinct()
                // 키워드 리스트로 변환
                .collect(Collectors.toList());

        // 입력된 메시지를 소문자로 변환하여 처리 (대소문자 구분 없이 검색)
        String lowerMessage = message.toLowerCase();

        // 키워드 리스트에서 메시지에 포함된 키워드만 추출
        return keywords.stream()
                // 메시지에 키워드가 포함되어 있는지 확인
                .filter(keyword -> lowerMessage.contains(keyword.toLowerCase()))
                // 키워드 리스트로 반환
                .collect(Collectors.toList());
    }
}