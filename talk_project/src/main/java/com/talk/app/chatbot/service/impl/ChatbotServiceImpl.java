package com.talk.app.chatbot.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.talk.app.chatbot.mapper.FAQMapper;
import com.talk.app.chatbot.service.ChatMessage;
import com.talk.app.chatbot.service.ChatbotService;
import com.talk.app.chatbot.service.FAQVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatbotServiceImpl implements ChatbotService {
    private final FAQMapper faqMapper;
    private final ConcurrentHashMap<String, Integer> roomStepState = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> previousStatusState = new ConcurrentHashMap<>();

    @Override
    public void initializeRoom(String roomId) {
        roomStepState.put(roomId, 1); // 초기화 - 첫 번째 스텝부터 시작
        previousStatusState.put(roomId, -1); // 초기화 - 이전 상태가 없음
    }

    @Override
    public void endRoom(String roomId) {
        roomStepState.remove(roomId);
        previousStatusState.remove(roomId);
    }

    @Override
    public String processMessage(ChatMessage message) {
        String roomId = message.getRoomId();
        int currentQuestionId = message.getCurrentQuestionId();
        int previousStatusId = message.getPreviousQuestionId();
        String content = message.getContent();

        // content가 존재하면 바로 handleStepResponse 메서드로 처리
        if (content != null && !content.trim().isEmpty()) {
            return handleStepResponse(roomId, currentQuestionId, previousStatusId, content);
        }

        // 메시지 내용에 따른 로직 추가 가능
        switch (currentQuestionId) {
            case 1:
                return getInitialMenu(roomId);
            case 2:
            case 3:
            case 4:
            case 5:
                return handleStepResponse(roomId, currentQuestionId, previousStatusId, content);
            default:
                return "잘못된 입력입니다. 다시 시도해 주세요.";
        }
    }

    private String getInitialMenu(String roomId) {
        roomStepState.put(roomId, 1); // 초기 스텝 설정
        return "안녕하세요! 궁금한 내용을 선택해 주세요\n" +
               "<br><button class='chatbotBtn btn btn-primary' data-status='1'>1. 일반 FAQ (서비스, 로그인, 회원가입)</button><br>\n" +
               "<br><button class=\"chatbotBtn btn btn-primary\" data-status='2'>2. 채용 FAQ (채용공고, 이력서)</button><br>\n" +
               "<br><button class=\"chatbotBtn btn btn-primary\" data-status='3'>3. 복지제도 FAQ</button><br>\n" +
               "<br><button class=\"chatbotBtn btn btn-primary\" data-status='4'>4. 기타 FAQ</button>\n";
    }
    
 // 메시지에서 키워드를 추출하는 함수
    private List<String> extractKeywordsFromMessage(String message) {
        // 모든 FAQ 항목을 가져옴
        List<FAQVO> allFAQs = faqMapper.findAll();
        List<String> keywords = new ArrayList<>();
        
        // FAQ 항목에서 키워드를 추출
        for (FAQVO faq : allFAQs) {
            String[] keywordArray = faq.getKeyword().split(",");
            for (String keyword : keywordArray) {
                keyword = keyword.trim();
                if (!keywords.contains(keyword)) {
                    keywords.add(keyword);  // 중복을 방지하기 위해 리스트에 키워드 추가
                }
            }
        }

        // 메시지를 소문자로 변환
        String lowerMessage = message.toLowerCase();
        List<String> matchedKeywords = new ArrayList<>();

        // 메시지에 키워드가 포함되어 있는지 확인
        for (String keyword : keywords) {
            if (lowerMessage.contains(keyword.toLowerCase())) {
                matchedKeywords.add(keyword);
            }
        }

        return matchedKeywords;
    }

    private String handleStepResponse(String roomId, int currentQuestionId, int previousStatusId, String content) {
    	// 문장에서 키워드를 추출
        List<String> extractedKeywords = extractKeywordsFromMessage(content);
        
        // 추출된 키워드가 있는 경우 해당 키워드로 FAQ 검색
        for (String keyword : extractedKeywords) {
            List<FAQVO> faqs = faqMapper.findByKeywords(Arrays.asList(keyword));
            if (!faqs.isEmpty()) {
                FAQVO faq = faqs.get(0);
                roomStepState.put(roomId, faq.getStepNo()); // 현재 상태 업데이트
                previousStatusState.put(roomId, faq.getPreNo()); // 이전 상태 업데이트
                return faq.getAnswer(); // 매칭된 답변 반환
            }
        }
        // 키워드로 매칭되지 않으면 기존 방식으로 처리
        List<FAQVO> faqs = faqMapper.findFAQByStepAndPreviousStatus(currentQuestionId, previousStatusId);

        if (faqs == null || faqs.isEmpty()) {
            return "해당 질문에 대한 답변이 없습니다. 다시 시도해 주세요.";
        }

        // 여러 개의 답변이 있을 때, 모두 합쳐서 반환하거나 필요한 형태로 가공할 수 있음
        StringBuilder response = new StringBuilder();
        for (FAQVO faq : faqs) {
            roomStepState.put(roomId, faq.getStepNo()); // 현재 상태 업데이트
            previousStatusState.put(roomId, faq.getPreNo()); // 이전 상태 업데이트
            response.append(faq.getAnswer()).append("\n"); // 답변 추가
        }

        return response.toString(); // 합쳐진 답변 반환
    }
    
    
}