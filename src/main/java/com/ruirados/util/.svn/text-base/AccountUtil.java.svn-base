/**
 * @author YuLong.Dai
 * @time 2017年4月17日 上午11:02:07
 * TODO
 */
package com.ruirados.util;


import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.ruirados.pojo.Account;
import com.ruirados.service.InstantiationTracingBeanPostProcessor;

/**
 * @author YuLong.Dai
 * @time 2017年4月17日 上午11:02:07
 */
public class AccountUtil {
	
	private static Logger log = Logger.getLogger(InstantiationTracingBeanPostProcessor.class);
	 
	public static void  intodb(Account account){
		
		try {
			
			ConcurrentLinkedQueue<Account> accountQueue = GlobalAttr.getInstance().getAccountQueue();
			
			accountQueue.add(account);
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e);
		}
	}
	
	
	public static boolean useAccount(String zoneId){
		String zoneIds = Prop.getInstance().getPropertiesByPro("cloudStack.properties", "cashTicket.zoneId");
		String[] zoneIdString=zoneIds.split("#");
		for (String string : zoneIdString) {
			if(!"".equals(string)&&string!=null&&string.equals(zoneId)){
				return true;
			}
		}
		return false;
	}
	
	
}
