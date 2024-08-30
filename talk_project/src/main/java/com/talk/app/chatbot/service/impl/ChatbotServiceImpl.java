package com.talk.app.chatbot.service.impl;

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
               "<br><button class=\"chatbotBtn btn btn-primary\" data-status='3'>3. 복지제도, 직업훈련영상 FAQ</button><br>\n" +
               "<br><button class=\"chatbotBtn btn btn-primary\" data-status='4'>4. 기타 FAQ</button>\n";
    }

    private String handleStepResponse(String roomId, int currentQuestionId, int previousStatusId, String content) {
        // 타이핑 입력이 있는 경우 해당 내용이 FAQ에서 찾을 수 있는지 확인
        if (content != null && !content.trim().isEmpty()) {
            FAQVO matchedFaq = faqMapper.findFAQByKeyword(content);
            if (matchedFaq != null) {
                roomStepState.put(roomId, matchedFaq.getStepNo()); // 현재 상태 업데이트
                previousStatusState.put(roomId, matchedFaq.getPreNo()); // 이전 상태 업데이트
                return matchedFaq.getAnswer(); // 매칭된 답변 반환
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