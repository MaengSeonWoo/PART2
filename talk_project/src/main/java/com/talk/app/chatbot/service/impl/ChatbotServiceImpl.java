package com.talk.app.chatbot.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

 // 상태를 저장하는 Map을 사용해 첫 대화를 추적 (세션 기반)
    Map<String, String> conversationState = new HashMap<>();

    @Override
    public String getResponse(String message, String roomId) {
        // 현재 대화 상태를 가져옴 (없으면 기본 상태로 설정)
        String currentState = conversationState.getOrDefault(roomId, "initial");

        // 처음 대화하는 사용자일 경우 기본 메뉴 출력
        if (currentState.equals("initial")) {
            conversationState.put(roomId, "menu"); // 메뉴 상태로 전환
            return getDefaultMenu();
        }

        // 사용자가 메뉴에서 선택한 경우에 대한 처리
        if (currentState.equals("menu")) {
            switch (message.trim()) {
                case "1":
                    conversationState.put(roomId, "general_faq");
                    return "일반 FAQ를 선택하셨습니다. 질문을 입력해 주세요.";
                case "2":
                    conversationState.put(roomId, "recruit_faq");
                    return "채용 FAQ를 선택하셨습니다. 질문을 입력해 주세요.";
                case "3":
                    conversationState.put(roomId, "benefits_faq");
                    return "복지제도 FAQ를 선택하셨습니다. 질문을 입력해 주세요.";
                case "4":
                    conversationState.put(roomId, "other_faq");
                    return "기타 FAQ를 선택하셨습니다. 질문을 입력해 주세요.";
                default:
                    return "올바른 선택이 아닙니다. 1에서 4 사이의 숫자를 입력해 주세요.";
            }
        }

        // 키워드 추출 후 FAQ 검색
        List<String> extractedKeywords = extractKeywordsFromMessage(message);
        for (String keyword : extractedKeywords) {
            List<FAQVO> faqs = faqMapper.findByKeywords(Arrays.asList(keyword));

            if (!faqs.isEmpty()) {
                FAQVO faq = faqs.get(0); // 첫 번째 FAQ 항목 선택

                // 긍정적인 응답일 경우 페이지 이동 처리
                if (isPositiveResponse(message)) {
                    conversationState.put(roomId, "navigating"); // 상태 변경
                    return "페이지로 이동합니다: " + faq.getPageUrl() + "\n" + faq.getPageDescription();
                }

                // 일반 FAQ 응답 처리
                return faq.getAnswer();
            }
        }

        return "죄송합니다, 이해하지 못했습니다.";
    }

    // 기본 메뉴 출력 함수
    private String getDefaultMenu() {
        return "안녕하세요! 아래에서 궁금한 내용을 선택해 주세요:\n" +
               "1. 일반 FAQ (서비스, 로그인, 회원가입)\n" +
               "2. 채용 FAQ (채용공고, 이력서)\n" +
               "3. 복지제도, 직업훈련영상 FAQ\n" +
               "4. 기타 FAQ";
    }

    // 메시지에서 긍정적인 응답을 확인하는 함수
    private boolean isPositiveResponse(String message) {
        List<String> positiveResponses = Arrays.asList("네", "그래", "예", "응", "ㅇㅇ");
        return positiveResponses.stream().anyMatch(response -> message.contains(response));
    }

    // 메시지에서 키워드를 추출하는 함수
    private List<String> extractKeywordsFromMessage(String message) {
        List<FAQVO> allFAQs = faqMapper.findAll();
        List<String> keywords = allFAQs.stream()
                .flatMap(faq -> Arrays.stream(faq.getKeyword().split(",")))
                .map(String::trim)
                .distinct()
                .collect(Collectors.toList());

        String lowerMessage = message.toLowerCase();
        return keywords.stream()
                .filter(keyword -> lowerMessage.contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}