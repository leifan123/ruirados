package com.ruirados.util;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

public class WebUtil {

    public static void output(String content, HttpServletResponse response, String contentType) {
        if (contentType.equals(WebConstants.ContentType_JSON)) {
            outputJson(content, response);
        } else if (contentType.equals(WebConstants.ContentType_XML)) {
            outputXml(content, response);
        } else {
            response.setContentType(contentType);
            output(content, response);
        }
    }

    public static void outputPlainText(String content, HttpServletResponse response) {
        response.setContentType(WebConstants.ContentType_PLAINTEXT);
        output(content, response);
    }

    public static void outputXml(String content, HttpServletResponse response) {
        response.setContentType(WebConstants.ContentType_XML);
        output(content, response);
    }

    public static void outputJson(String content, HttpServletResponse response) {
        response.setContentType(WebConstants.ContentType_JSON);
        output(content, response);
    }

    public static void output(String content, HttpServletResponse response) {
        OutputStream out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            out = response.getOutputStream();
            response.setContentLength(content.getBytes("UTF-8").length);
            out.write(content.getBytes("UTF-8"));
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(WebUtil.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
            }
        }
    }
    
    /**
     * URL处理
     * @param url
     * @return
     */
    public static String UrlNorm(String url){
    	return url.replaceAll("[+]", "%2B");
    	
    }
    
    /**
     * 获取三个月的日期
     * @return
     */
    public static String getSgyStr(){
    	
    	Date dNow = new Date(); //当前时间
    	Date dBefore = new Date();
    	Calendar calendar = Calendar.getInstance(); //得到日历
    	calendar.setTime(dNow);//把当前时间赋给日历
    	calendar.add(Calendar.MONTH, -3); //设置为前3月
    	dBefore = calendar.getTime(); //得到前3月的时间

    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
    	String defaultStartDate = sdf.format(dBefore); //格式化前3月的时间

    	 return defaultStartDate;
    	
    }
    
  
}
