package com.talk.app.admin.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.talk.app.admin.mapper.WelfareMapper;

@Service
public class SampleService {

    @Value("${serviceKey}")
    private String key;

    @Autowired
    private WelfareMapper mapper;

    @Transactional
    public void fetchAndSaveWelfareData() throws Exception {
        String response = callApi();

        // XML 파싱
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(response)));

        NodeList servIdList = doc.getElementsByTagName("servId");
        NodeList servNameList = doc.getElementsByTagName("servNm");
        NodeList supPeriodList = doc.getElementsByTagName("sprtCycNm");
        NodeList provTypeList = doc.getElementsByTagName("srvPvsnNm");
        NodeList bizDeptList = doc.getElementsByTagName("bizChrDeptNm");
        NodeList sidoList = doc.getElementsByTagName("ctpvNm");
        NodeList servSummaryList = doc.getElementsByTagName("servDgst");
        NodeList likeSubjectList = doc.getElementsByTagName("intrsThemaNmArray");
        NodeList startDateList = doc.getElementsByTagName("enfcBgngYmd");
        NodeList endDateList = doc.getElementsByTagName("enfcEndYmd");
        NodeList sggList = doc.getElementsByTagName("sggNm");
        NodeList householdList = doc.getElementsByTagName("trgterIndvdlNmArray");
        NodeList appWayList = doc.getElementsByTagName("aplyMtdCn");
        NodeList supTargetList = doc.getElementsByTagName("sprtTrgtCn");
        NodeList selStandardList = doc.getElementsByTagName("slctCritCn");
        NodeList salServList = doc.getElementsByTagName("alwServCn");

        for (int i = 0; i < servIdList.getLength(); i++) {
            WelfareVO vo = new WelfareVO();
            
            vo.setServId(getTextContent(servIdList, i));
            vo.setServName(getTextContent(servNameList, i));
            vo.setSupPeriod(getTextContent(supPeriodList, i));
            vo.setProvType(getTextContent(provTypeList, i));
            vo.setBizDept(getTextContent(bizDeptList, i));
            vo.setSido(getTextContent(sidoList, i));
            vo.setServSummary(getTextContent(servSummaryList, i));
            vo.setLikeSubject(getTextContent(likeSubjectList, i));
            vo.setStartDate(getTextContent(startDateList, i));
            vo.setEndDate(getTextContent(endDateList, i));
            vo.setSgg(getTextContent(sggList, i));
            vo.setHousehold(getTextContent(householdList, i));
            vo.setAppWay(getTextContent(appWayList, i));
            vo.setSupTarget(getTextContent(supTargetList, i));
            vo.setSelStandard(getTextContent(selStandardList, i));
            vo.setSalServ(getTextContent(salServList, i));

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

    private String getTextContent(NodeList nodeList, int index) {
        return nodeList.item(index) != null ? nodeList.item(index).getTextContent() : null;
    }
    
    
    public void fetchAndUpdateWelfareDetails() throws Exception {
        List<String> servIdList = mapper.getAllServIds();

        for (String servId : servIdList) {
            String response = callApiForDetails(servId);

            // XML 파싱
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response)));

            // 태그 가져오기
            String startDate = getTextContent1(doc.getElementsByTagName("enfcBgngYmd"), 0);
            String endDate = getTextContent1(doc.getElementsByTagName("enfcEndYmd"), 0);
            String appWay = getTextContent1(doc.getElementsByTagName("aplyMtdCn"), 0);
            String supTarget = getTextContent1(doc.getElementsByTagName("sprtTrgtCn"), 0);
            String salServ = getTextContent1(doc.getElementsByTagName("alwServCn"), 0);
            String selStandard = getTextContent1(doc.getElementsByTagName("slctCritCn"), 0);

            // 업데이트 수행
            mapper.updateWelfareDetails(servId, startDate, endDate, appWay, supTarget, salServ, selStandard);

            System.out.println("Updated details for: " + servId);
        }
    }

    private String callApiForDetails(String servId) throws Exception {
        StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B554287/LocalGovernmentWelfareInformations/LcgvWelfaredetailed");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + key);
        urlBuilder.append("&" + URLEncoder.encode("servId", "UTF-8") + "=" + URLEncoder.encode(servId, "UTF-8"));

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

    private String getTextContent1(NodeList nodeList, int index) {
        if (nodeList != null && nodeList.item(index) != null) {
            return nodeList.item(index).getTextContent();
        }
        return null;
    }
    
    
    
    
    
    
    
}
