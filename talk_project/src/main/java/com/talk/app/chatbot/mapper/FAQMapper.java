package com.talk.app.chatbot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.talk.app.chatbot.service.FAQVO;

public interface FAQMapper {
	@Select("SELECT * FROM FAQ WHERE faq_id = #{faqId}")
    FAQVO findById(Long faqId);

	
	List<FAQVO> findFAQByStepAndPreviousStatus(@Param("stepNo") int stepNo, @Param("preNo") int preNo);
	
	FAQVO findFAQByKeyword(@Param("keyword") String keyword);
//	@Select("SELECT * FROM FAQ WHERE keyword LIKE '%' || #{keyword} || '%'")
//	List<FAQVO> findByKeyword(String keyword);
	
//	@Select({
//	    "<script>",
//	    "SELECT * FROM FAQ WHERE",
//	    "<foreach collection='keywords' item='keyword' separator=' OR '>",
//	    "keyword LIKE '%' || #{keyword} || '%'",
//	    "</foreach>",
//	    "</script>"
//	})
//	List<FAQVO> findByKeywords(@Param("keywords") List<String> keywords);
//    @Select("SELECT * FROM FAQ")
//    List<FAQVO> findAll();

	 // 키워드로 FAQ 항목을 조회
    List<FAQVO> findByKeywords(@Param("keywords") String keywords);
//    List<FAQVO> findByKeywords(@Param("keywords") List<String> keywords);

    // 모든 FAQ 항목을 조회
    List<FAQVO> findAll();
	
    void insertFAQ(FAQVO faq);

    void updateFAQ(FAQVO faq);

    void deleteFAQ(Long faqId);
}
