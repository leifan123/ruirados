package com.ruirados.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static String getTime(){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
    	return sdf.format(new Date());
    }
    public static String getYearToDay(){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	return sdf.format(new Date());
    }
    public static String getYearToSecond(){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return sdf.format(new Date());
    }
    public static String getYearToMonth(){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
    	return sdf.format(new Date());
    }
    public static String getYearToSecondByDate(Date date){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return sdf.format(date);
    }
    
    public static Date getDateTypeFromString(String stringtime){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date=null;
    	try {
    		 date= sdf.parse(stringtime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return date;
    }
    
    public static String getYearToMonthCountOne(){
    	Calendar ca=Calendar.getInstance();
    	ca.setTime(new Date());
    	  int year = ca.get(Calendar.YEAR);//年份
    	    int month = ca.get(Calendar.MONTH);//月份
    	return year+"-"+( (month+2)<10?"0"+(month+2):(month+2));
    }
    
    
    public static Long getRemainTime(Date date)  {
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, 2);

        int year=c.get(Calendar.YEAR);    
        int month=c.get(Calendar.MONTH)+1;  
        int day=c.get(Calendar.DAY_OF_MONTH);
        int hour=c.get(Calendar.HOUR_OF_DAY);
        
        SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long d_2017=0l;
		try {
			d_2017 = s.parse(year+"-"+month+"-"+day+" "+hour+":59:59").getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Long now=new Date().getTime();
        
        Long second=(d_2017-now);  //总的秒数
        

    	//倒计时2小时
    	return second;
    }
    public static void main(String[] args) throws ParseException {
    	   SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	   Date d_2017 = s.parse("2017-11-07 17:59:20");
  
    	//System.out.println(FcUtil.IDCardValidate("500101198803130112"));;
    	System.out.println( getRemainTime(d_2017));;
	}
}
