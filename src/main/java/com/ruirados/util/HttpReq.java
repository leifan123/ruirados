package com.ruirados.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;

import com.ruirados.pojo.RspData;
 
 

public class HttpReq {
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        String urlNameString="";
        try {
        	if(param==null||param.isEmpty()){
        		  urlNameString = url ;
        	}else{
        		  urlNameString = url + "?" + param;
        	}
           
            
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("contentType", "utf-8");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
       /*     for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            //System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        //System.out.println(result);
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
        	//System.out.println("url="+url);
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            //out.print(param);
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            //System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        //System.out.println("-->"+result);
        return result;
    }    
    
    /**
     * 获取IB数据
     * @param model
     * @param func
     * @param map
     * @return
     */
    public static RspData  getIbMsg (String model, String func, Map<String, String> map){
    	 
    	String  ContentPath = Prop.getInstance().getPropertiesByPro("cloudStack.properties", "ib.server");
	
		String res = HttpReq.sendGet(ContentPath + model+"/"+func, FcUtil.map2Param(map));
		RspData resJson = null;
		resJson = JSONUtils.getRspDataByJson(res);
		//System.out.println("res===>"+res);
		/*try {
			resJson = JSONUtils.getRspDataByJson(new String(res.getBytes(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resJson = JSONUtils.getRspDataByJson(res);
		}*/
    	
		return  resJson;
    }
    
    
    /**
     * 获取IB数据
     * @param model
     * @param func
     * @param map
     * @return
     */
    public static RspData  postIbMsg (String model, String func, Map<String, String> map,Map<String, String> phoneMessage){
    	 
    	String  ContentPath = Prop.getInstance().getPropertiesByPro("ruirados.properties", "ib.server");
    	String res="";
    	if(phoneMessage==null){
    		 res = HttpReq.sendPost(ContentPath + model+"/"+func, FcUtil.map2Param(map));
    		
    	}
    	else{
    		 res = HttpReq.sendPost(ContentPath + model+"/"+func, FcUtil.map2Param(map)+"&map="+JSONUtils.createObjectJson(phoneMessage));
    	}
		
		
		RspData resJson = JSONUtils.getRspDataByJson(res);
    	
		return  resJson;
    }
   
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public void sendPostReq(String url, String param) {
    
    	
    	HttpClient httpClient = new HttpClient();  
    	String timeOut = Prop.getInstance().getPropertiesByPro("cloudStack.properties", "httpClient.timeOut");
    	httpClient.setConnectionTimeout(Integer.parseInt(timeOut));
    	httpClient.setTimeout(Integer.parseInt(timeOut));
        PostMethod post = new PostMethod(url);  
        post.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");  
        post.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");  
        post.setRequestHeader("Accept-Language","zh-cn,zh;q=0.8");  
        post.setRequestHeader("Accept-Encoding","gzip, deflate, sdch");  
        post.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");  
        //post.setRequestHeader("Host","");  
        post.setRequestHeader("Connection","keep-alive");  
        //post.setRequestHeader("Referer","");  
        RequestEntity entity;
		try {
			entity = new StringRequestEntity(param, "text/html", "utf-8");
	        post.setRequestEntity(entity);  
	        httpClient.executeMethod(post);  
	        String html = post.getResponseBodyAsString();  
	        //System.out.println(html);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    	
    }    
    
    public static void main(String[] args) {
       /* //发送 GET 请求
        String s=HttpRequest.sendGet("http://daiyulong000.oicp.net:51975/ibserver/comperService/validatePw", "email=wangxuguang&password=123456");
        System.out.println(s);*/
        
        //发送 POST 请求
    	/*Map m = new LinkedHashMap();
		
		m.put("vpcid", "4d8aec68-5ec3-41b9-baa2-9c80c86aa07d1");

		try {
			String url = CloudStackHelper.associateIpAddress(m);
			 
			System.out.println(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
   /* 	HttpReq hr = new HttpReq();
     	Map<String,String> param_map = new HashMap<String, String>();
     	Map<String,String> where_param = new HashMap<String, String>();
     	where_param.put("starttime", "2016-09-11 17:42:45");
     	where_param.put("endtime", "2016-11-11 17:42:45");
     	where_param.put("isRead", "0");
    	param_map.put("companyId", "148714318211");
    	param_map.put("where", JSONUtils.createObjectJson(where_param));
    	param_map.put("rows", "4");
    	param_map.put("page", "1");
	     String requestResult=   HttpReq.sendPost("http://localhost:8080/ruicloud/information/destroyVirtualMachine.do", "{\"virtualMachineid\":\"2a7ed3ef-b1a5-483f-97f7-f998b3672499\"}");*/
	     //System.out.println(requestResult);
    	//System.out.println(ss);
    
    }
}