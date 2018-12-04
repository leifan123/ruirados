package com.ruirados.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpUtil {
    //private static final String RESPONSE_JSON = "&response=json";
    private static final String RESPONSE_JSON = "";

    public static String encodeUtf8(String server, String cmd) throws UnsupportedEncodingException {

        return server + "?" + cmd + RESPONSE_JSON;
    }

    public static String encodedSignature(List<String> parameterNames, Map<String, String> requestParameters,
                                          String apiKey, String secretKey) throws UnsupportedEncodingException {

        //String encodedCmd = URLEncoder.encode(cmd, "UTF-8");
        String requestToSign = null;
        String encodedApiKey = URLEncoder.encode(apiKey, "UTF-8");

        parameterNames.add("apikey");
        requestParameters.put("apikey", encodedApiKey);

        Collections.sort(parameterNames);

        for (String paramName : parameterNames) {
            String paramValue = requestParameters.get(paramName);

            if (requestToSign == null) {
                requestToSign = paramName + "=" + URLEncoder.encode(paramValue, "UTF-8").replaceAll("\\+", "%20");
            } else {
                requestToSign = requestToSign + "&" + paramName + "=" + URLEncoder.encode(paramValue, "UTF-8").replaceAll("\\+", "%20");
            }

        }

        requestToSign = requestToSign.toLowerCase();
        String signature = HttpUtil.signRequest(requestToSign, secretKey);

        return URLEncoder.encode(signature, "UTF-8");


    }

    public static String encodeUtf8(String server, String cmd, String apiKey, String secretKey) throws UnsupportedEncodingException {

        Map<String, String> requestParameters = new HashMap<String, String>();
        List<String> parameterNames = new ArrayList<String>();
        String[] commands = cmd.split("&");

        for (String command : commands) {
            String[] items = command.split("=");
            parameterNames.add(items[0]);
            requestParameters.put(items[0], items[1]);
        }

        String encodedApiKey = URLEncoder.encode(apiKey, "UTF-8");
        String encodedSignature = encodedSignature(parameterNames, requestParameters, apiKey, secretKey);

        String url = server + "?" + cmd + RESPONSE_JSON + "&apikey=" + encodedApiKey + "&signature=" + encodedSignature;

        return url;


    }

    public static String encodeUtf8ali(String server, String cmd, String apiKey, String secretKey) throws UnsupportedEncodingException {

        Map<String, String> requestParameters = new HashMap<String, String>();
        List<String> parameterNames = new ArrayList<String>();
        String[] commands = cmd.split("&");

        for (String command : commands) {
            String[] items = command.split("=");
            parameterNames.add(items[0]);
            requestParameters.put(items[0], items[1]);
        }

        String encodedApiKey = URLEncoder.encode(apiKey, "UTF-8");
        //String encodedSignature = encodedSignature(parameterNames, requestParameters, apiKey, secretKey);

        String url = server + "?" + cmd + RESPONSE_JSON + "&signature=" + secretKey;

        return url;


    }
    
    
    //发送http请求  方式：get
    public static HashMap<String, InputStream> sendHttpRequest(String url) throws UnsupportedEncodingException {
         //返回执行后的网站的状态吗：  比如200执行成功, 404未找到页面  等等
        int responseCode = 0;
        InputStream is = null;
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        HashMap<String, InputStream> response = new HashMap<String, InputStream>();
        try {
        	//执行url命令，并返回执行后的状态码
            responseCode = client.executeMethod(method);
             //将执行后服务器返回的内容以InputStream 得到
            is = method.getResponseBodyAsStream();
             //判断网站返回的状态码是否为200 ，即执行成功    HttpStatus.SC_OK 的值为   200
            if (responseCode == HttpStatus.SC_OK) {
                response.put("ok", is);
                return response;

            } else {
                //System.out.println("error msg :"+getStringFromInputStream(is));
            }
        } catch (HttpException e) {
            return null;
        } catch (IOException e) {
            return null;

        }
        //  判断服务器端是否出错： 状态码为500     HttpStatus.SC_INTERNAL_SERVER_ERROR的值为500
        if (responseCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
            Map<String, String> errorInfo = XmlUtil.getSingleValueFromXML(is,
                    new String[]{"errorcode", "description"});
        }
        response.put("error", is);
        return response;
    }

    public static HashMap<String, InputStream> sendHttpRequestWithAuthorization(String url, String userName, String password) {

        int responseCode = 0;
        InputStream is = null;
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        byte[] encodePassword = (userName + ":" + password).getBytes();
        BASE64Encoder encoder = new BASE64Encoder();
        method.addRequestHeader("Authorization", "Basic " + encoder.encode(encodePassword));
        
        HashMap<String, InputStream> response = new HashMap<String, InputStream>();
        try {
            responseCode = client.executeMethod(method);
            is = method.getResponseBodyAsStream();
            if (responseCode == HttpStatus.SC_OK) {
                response.put("ok", is);
                return response;

            } else {
                //System.out.println("error msg :"+getStringFromInputStream(is));
            }
        } catch (HttpException e) {
            return null;
        } catch (IOException e) {
            return null;

        }

        if (responseCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
            Map<String, String> errorInfo = XmlUtil.getSingleValueFromXML(is,
                    new String[]{"errorcode", "description"});
        }
        response.put("error", is);
        return response;
    }

    public static String queryAsyncJobResult(String cmd) {

        InputStream is = null;
        String jobStatus = null;
        String result = null;

        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(cmd);
        while (true) {
            try {
                client.executeMethod(method);
                //System.out.println("Query job url " + resultUrl);
                is = method.getResponseBodyAsStream();
                result = HttpUtil.getStringFromInputStream(is);
                Map<String, String> values = XmlUtil.getSingleValueFromXML(result, new String[]{"jobstatus"});
                jobStatus = values.get("jobstatus");
                if (jobStatus.equalsIgnoreCase(JobStatus.PENGDING.getStatus())) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    break;
                }

            } catch (Exception ex) {
                return null;
            }
        }

        return jobStatus;

    }

    // convert InputStream to String
    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        return sb.toString();

    }


    public static String signRequest(String request, String key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            mac.init(keySpec);
            mac.update(request.getBytes());
            byte[] encryptedBytes = mac.doFinal();
            return Base64.encodeBytes(encryptedBytes);
        } catch (Exception ex) {
        }
        return null;
    }
}
