/**
 * @author YuLong.Dai
 * @time Sep 18, 2016 4:16:11 PM
 * TODO
 */
package com.ruirados.utils;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
 



/**
 * 工具类
 * @author YuLong.Dai
 * @time Sep 18, 2016 4:17:01 PM
 */
public class FcUtil {
	
	private static final Log log = LogFactory.getLog(FcUtil.class);
	
	private static  byte[] defaultIV = { 49, 50, 51, 52, 53, 54, 55, 56 };
	
	private static  String DES_ALGORITHM = "DESede/CBC/PKCS5Padding";
	
	/**
	 * 字符串转数字
	 * @param str
	 * @return
	 */
	public static Integer str2Int(String str){
		 
			return str == null ? null: Integer.valueOf(str);
		  
	}
	
	/**
	 * map转 sql where条件
	 * @param map
	 * @return
	 */
	public static String map2Condition(Map<String, String> map){
		StringBuffer condition = new StringBuffer(" where ");
		
		for(Map.Entry<String, String> entry : map.entrySet()){
			String key = entry.getKey();
			Object value = entry.getValue();
			condition.append( key+"="+value.toString() +" and ");
		}
		return condition.substring(0, condition.lastIndexOf("and"));
	}
	

	/**
	 * 判斷字段是否為空
	 * @param field
	 * @return
	 */
	public static Boolean checkIsNull(String field){
		
		if(field == null || field.isEmpty()){
			return true;
		}
		return false; 
	}
	
	/**
	 * map转 url 条件
	 * @param map
	 * @return
	 */
	public static String map2Param(Map<String, String> map){
		
		StringBuffer condition = new StringBuffer("");
		 
		for (String key: map.keySet()) {
			condition.append(key+"="+map.get(key)+"&");	
		}
		
		String res = condition.substring(0,condition.length()-1);
		
		//System.out.println("param:"+res);
		return res;
	}
	
	/**
	 * 将时间转换为时间戳
	 * @param s
	 * @return
	 */
    public static Long dateToStamp(Date date){
        
		long ts = date.getTime();
		 
        return ts;
    }

    /* 
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    
    /* 
     * 百分比处理
     */
    public static int changePer(float s){
    	 
    	return Math.round(s*100);
    	   
    }
    	
