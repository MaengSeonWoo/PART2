package com.talk.app.chatbot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.talk.app.chatbot.service.Step;

public interface StepsMapper {
	Step getStepById(@Param("stepId") int stepId);
    List<Step> getStepsByQuestionId(@Param("questionId") int questionId);
}
