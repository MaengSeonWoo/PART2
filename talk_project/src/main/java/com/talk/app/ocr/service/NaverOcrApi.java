package com.talk.app.ocr.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NaverOcrApi {
    @Value("${naver.service.url}")
    private String url;
    
    // 해당 단어들을 제외하고 추출
    private static final Set<String> FILTER_WORDS = new HashSet<>(Arrays.asList());
    /**
     * 네이버 OCR API를 호출한다
     * @param type 호출 메서드 타입
     * @param filePath 파일 경로
     * @param naver_secretKey 네이버 시크릿키 값
     * @param ext 확장자
     * @return 추출된 텍스트 리스트
     */
    public List<String> callApi(String type, String filePath, String naver_secretKey, String ext) {
        String apiURL = url;
        String secretKey = naver_secretKey;
        String imageFile = filePath;
        List<String> parseData = null;

        log.info("callApi Start!");

        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod(type);
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", ext);
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.put(image);  // 수정된 부분
            json.put("images", images);
            String postParams = json.toString();

            con.connect();
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                File file = new File(imageFile);
                writeMultiPart(wr, postParams, file, boundary);
            }

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            parseData = jsonparse(response);

        } catch (Exception e) {
            log.error("Error occurred while calling OCR API", e);
        }
        return parseData;
    }

    /**
     * 멀티파트 데이터를 작성한다
     * @param out 데이터를 출력할 OutputStream
     * @param jsonMessage 요청 파라미터
     * @param file 요청 파일
     * @param boundary 경계 문자열
     */
    private static void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(jsonMessage);
        sb.append("\r\n");

        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();

        if (file != null && file.isFile()) {
            out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
            StringBuilder fileString = new StringBuilder();
            fileString.append("Content-Disposition:form-data; name=\"file\"; filename=\"");
            fileString.append(file.getName()).append("\"\r\n");
            fileString.append("Content-Type: application/octet-stream\r\n\r\n");
            out.write(fileString.toString().getBytes("UTF-8"));
            out.flush();

            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes());
            }

            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        }
        out.flush();
    }

    /**
     * JSON 응답을 파싱하여 텍스트 리스트를 반환한다
     * @param response 응답 값
     * @return 텍스트 리스트
     */
    private static List<String> jsonparse(StringBuilder response) {
        List<String> result = new ArrayList<>();

        try {
            // JSON 파싱
            JSONObject jobj = new JSONObject(response.toString());

            // images 배열을 추출
            JSONArray jsonArray = jobj.getJSONArray("images");
            JSONObject jsonObjImage = jsonArray.getJSONObject(0);
            JSONArray fields = jsonObjImage.getJSONArray("fields");

            // fields 배열에서 inferText 추출
            for (int i = 0; i < fields.length(); i++) {
                JSONObject field = fields.getJSONObject(i);
                String text = field.optString("inferText", "");
                
                // 필터링할 단어가 포함되지 않은 경우에만 리스트에 추가
                if (!FILTER_WORDS.contains(text)) {
                    result.add(text);
                }
            }

        } catch (Exception e) {
            log.error("JSON 응답 파싱 중 오류 발생", e);
        }

        return result;
    }
}
