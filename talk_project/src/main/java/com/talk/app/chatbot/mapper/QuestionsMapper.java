package com.talk.app.chatbot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.talk.app.chatbot.service.FAQVO;
import com.talk.app.chatbot.service.Question;

public interface QuestionsMapper {
	List<Question> getQuestionsByCategory(@Param("categoryName") String categoryName);
    Question getQuestionById(@Param("questionId") int questionId);

    // 키워드를 사용한 검색 메서드 추가
    List<FAQVO> findByKeywords(@Param("keyword") String keyword);
}