    /**
	 * unicode转中文
	 * @param asciicode
	 * @return
	 */
	public static String ascii2native (String asciicode )
    {
        String[] asciis = asciicode.split ("\\\\u");
        String nativeValue = asciis[0];
        try
        {
            for ( int i = 1; i < asciis.length; i++ )
            {
                String code = asciis[i];
                nativeValue += (char) Integer.parseInt (code.substring (0, 4), 16);
                if (code.length () > 4)
                {
                    nativeValue += code.substring (4, code.length ());
                }
            }
        }
        catch (NumberFormatException e)
        {
            return asciicode;
        }
        return nativeValue;
    }
	
	
    public static Boolean IDCardValidate(String IDStr)   {          
    	Boolean tipInfo = true;// 记录错误信息  
        String Ai = "";  
        // 判断号码的长度 15位或18位  
        if (IDStr.length() != 15 && IDStr.length() != 18) {  
            
            return false;  
        }  
           
   
        // 18位身份证前17位位数字，如果是15位的身份证则所有号码都为数字  
        if (IDStr.length() == 18) {  
            Ai = IDStr.substring(0, 17);  
        } else if (IDStr.length() == 15) {  
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);  
        }  
        if (isNumeric(Ai) == false) {  
            
            return false;  
        }  
           
   
        // 判断出生年月是否有效   
        String strYear = Ai.substring(6, 10);// 年份  
        String strMonth = Ai.substring(10, 12);// 月份  
        String strDay = Ai.substring(12, 14);// 日期  
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {  
             
            return false;  
        }  
        GregorianCalendar gc = new GregorianCalendar();  
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");  
        try {  
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150  
                    || (gc.getTime().getTime() - s.parse(  
                            strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {  
                  
                return false;  
            }  
        } catch (NumberFormatException e) {  
            e.printStackTrace();  
        } catch (java.text.ParseException e) {  
            e.printStackTrace();  
        }  
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {  
            
            return false;  
        }  
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {  
           
            return false;  
        }  
           
   
        // 判断地区码是否有效   
        Hashtable areacode = GetAreaCode();  
        //如果身份证前两位的地区码不在Hashtable，则地区码有误  
        if (areacode.get(Ai.substring(0, 2)) == null) {  
           
            return false;  
        }  
           
        if(isVarifyCode(Ai,IDStr)==false){  
           
            return false;  
        }  
          
           
        return tipInfo;  
    }  
       
       
     /* 
      * 判断第18位校验码是否正确 
     * 第18位校验码的计算方式：  
        　　1. 对前17位数字本体码加权求和  
        　　公式为：S = Sum(Ai * Wi), i = 0, ... , 16  
        　　其中Ai表示第i个位置上的身份证号码数字值，Wi表示第i位置上的加权因子，其各位对应的值依次为： 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2  
        　　2. 用11对计算结果取模  
        　　Y = mod(S, 11)  
        　　3. 根据模的值得到对应的校验码  
        　　对应关系为：  
        　　 Y值：     0  1  2  3  4  5  6  7  8  9  10  
        　　校验码： 1  0  X  9  8  7  6  5  4  3   2 
     */  
    private static boolean isVarifyCode(String Ai,String IDStr) {  
         String[] VarifyCode = { "1", "0", "X", "9", "8", "7", "6", "5", "4","3", "2" };  
         String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7","9", "10", "5", "8", "4", "2" };  
         int sum = 0;  
         for (int i = 0; i < 17; i++) {  
            sum = sum + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);  
         }  
         int modValue = sum % 11;  
         String strVerifyCode = VarifyCode[modValue];  
         Ai = Ai + strVerifyCode;  
         if (IDStr.length() == 18) {  
             if (Ai.equals(IDStr) == false) {  
                 return false;  
                   
             }  
         }   
         return true;  
    }  
       
   
    /** 
     * 将所有地址编码保存在一个Hashtable中     
     * @return Hashtable 对象 
     */  
      
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static Hashtable GetAreaCode() {  
        Hashtable hashtable = new Hashtable();  
        hashtable.put("11", "北京");  
        hashtable.put("12", "天津");  
        hashtable.put("13", "河北");  
        hashtable.put("14", "山西");  
        hashtable.put("15", "内蒙古");  
        hashtable.put("21", "辽宁");  
        hashtable.put("22", "吉林");  
        hashtable.put("23", "黑龙江");  
        hashtable.put("31", "上海");  
        hashtable.put("32", "江苏");  
        hashtable.put("33", "浙江");  
        hashtable.put("34", "安徽");  
        hashtable.put("35", "福建");  
        hashtable.put("36", "江西");  
        hashtable.put("37", "山东");  
        hashtable.put("41", "河南");  
        hashtable.put("42", "湖北");  
        hashtable.put("43", "湖南");  
        hashtable.put("44", "广东");  
        hashtable.put("45", "广西");  
        hashtable.put("46", "海南");  
        hashtable.put("50", "重庆");  
        hashtable.put("51", "四川");  
        hashtable.put("52", "贵州");  
        hashtable.put("53", "云南");  
        hashtable.put("54", "西藏");  
        hashtable.put("61", "陕西");  
        hashtable.put("62", "甘肃");  
        hashtable.put("63", "青海");  
        hashtable.put("64", "宁夏");  
        hashtable.put("65", "新疆");  
        hashtable.put("71", "台湾");  
        hashtable.put("81", "香港");  
        hashtable.put("82", "澳门");  
        hashtable.put("91", "国外");  
        return hashtable;  
    }  
   
    /** 
     * 判断字符串是否为数字,0-9重复0次或者多次    
     * @param strnum 
     * @return 
     */  
    private static boolean isNumeric(String strnum) {  
        Pattern pattern = Pattern.compile("[0-9]*");  
        Matcher isNum = pattern.matcher(strnum);  
        if (isNum.matches()) {  
            return true;  
        } else {  
            return false;  
        }  
    }  
   
