/**
 * 
 */
package com.ruirados.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
 
/*
 * 利用HttpClient进行post请求的工具类
 */
public class HttpRequest {
	
	
	/**
	 * Get Request
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url) throws Exception {
		URL localURL = new URL(url);
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setRequestProperty("Accept-Encoding", "");
		httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
		
		//httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");
		//httpURLConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		if (httpURLConnection.getResponseCode() >= 300) {
			throw new Exception(
					"HTTP Request is not success, Response code is "
							+ httpURLConnection.getResponseCode());
		}

		try {
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}

		} finally {

			if (reader != null) {
				reader.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}

		}
		return resultBuffer.toString();
	}

	/**
	 * Post request
	 * @param url
	 * @param json
	 * @return
	 */
	public static String doPost(String url,String json){
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try{
			httpClient = new DefaultHttpClient();
			
			httpPost = new HttpPost(url);
			//设置参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
		 
			params.add(new BasicNameValuePair("param",json));
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"utf-8");

			httpPost.setEntity(entity);
			
			HttpResponse response = httpClient.execute(httpPost);
			 
			if(response.getStatusLine().getStatusCode() == 200 ){
					HttpEntity entry = response.getEntity();
					result = EntityUtils.toString(entry,"utf-8");
					//System.out.println(res);
			}else{
				throw new RuntimeException("http error :" +response.getStatusLine().getStatusCode());
			}
			 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return result;
	}
	public static void main(String[] args) {
		
		String url = "http://192.168.3.130:8080/ruicloud/device/queryNetPric";
		try {
			//System.out.println(HttpRequest.doGet(url));
			 System.out.println(HttpRequest.doPost(url,"1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
	 