package com.Me_and_U.project.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ChatbotService {
	
	
	public  String chatbotMultimain(String voiceMessage) {
		
		String apiURL="https://nmzvabfpfh.apigw.ntruss.com/custom/v1/8990/8608212384a68070ccf89d42cb7b9841d443f2005392e728b57d4032041cbaa5";
		String secretKey= "U2h5WnRGUW9PYXNXdkJ6RGpFS2NxbHdRc2VCZnpjRU8="; 
		
		/////////String apiURL="";배포용 꼭 지우기!!!!!
		///////String secretKey= ""; 
		
		
        String chatbotMessage = "";  //응답 메세지

        try {
            

            URL url = new URL(apiURL);

            String message = getReqMessage(voiceMessage);
            System.out.println("##" + message);

            String encodeBase64String = makeSignature(message, secretKey);

            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json;UTF-8");
            con.setRequestProperty("X-NCP-CHATBOT_SIGNATURE", encodeBase64String);

            // post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(message.getBytes("UTF-8"));
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();

            BufferedReader br;

            if(responseCode==200) { // Normal call
                System.out.println(con.getResponseMessage());

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                con.getInputStream()));
                String decodedString;
                while ((decodedString = in.readLine()) != null) {
                    chatbotMessage = decodedString;  //서버로부터 결과 받은 메세지  chatbotMessage(응답메세지)
                }
                
                in.close();                
               
                System.out.println("응답메세지:"+chatbotMessage);
               
            } else {  // Error occurred
                chatbotMessage = con.getResponseMessage();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return chatbotMessage;
    }
    public  String makeSignature(String message, String secretKey) {

        String encodeBase64String = "";

        try {
            byte[] secrete_key_bytes = secretKey.getBytes("UTF-8");

            SecretKeySpec signingKey = new SecretKeySpec(secrete_key_bytes, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
            //encodeBase64String = Base64.encodeToString(rawHmac, Base64.NO_WRAP);
            encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);

            return encodeBase64String;

        } catch (Exception e){
            System.out.println(e);
        }
        return encodeBase64String;

    }

    public  String getReqMessage(String voiceMessage) {

        String requestBody = "";

        try {

            JSONObject obj = new JSONObject();

            long timestamp = new Date().getTime();

            System.out.println("##"+timestamp);

            obj.put("version", "v2");
            obj.put("userId", "U47b00b58c90f8e47428af8b7bddc1231heo2");
            obj.put("timestamp", timestamp);

            JSONObject bubbles_obj = new JSONObject();

            bubbles_obj.put("type", "text");

            JSONObject data_obj = new JSONObject();
            data_obj.put("description", voiceMessage);

            bubbles_obj.put("type", "text");
            bubbles_obj.put("data", data_obj);

            JSONArray bubbles_array = new JSONArray();
            bubbles_array.put(bubbles_obj);

            obj.put("bubbles", bubbles_array);
             
            if(voiceMessage == "") {
                obj.put("event", "open"); 
            } else {
                obj.put("event", "send"); //질문
            }
                                    
            requestBody = obj.toString();

        } catch (Exception e){
            System.out.println("## Exception : " + e);
        }
        return requestBody;
    }
   
    public String jsonToString(String jsonResultStr) {
    	
    	JSONArray bubbleArray = new JSONObject(jsonResultStr).getJSONArray("bubbles");    	
    	String result = bubbleArray.getJSONObject(0).getJSONObject("data").getString("description");  
    	   	
    	return result;
    }

}