    /** 
     * 功能：判断字符串出生日期是否符合正则表达式：包括年月日，闰年、平年和每月31天、30天和闰月的28天或者29天 
     *  
     * @param string 
     * @return 
     */  
    public static boolean isDate(String strDate) {  
       
        Pattern pattern = Pattern  
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");  
        Matcher m = pattern.matcher(strDate);  
        if (m.matches()) {  
            return true;  
        } else {  
            return false;  
        }  
    }  
     
    /**
     * 调用个人认证接口
     * @param zfCode
     * @param username
     * @param tel
     * @return
     */
    public static Boolean authUserInfo(String zfCode,String username,String tel){
    	
    	String host = "http://telvertify.market.alicloudapi.com";
 	    String path = "/lianzhuo/telvertify";
 	    String method = "GET";
 	    String appcode = Prop.getInstance().getPropertiesByPro("cloudStack.properties", "userinfo.addr");
 	    Map<String, String> headers = new HashMap<String, String>();
 	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
 	    headers.put("Authorization", "APPCODE " + appcode);
 	    Map<String, String> querys = new HashMap<String, String>();
 	    querys.put("id", zfCode);
 	    querys.put("name",username);
 	    querys.put("telnumber", tel);

 	     
 	    try {
 	    	/**
 	    	* 重要提示如下:
 	    	* HttpUtils请从
 	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
 	    	* 下载
 	    	*
 	    	* 相应的依赖请参照
 	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
 	    	*/
 	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
 	    	//System.out.println(response.toString());
 	    	//获取response的body
 	    	 //JSONUtils.getm
 	    	//System.out.println(EntityUtils.toString(response.getEntity()));
 	    	String json = EntityUtils.toString(response.getEntity());
 	    	Map<String, Object> map = (Map)JSONUtils.getMapObjectByJson(json).get("resp");
 	    	if("0".equals(map.get("code"))){
 	    		return true;
 	    	}
 	    } catch (Exception e) {
 	    	e.printStackTrace();
 	    	return false;
 	    }
    	 
    	return false;
    }
    
    public static void main(String[] args) {
    	 System.out.println(authUserInfo("500101198803130113", "戴宇隆", "17723582189"));
     
	}
    /**
     * 验证短信
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles){  
	   
    	Pattern p = Pattern.compile("^1\\d{10}$");  
		   
	    Matcher m = p.matcher(mobiles);  
		    
		return m.matches();  
	   
	 }  
    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean isEmailNO(String email){  
 	   
    	Pattern p = Pattern.compile("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");  
		   
	    Matcher m = p.matcher(email);  
		    
		return m.matches();  
	   
	 }  
    
    
	/**
	 * 生成随机6位验证码
	 * @return
	 */
	public static String getRandomNum(){
		
		Long rNum = Math.round((Math.random() * 999999)) ;
		
		String rStr = String.valueOf(rNum);
		
		for (int i = 0; i < 6-rStr.length(); i++) {
			rStr = "0" + rStr;
		}
		 
		return rStr;
	}
    
    /**
     * 异常堆栈
     * @param t
     * @return
     */
    public static String printStackTraceToString(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }
    
    /**
     * 判断字段长度
     * @param str
     * @param size
     * @return
     */
    public  static  Boolean checkLong(String str,Integer size){
    	
    	if(str == null || str.isEmpty()){
    		return true;
    	}
    	if(str.length() > size){
    		return false;
    	}
    	return true;
    }
    
    /**
     * 异常处理
     * @param error_code
     * @return
     */
    public static  Map<String,Object> getResult(String code){
    	
    	Map<String,Object> resultMap = new HashMap<String,Object>(); 
    	
    	String  val= Prop.getInstance().getPropertiesByPro("errorinfo.properties", code);
    	
    	resultMap.put("status", code);
    	resultMap.put("message", val);    	
    	return resultMap;
    }
}
