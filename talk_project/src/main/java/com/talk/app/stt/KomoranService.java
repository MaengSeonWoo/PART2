package com.talk.app.stt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

@Service
public class KomoranService {
	public List<String> analyzeText(String document) {
	    Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
	    String[] words = document.split(" ");
	    List<String> result = new ArrayList<>();
	    for (String word : words) {
	        KomoranResult analyzeResultList = komoran.analyze(word);
	        List<Token> tokenList = analyzeResultList.getTokenList();
	        for (Token token : tokenList) {
	            String pos = token.getPos();
	            if (pos.equals("NNG") || pos.equals("NNP")) {
	                result.add(token.getMorph());
	            }
	        }
	    }
	    return result;
	}
}
	
	
