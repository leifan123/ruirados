package com.ruirados.util;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;
import org.twonote.rgwadmin4j.RgwAdmin;

import com.ruirados.bean.DestroyBucketBean;
import com.ruirados.pojo.Account;
import com.ruirados.pojo.OssZone;

/**
 * @author daire 2015-7-27
 */
public class GlobalAttr {

	private Logger log = Logger.getLogger(GlobalAttr.class);

	private static GlobalAttr globalAttr;
  
	private static Map<String, String> userTokenMap;
 
	private static Map<String,OssZone> zoneMap;
	
	private static Map<String, RgwAdmin> rgwAdminMap;
	
	private static ConcurrentLinkedQueue<DestroyBucketBean> destroyBucketqueue;	
	
	private static ConcurrentLinkedQueue<Account> accountQueue;
  
	public static GlobalAttr getInstance() {
		if (globalAttr == null) {
			globalAttr = new GlobalAttr();
		}
		return globalAttr;
	}

	private GlobalAttr() {
 
		userTokenMap = new HashMap<String, String>();
		zoneMap = new HashMap<String,OssZone>();
		destroyBucketqueue = new ConcurrentLinkedQueue<DestroyBucketBean>();
		rgwAdminMap = new HashMap<String, RgwAdmin>();
		accountQueue = new ConcurrentLinkedQueue<Account>();
	}

	public Map<String, String> getUserTokenMap() {
		return userTokenMap;
	}

	 


	public  Map<String, OssZone> getZoneMap() {
		return zoneMap;
	}

	public static Map<String, RgwAdmin> getRgwAdminMap() {
		return rgwAdminMap;
	}
	
	public static ConcurrentLinkedQueue<Account> getAccountQueue() {
		return accountQueue;
	}

	public static void setAccountQueue(ConcurrentLinkedQueue<Account> accountQueue) {
		GlobalAttr.accountQueue = accountQueue;
	}

	public static void setRgwAdminMap(Map<String, RgwAdmin> rgwAdminMap) {
		GlobalAttr.rgwAdminMap = rgwAdminMap;
	}

	public static ConcurrentLinkedQueue<DestroyBucketBean> getDestroyBucketqueue() {
		return destroyBucketqueue;
	}

	public static void setDestroyBucketqueue(ConcurrentLinkedQueue<DestroyBucketBean> destroyBucketqueue) {
		GlobalAttr.destroyBucketqueue = destroyBucketqueue;
	}

}
