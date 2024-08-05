package com.talk.app.admin.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.talk.app.admin.mapper.WelfareMapper;

@Service
public class SampleService {

	@Autowired
    private WelfareMapper mapper;
    
    private static final Logger logger = LoggerFactory.getLogger(SampleService.class);
    public SampleService(WelfareMapper mapper) {
        this.mapper = mapper;
    }
    
    @Transactional
    public void fetchAndSaveWelfareData() throws Exception {
        logger.info("Fetching and saving welfare data");

        String response = callApi();

        // XML 파싱
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(response)));

        NodeList servIdList = doc.getElementsByTagName("servId");
        for (int i = 0; i < servIdList.getLength(); i++) {
            if (servIdList.item(i) != null) {
                String servId = servIdList.item(i).getTextContent();
                System.out.println("ServId: " + servId);
                // 이후 servId 값을 사용해 데이터를 삽입하는 로직
            } else {
                System.out.println("servId tag not found or is null");
            }
        }
        NodeList servNameList = doc.getElementsByTagName("servNm");
        for (int i = 0; i < servNameList.getLength(); i++) {
            if (servNameList.item(i) != null) {
                String servName = servNameList.item(i).getTextContent();
                System.out.println("ServName: " + servName);
            } else {
                System.out.println("servNm tag not found or is null");
            }
        }
//        NodeList startDateList = doc.getElementsByTagName("enfcBgngYmd");
//        NodeList endDateList = doc.getElementsByTagName("enfcEndYmd");
        NodeList bizDeptList = doc.getElementsByTagName("bizChrDeptNm");
        for (int i = 0; i < bizDeptList.getLength(); i++) {
            if (bizDeptList.item(i) != null) {
                String bizDept = bizDeptList.item(i).getTextContent();
                System.out.println("BizDept: " + bizDept);
            } else {
                System.out.println("bizChrDeptNm tag not found or is null");
            }
        }
        NodeList sidoList = doc.getElementsByTagName("ctpvNm");
        for (int i = 0; i < sidoList.getLength(); i++) {
            if (sidoList.item(i) != null) {
                String sido = sidoList.item(i).getTextContent();
                System.out.println("Sido: " + sido);
            } else {
                System.out.println("ctpvNm tag not found or is null");
            }
        }
//        NodeList sggList = doc.getElementsByTagName("sggNm");
        NodeList servSummaryList = doc.getElementsByTagName("servDgst");
        for (int i = 0; i < servSummaryList.getLength(); i++) {
            if (servSummaryList.item(i) != null) {
                String servSummary = servSummaryList.item(i).getTextContent();
                System.out.println("ServSummary: " + servSummary);
            } else {
                System.out.println("servDgst tag not found or is null");
            }
        }

//        NodeList householdList = doc.getElementsByTagName("trgterIndvdlNmArray");
        NodeList likeSubjectList = doc.getElementsByTagName("intrsThemaNmArray");
        for (int i = 0; i < likeSubjectList.getLength(); i++) {
            if (likeSubjectList.item(i) != null) {
                String likeSubject = likeSubjectList.item(i).getTextContent();
                System.out.println("LikeSubject: " + likeSubject);
            } else {
                System.out.println("intrsThemaNmArray tag not found or is null");
            }
        }
        NodeList supPeriodList = doc.getElementsByTagName("sprtCycNm");
        for (int i = 0; i < supPeriodList.getLength(); i++) {
            if (supPeriodList.item(i) != null) {
                String supPeriod = supPeriodList.item(i).getTextContent();
                System.out.println("SupPeriod: " + supPeriod);
            } else {
                System.out.println("sprtCycNm tag not found or is null");
            }
        }
        NodeList provTypeList = doc.getElementsByTagName("srvPvsnNm");
        for (int i = 0; i < provTypeList.getLength(); i++) {
            if (provTypeList.item(i) != null) {
                String provType = provTypeList.item(i).getTextContent();
                System.out.println("ProvType: " + provType);
            } else {
                System.out.println("srvPvsnNm tag not found or is null");
            }
        }
//        NodeList appWayList = doc.getElementsByTagName("aplyMtdCn");
//        NodeList supTargetList = doc.getElementsByTagName("sprtTrgtCn");
//        NodeList selStandardList = doc.getElementsByTagName("slctCritCn");
//        NodeList salServList = doc.getElementsByTagName("alwServCn");

        for (int i = 0; i < servIdList.getLength(); i++) {
            WelfareVO vo = new WelfareVO();
            vo.setServId(servIdList.item(i).getTextContent());
            vo.setServName(servNameList.item(i).getTextContent());
//            vo.setStartDate(startDateList.item(i).getTextContent());
//            vo.setEndDate(endDateList.item(i).getTextContent());
            vo.setBizDept(bizDeptList.item(i).getTextContent());
            vo.setSido(sidoList.item(i).getTextContent());
//            vo.setSgg(sggList.item(i).getTextContent());
            vo.setServSummary(servSummaryList.item(i).getTextContent());
//            vo.setHousehold(householdList.item(i).getTextContent());
            vo.setLikeSubject(likeSubjectList.item(i).getTextContent());
            vo.setSupPeriod(supPeriodList.item(i).getTextContent());
            vo.setProvType(provTypeList.item(i).getTextContent());
//            vo.setAppWay(appWayList.item(i).getTextContent());
//            vo.setSupTarget(supTargetList.item(i).getTextContent());
//            vo.setSelStandard(selStandardList.item(i).getTextContent());
//            vo.setSalServ(salServList.item(i).getTextContent());

            System.out.println("Preparing to insert data for: " + vo.getServId());
            int rowsAffected = mapper.insertWelfareInfo(vo);
            System.out.println("Rows affected: " + rowsAffected);


            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully: " + vo.getServId());
            } else {
                System.out.println("Data insertion failed for: " + vo.getServId());
            }
        }
    }

    private String callApi() throws Exception {
        String key = "odp3R%2BAnv93%2BqG0hMhsxznQIF589DFV7I%2BJbKgPbJu2h86CikqZnQN0weoWc9r1FZqDwWOL3YsDVzXFd%2BvX7%2Bw%3D%3D";  // 실제 API 키로 변경
        StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B554287/LocalGovernmentWelfareInformations/LcgvWelfarelist");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + key);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1285", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("lifeArray","UTF-8") + "=" + URLEncoder.encode("006", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("arrgOrd","UTF-8") + "=" + URLEncoder.encode("001", "UTF-8"));
        
        URI uri = new URI(urlBuilder.toString());
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        return sb.toString();
    }
}
