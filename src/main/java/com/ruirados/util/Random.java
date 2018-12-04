package com.ruirados.util;

import java.util.Date;

public class Random {
/*public  static String rand(HttpServletRequest request){
	YrComper yr=(YrComper)request.getSession().getAttribute("comper");
	String orderNumber=yr.getCompanyid()+System.currentTimeMillis();
	
	return orderNumber;
}*/
//订单 编号 16位

private static int odn = 100;

/**
 * 0充值  1提现    2内部转账  3消费 4收益 
 * @param type
 * @return
 */
public static  String rand(){
  //time(13)+odn(4)
  if(odn++ > 999){
    odn = 100;
  }
    
  return   new Date().getTime()+""+ odn;
}
public static  String timeRand(){
	  //time(13)+odn(4)
	  if(++odn > 998){
	    odn = 100;
	  }
	    
	  return   System.currentTimeMillis()+""+ odn;
	}
}
