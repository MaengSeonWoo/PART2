package com.talk.app.tts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TtsController {
	/*
	 * @Autowired private TextToSpeechClient textToSpeechClient;
	 * 
	 * public byte[] generateSpeech(String text, String voice) throws Exception {
	 * SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();
	 * 
	 * VoiceSelectionParams voiceParams =
	 * VoiceSelectionParams.newBuilder().setLanguageCode("en-US").setName(voice).
	 * build();
	 * 
	 * AudioConfig audioConfig =
	 * AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();
	 * 
	 * SynthesizeSpeechResponse response =
	 * textToSpeechClient.synthesizeSpeech(input, voiceParams, audioConfig);
	 * 
	 * return response.getAudioContent().toByteArray(); }
	 */
}
