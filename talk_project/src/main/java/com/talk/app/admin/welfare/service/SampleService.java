package com.talk.app.admin.welfare.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.talk.app.admin.welfare.mapper.WelfareMapper;
import com.talk.app.admin.welfare.vo.WelfareVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SampleService {
	@Autowired
    private WelfareMapper mapper;

	  @Value("${serviceKey}")     // 보안을 위해 application.properties에 저장해둠
	    String serviceKey;
	  
    //@Transactional 
    public String fetchAndSaveServId() throws Exception {
        String response = callapihttp();
        // XML 파싱
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
        Document doc = null;
		try {
			doc = builder.parse(new InputSource(new StringReader(response)));
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}

        NodeList nodeList = doc.getElementsByTagName("servId");
        for (int i = 0; i < nodeList.getLength(); i++) {
            String servId = nodeList.item(i).getTextContent();

            // servId DB 저장
            WelfareVO vo = new WelfareVO();
            vo.setServId(servId);

            int rowsAffected = mapper.insertServId(vo); 

            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully: " + servId);
            } else {
                System.out.println("Data insertion failed for: " + servId);
            }
        }
		return response;
    }


    public void fetchAndUpdateDetailInfo() throws Exception {
        List<WelfareVO> servIdList = mapper.getAllServId();

        for (WelfareVO info : servIdList) {
            String servId = info.getServId();
            String response = getDetailedInfo(servId);

             //XML 파싱
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response)));

             //필요한 필드 추출
            String servName = doc.getElementsByTagName("servNm").item(0).getTextContent();
            String startDate = doc.getElementsByTagName("enfcBgngYmd").item(0).getTextContent();
            String endDate = doc.getElementsByTagName("enfcEndYmd").item(0).getTextContent();
            String bizDept = doc.getElementsByTagName("bizChrDeptNm").item(0).getTextContent();
            String sido = doc.getElementsByTagName("ctpvNm").item(0).getTextContent();
            String sgg = doc.getElementsByTagName("sggNm").item(0).getTextContent();
            String servSummary = doc.getElementsByTagName("servDgst").item(0).getTextContent();
            String household = doc.getElementsByTagName("trgterIndvdlNmArray").item(0).getTextContent();
            String likeSubject = doc.getElementsByTagName("intrsThemaNmArray").item(0).getTextContent();
            String supPeriod = doc.getElementsByTagName("sprtCycNm").item(0).getTextContent();
            String provType = doc.getElementsByTagName("srvPvsnNm").item(0).getTextContent();
            String appWay = doc.getElementsByTagName("aplyMtdCn").item(0).getTextContent();
            String supTarget = doc.getElementsByTagName("sprtTrgtCn").item(0).getTextContent();
            String selStandard = doc.getElementsByTagName("slctCritCn").item(0).getTextContent();
            String salServ = doc.getElementsByTagName("alwServCn").item(0).getTextContent();

             //상세 정보 DB 업데이트
            WelfareVO detailInfo = new WelfareVO();
            detailInfo.setServId(servId);
            detailInfo.setServName(servName);
            detailInfo.setStartDate(startDate);
            detailInfo.setEndDate(endDate);
            detailInfo.setBizDept(bizDept);
            detailInfo.setSido(sido);
            detailInfo.setSgg(sgg);
            detailInfo.setServSummary(servSummary);
            detailInfo.setHousehold(household);
            detailInfo.setLikeSubject(likeSubject);
            detailInfo.setSupPeriod(supPeriod);
            detailInfo.setProvType(provType);
            detailInfo.setAppWay(appWay);
            detailInfo.setSupTarget(supTarget);
            detailInfo.setSelStandard(selStandard);
            detailInfo.setSalServ(salServ);

            mapper.updateDetailInfo(detailInfo);
            
        }
    }

    //상세 데이터 수집
    private String getDetailedInfo(String servId) throws Exception {
//        String key = "odp3R%2BAnv93%2BqG0hMhsxznQIF589DFV7I%2BJbKgPbJu2h86CikqZnQN0weoWc9r1FZqDwWOL3YsDVzXFd%2BvX7%2Bw%3D%3D";   //실제 API 키로 변경 필요
        String urlStr = "https://apis.data.go.kr/B554287/LocalGovernmentWelfareInformations/LcgvWelfaredetailed?serviceKey=" + URLEncoder.encode(serviceKey, "UTF-8") + "&servId=" + URLEncoder.encode(servId, "UTF-8");
        URI uri = new URI(urlStr);
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

    private String callapihttp() throws Exception {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6410000/busrouteservice/getBusRouteInfoItem"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + "odp3R%2BAnv93%2BqG0hMhsxznQIF589DFV7I%2BJbKgPbJu2h86CikqZnQN0weoWc9r1FZqDwWOL3YsDVzXFd%2BvX7%2Bw%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1285", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("lifeArray","UTF-8") + "=" + URLEncoder.encode("006", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("arrgOrd","UTF-8") + "=" + URLEncoder.encode("001", "UTF-8"));
        
        URI uri = new URI(urlBuilder.toString());  
        URL url = uri.toURL();
        
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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


