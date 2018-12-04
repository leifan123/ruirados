/**
 * NewHeight.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.ruirados.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.ruirados.pojo.AmazonCacheBean;
import com.ruirados.pojo.UserCoreAccess;
import com.ruirados.service.UserCoreAccessService;

/**
 * <pre>
 * s3连接缓存
 * </pre>
 *
 * @author yunrui006
 * @version $Id: AmazonCache.java, v 0.1 2018年6月7日 下午1:25:33 yunrui006 Exp $
 */
@Controller
public class AmazonCache {
	
	
	private static  UserCoreAccessService userCoreAccessService;
	
	public static Map<String, List<AmazonCacheBean>> amazonConnCache = new HashMap<String, List<AmazonCacheBean>>();

	
	
	@Autowired
	private   AmazonCache(UserCoreAccessService userCoreAccessService) {
		this.userCoreAccessService = userCoreAccessService;
	}
	
	public static AmazonCacheBean getAmazonConn(String companyId,int protocol){
		List<AmazonCacheBean> AmazonConnList = amazonConnCache.get(companyId);
		if(protocol == 1){
			return createAmazonConnBean(companyId,protocol);
		}	else{
			if(AmazonConnList != null){
				return AmazonConnList.get(0);
			}
			
			return createAmazonConnBean(companyId,protocol);
		}
//		return createAmazonConnBean(companyId,protocol);
	}
	
	public static  AmazonCacheBean createAmazonConnBean(String companyId,int protocol){

		UserCoreAccess userCoreAccessParams = new UserCoreAccess();
		userCoreAccessParams.setCompanyid(companyId);
		UserCoreAccess userCoreAccess = userCoreAccessService.select(userCoreAccessParams).get(0);
		if(userCoreAccess != null){
			List<AmazonCacheBean> CAmazonConnList = new ArrayList<AmazonCacheBean>();
			String accessKey = userCoreAccess.getCephaccesskeyid();
			String secretKey = userCoreAccess.getCephaccesskeysecret();
			AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
			ClientConfiguration clientConfig = new ClientConfiguration();
			clientConfig.setSignerOverride("S3SignerType");
			if(protocol == 1){
				String pro = userCoreAccess.getProtocol();
				if("http".equals(pro.toLowerCase())){
					clientConfig.setProtocol(Protocol.HTTP);
				}	else{
					clientConfig.setProtocol(Protocol.HTTPS);
				}
			}	else{
				clientConfig.setProtocol(Protocol.HTTPS);
			}
			AmazonS3 conn = new AmazonS3Client(credentials,clientConfig);
			conn.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build());
			AmazonCacheBean amazonCacheBean = new AmazonCacheBean(conn, null);
			CAmazonConnList.add(amazonCacheBean);
			
			amazonConnCache.put(companyId, CAmazonConnList);
			
			
			return amazonCacheBean;
		}
		return null;
	}
	

	public static Map<String, List<AmazonCacheBean>> getAmazonConnCache() {
		return amazonConnCache;
	}

	
	public static void setAmazonConnCache(Map<String, List<AmazonCacheBean>> amazonConnCache) {
		AmazonCache.amazonConnCache = amazonConnCache;
	}
	
	
}
