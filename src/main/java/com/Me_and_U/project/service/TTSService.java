package com.Me_and_U.project.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Service;

@Service
public class TTSService {
	public String chatbotTTS(String message) {

		String clientId = "ffi3xseu1j";
		String clientSecret = "Z8HSryH1tIsZES1U1fytQQwxHcSWGlOnon1Hnxed"; 

    
	/////////String clientId="";배포용 꼭 지우기!!!!!
	///////String clientSecret= ""; 
    
    
    String voiceFileName = ""; 
    
    try {
    	
        String text = URLEncoder.encode(message, "UTF-8"); // 13자    	
    	
        String apiURL = "https://naveropenapi.apigw.ntruss.com/tts-premium/v1/tts";
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
        con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
        // post request
        String postParams = "speaker=nara&volume=0&speed=0&pitch=0&format=mp3&text=" + text;
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postParams);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        BufferedReader br;
        if(responseCode==200) { // 정상 호출
            InputStream is = con.getInputStream();
            int read = 0;
            byte[] bytes = new byte[1024];
            // 랜덤한 이름으로 mp3 파일 생성
            String tempname = Long.valueOf(new Date().getTime()).toString();
            //File f = new File(tempname + ".mp3"); // 저장되는 파일명 (기본 위치 확인 : 프로젝트 루트)
            //File f = new File("C:/springWorkspace/upload/tts_" + tempname + ".mp3"); // 파일 저장 경로 변경
            // 음성 파일명 
            voiceFileName = "tts_" + tempname + ".mp3";
            File f = new File("C:/springWorkspace/upload/" + voiceFileName);///
            f.createNewFile();
            OutputStream outputStream = new FileOutputStream(f);
	            while ((read =is.read(bytes)) != -1) {
	                outputStream.write(bytes, 0, read);
	            } 
	            is.close(); // 파일 저장 완료
	        } else {  // 오류 발생
	            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	            while ((inputLine = br.readLine()) != null) {
	                response.append(inputLine);
	            }
	            br.close();
	            System.out.println(response.toString());
	        }
	    } catch (Exception e) {
	        System.out.println(e);
	    }    
	    return voiceFileName; // 저장된 음성 파일명 반환
	}
	
	
}
