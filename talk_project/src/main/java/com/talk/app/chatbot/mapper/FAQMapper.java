package com.talk.app.chatbot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.talk.app.chatbot.service.FAQVO;

public interface FAQMapper {
	@Select("SELECT * FROM FAQ WHERE faq_id = #{faqId}")
    FAQVO findById(Long faqId);

	@Select("SELECT * FROM FAQ WHERE keyword LIKE '%' || #{keyword} || '%'")
    List<FAQVO> findByKeyword(String keyword);

    @Select("SELECT * FROM FAQ")
    List<FAQVO> findAll();

    void insertFAQ(FAQVO faq);

    void updateFAQ(FAQVO faq);

    void deleteFAQ(Long faqId);
}
