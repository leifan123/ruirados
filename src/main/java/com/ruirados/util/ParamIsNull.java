package com.ruirados.util;

import java.util.HashMap;
import java.util.Map;

public class ParamIsNull {
       public static boolean isNull(Map<String,String> param){
    	   for (String para : param.keySet()) {
			if(param.get(para)==null){
				return false;
			}
		}
    	  return true; 
       }
       public static void main(String[] args) {
    		Map<String,String> a=new HashMap<String,String>();
    	     a.put("a", "0");
    	     a.put("b", "1");
    	     a.put("c", "1");
    	   
    	    //System.out.println( ParamIsNull.isNull(a));
	}
       public static boolean isNull(Object...  param ){
    	   for (Object para : param) {
			if(para==null||"".equals(para)){
				return false;
			}
		}
    	  return true; 
       }
      
}
