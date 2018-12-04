package com.ruirados.utils;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @describe Analytic properties
 * @author daire
 */
public class Prop {

	private static Prop instance = null;

	private   Map<String, String> propMap = null;
	
	private  static Map<String, Map<String, String>> propMapCache= null;
	
	private Prop() {
		// TODO Auto-generated constructor stub
		 
		propMapCache = new HashMap<String, Map<String,String>>();
	}

	public static  Prop getInstance() {
		
		if (instance == null) {
			instance = new Prop();
		}

		return instance;
	}
	/**
	 * 
	 * @param propPath
	 * @return
	 * 2011-12-20
	 */
	@SuppressWarnings("unchecked")
	private   Map<String, String> getProp(String propPath) {
		propMap = new HashMap<String, String>();
		propMap.clear();
		String propBak = propPath;
		
		if (propPath.indexOf("/") != 0) {
			propPath = "/" + propPath;
		}
 
		InputStream in = getClass().getResourceAsStream(propPath);
	 
		Properties prop = new Properties();
		//System.out.println(in);
		try {
			prop.load(in);

			Iterator<?> it = prop.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();
		 
				propMap.put(key, value);
			}
			propMapCache.put(propBak, propMap);
		 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
 			if(in !=  null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return propMap;
	}
	 
	/**
	 * 
	 * @param filename
	 * @return
	 */
	public  Map<String, String> getPropMap(String filename){
		
		if(propMapCache.containsKey(filename)){
			return propMapCache.get(filename);
		}else{
			 return getProp(filename);
		}
	}
	
	/**
	 * 
	 * @param filename
	 * @param key
	 * @return 2011-12-20
	 */
	public String getPropertiesByPro(String filename, String key) {

		if(propMapCache.containsKey(filename)){
			return propMapCache.get(filename).get(key);
		}else{
			 return getProp(filename).get(key);
		}
	}
       
}
