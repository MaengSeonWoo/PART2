package com.talk.app.chatbot.service.impl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.talk.app.chatbot.mapper.FAQMapper;
import com.talk.app.chatbot.mapper.QuestionsMapper;
import com.talk.app.chatbot.mapper.StepsMapper;
import com.talk.app.chatbot.service.ChatMessage;
import com.talk.app.chatbot.service.ChatbotService;
import com.talk.app.chatbot.service.Question;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChatbotServiceImpl implements ChatbotService {
    private final QuestionsMapper questionsMapper;
    private final StepsMapper stepsMapper;
    private final FAQMapper faqMapper;
    private final ConcurrentHashMap<String, Integer> roomQuestionState = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> roomStepState = new ConcurrentHashMap<>();

    @Override
    public void initializeRoom(String roomId) {
        roomQuestionState.put(roomId, -1); // Initialize with no question selected
        roomStepState.put(roomId, -1); // Initialize with no step selected
    }

    @Override
    public void endRoom(String roomId) {
        roomQuestionState.remove(roomId);
        roomStepState.remove(roomId);
    }

    @Override
    public String processMessage(ChatMessage message) {
        // 특정 키워드가 포함된 경우 우선 처리
//        String keywordResponse = handleKeyword(message);
//        if (keywordResponse != null) {
//            return keywordResponse;
//        }
        String roomId = message.getRoomId();
        int currentQuestionId = message.getCurrentQuestionId();
//        int currentStepId = message.getCurrentStepId();

        if (currentQuestionId == 1) {
            return getInitialMenu(roomId);
        } else if (currentQuestionId == 2) {
            return handleCategorySelection(message.getContent(), roomId);
//        } else if (currentStepId == -1) {
//            return handleInitialQuestion(currentQuestionId, roomId);
        } else if (currentQuestionId == 3) {
        	return "";
        }else {
        	return "ddd";
        }
    }

    private String getInitialMenu(String roomId) {
        roomQuestionState.put(roomId, 0);
        return "안녕하세요! 아래에서 궁금한 내용을 선택해 주세요:\n" +
               "<br><button class='chatbotBtn' data-status='1-0'>1. 일반 FAQ (서비스, 로그인, 회원가입)</button><br>\n" +
               "<br><button class=\"chatbotBtn\" data-status='2-0'>2. 채용 FAQ (채용공고, 이력서)</button><br>\n" +
               "<br><button class=\"chatbotBtn\" data-status='3-0'>3. 복지제도, 직업훈련영상 FAQ</button><br>\n" +
               "<br><button class=\"chatbotBtn\" data-status='4-0'>4. 기타 FAQ</button>\n";
    }

    private String handleCategorySelection(String message, String roomId) {
        String categoryName = null;

        switch (message.trim().toLowerCase()) {
            case "1":
            case "일반":
            case "일반 faq":
                categoryName = "일반 FAQ";
                break;
            case "2":
            case "채용":
            case "채용 faq":
                categoryName = "채용 FAQ";
                break;
            case "3":
            case "복지제도":
            case "직업훈련영상":
            case "복지제도 faq":
            case "직업훈련영상 faq":
                categoryName = "복지제도, 직업훈련영상 FAQ";
                break;
            case "4":
            case "기타":
            case "기타 faq":
                categoryName = "기타 FAQ";
                break;
            default:
                return "올바른 선택이 아닙니다. 1에서 4 사이의 숫자 또는 해당 카테고리 이름을 입력해 주세요.";
        }

        List<Question> questions = questionsMapper.getQuestionsByCategory(categoryName);
        StringBuilder response = new StringBuilder(categoryName + "를 선택하셨습니다. 다음 중 선택해주세요:\n");
        int i =1;
        for (Question question : questions) {
            response.append("<br><button class='chatbotBtn' data-status='1-"+ i +"'>" + i+". " +question.getInitialQuestion()+"</button>").append("<br>");
            i++;
        }

        roomQuestionState.put(roomId, questions.get(0).getQuestionId());
        return response.toString();
    }

//    private String handleInitialQuestion(int currentQuestionId, String roomId) {
//        Question question = questionsMapper.getQuestionById(currentQuestionId);
//        roomStepState.put(roomId, question.getInitialStepId());
//        return question.getInitialQuestion();
//    }

//    private String handleStepResponse(String message, String roomId, int currentStepId) {
//        Step currentStep = stepsMapper.getStepById(currentStepId);
//        String response = currentStep.getStepText();
//
//        if (message.contains("네") || message.contains("그래") || message.contains("ㅇㅇ")) {
//            if (currentStep.getActionUrl() != null) {
//                return "페이지로 이동합니다: " + currentStep.getActionUrl();
//            }
//        }
//
//        if (currentStep.getNextStepId() != 0) {
//            roomStepState.put(roomId, currentStep.getNextStepId());
//        } else {
//            roomStepState.remove(roomId);
//        }
//
//        return response;
//    }

//    private String handleKeyword(String message) {
//        // 특정 키워드를 감지하여 즉시 응답 처리
//        List<FAQVO> faqs = faqMapper.findByKeywords(message.toLowerCase());
//        if (!faqs.isEmpty()) {
//            FAQVO faq = faqs.get(0);
//
//            // 페이지 이동이 필요한 경우
//            if (faq.getPageUrl() != null) {
//                return "페이지로 이동합니다: " + faq.getPageUrl() + "\n" + faq.getPageDescription();
//            }
//
//            // 일반 텍스트 응답
//            return faq.getAnswer();
//        }
//
//        return null; // 키워드에 해당하는 응답이 없을 경우
//    }
}
