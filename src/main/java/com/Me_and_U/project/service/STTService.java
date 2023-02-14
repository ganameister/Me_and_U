package com.Me_and_U.project.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.util.Date;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class STTService {
	
	public String stt(String filePathName) {
		String result="";
		
		String clientId = "ffi3xseu1j";
		String clientSecret = "Z8HSryH1tIsZES1U1fytQQwxHcSWGlOnon1Hnxed"; 

		
		/////////String clientId="";배포용 꼭 지우기!!!!!
		///////String clientSecret= "";  
		
		
		
        try {
           String imgFile= filePathName;        	
            File voiceFile = new File(imgFile);

            String language = "Kor";       
            String apiURL = "https://naveropenapi.apigw.ntruss.com/recog/v1/stt?lang=" + language;
            URL url = new URL(apiURL);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

            OutputStream outputStream = conn.getOutputStream();
            FileInputStream inputStream = new FileInputStream(voiceFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            BufferedReader br = null;
            int responseCode = conn.getResponseCode();
            if(responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  // 오류 발생
                System.out.println("error!!!!!!! responseCode= " + responseCode);
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }
            String inputLine;

            if(br != null) {
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString()); //
                result=jsonToString(response.toString());  //보냄 text 로                
                System.out.println(result); //결과출력
            } else {
                System.out.println("error !!!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
	}
	
	public String jsonToString(String jsonResultStr) {
		String result= new JSONObject(jsonResultStr).getString("text");
		
		return result;
	}
		

	
}
