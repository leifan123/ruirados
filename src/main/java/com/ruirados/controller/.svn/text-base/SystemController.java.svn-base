/**
 * NewHeight.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.ruirados.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.twonote.rgwadmin4j.RgwAdmin;
import org.twonote.rgwadmin4j.RgwAdminBuilder;
import org.twonote.rgwadmin4j.model.CredentialType;
import org.twonote.rgwadmin4j.model.SubUser;

import com.ruirados.pojo.RspData;
import com.ruirados.pojo.UserAccess;
import com.ruirados.pojo.UserCoreAccess;
import com.ruirados.service.ConfigureInitation;
import com.ruirados.service.UserAccessService;
import com.ruirados.service.UserCoreAccessService;
import com.ruirados.threads.SynUserAccessKeyRunnable;
import com.ruirados.util.ExptNum;
import com.ruirados.util.JSONUtils;
import com.ruirados.util.MappingConfigura;
import com.ruirados.util.Prop;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author yunrui006
 * @version $Id: systemController.java, v 0.1 2018年6月26日 上午10:06:51 yunrui006 Exp $
 */
@Controller(value="systemController")
@RequestMapping(MappingConfigura.SYSTEM)
public class SystemController {
	
	Logger log = Logger.getLogger(getClass());
	@Autowired
	ConfigureInitation configureInitation;
	@Autowired
	UserAccessService userAccessService;
	@Autowired
	UserCoreAccessService userCoreAccessService;
	
	/**
	 * 刷新系统配置缓存
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "flushSystemConfigure", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String flushSystemConfigure(HttpServletRequest request, HttpServletResponse response) throws Exception {
		configureInitation.initSystemConfigure();
		RspData rspData = new RspData();
		rspData.setStatus(ExptNum.SUCCESS.getCode()+"");
		rspData.setMsg(ExptNum.SUCCESS.getDesc());
		return JSONUtils.createObjectJson(rspData);
	}
	
	/**
	 * 同步用户access_key  接口
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "synUserAccess", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String synUserAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int threadNum = Integer
				.valueOf(Prop.getInstance().getPropertiesByPro("ruirados.properties", "destroyBucket.thread.num"));
		//遍历子账户的companyId
		List<UserAccess> userAccessList = userAccessService.selectByParam("companyId", null);
		ConcurrentLinkedQueue<String> companyIdQueue = new ConcurrentLinkedQueue<String>();
		for(UserAccess userAccess : userAccessList) {
			companyIdQueue.offer(userAccess.getCompanyid());
		}
		ExecutorService threadPool = Executors.newFixedThreadPool(threadNum);
		for(int i = 0; i < threadNum; i++) {
			log.debug("正在同步AccessKey");
			threadPool.submit(new SynUserAccessKeyRunnable(userAccessService, userCoreAccessService, companyIdQueue));
		} 
		threadPool.shutdown();
		RspData rspData = new RspData();
		rspData.setStatus(ExptNum.SUCCESS.getCode()+"");
		rspData.setMsg(ExptNum.SUCCESS.getDesc());
		return JSONUtils.createObjectJson(rspData);
		
	}
	
	/**
	 * 新增区域时同步用户接口
	 * @param apiKey  区域管理员Accesskey
	 * @param secretKey  区域管理员SecretKey
	 * @param serverIp  区域ip
	 * @return
	 */
	@RequestMapping(value = "addZoneSynUser", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public  String creatUserToCeph(String apiKey, String secretKey, String serverIp){
		List<UserCoreAccess> userCoreAccessesList = userCoreAccessService.selectByParam(null, "cephAccessKeyID is not null and cephAccessKeySecret is not null");
		List<UserAccess> userAccessesList = userAccessService.selectByParam(null, "userId is not null and accessKeyID is not null and accessKeySecret is not null");
		RgwAdmin rgwAdmin = new RgwAdminBuilder().accessKey(apiKey).secretKey(secretKey).endpoint(serverIp)
                .build();
		for(UserCoreAccess userCoreAccess : userCoreAccessesList){
			Map<String, String> params = new HashMap<String, String>();
			params.put("user-caps", "usage=read, write");
			params.put("access-key", userCoreAccess.getCephaccesskeyid());
			params.put("secret-key", userCoreAccess.getCephaccesskeysecret());
			rgwAdmin.createUser(userCoreAccess.getCompanyid(), params);
			
			for(UserAccess userAccesses : userAccessesList){
				if(userCoreAccess.getCompanyid().equals(userAccesses.getCompanyid())){
					rgwAdmin.createSubUser(userCoreAccess.getCompanyid(), userAccesses.getUserid(), SubUser.Permission.FULL,
							CredentialType.S3);
					rgwAdmin.createS3CredentialForSubUser(userCoreAccess.getCompanyid(), userAccesses.getUserid(), 
							userAccesses.getAccesskeyid(), userAccesses.getAccesskeysecret());
				}
			}
		}
		
		RspData rspData = new RspData();
		rspData.setStatus(ExptNum.SUCCESS.getCode()+"");
		rspData.setMsg(ExptNum.SUCCESS.getDesc());
		return JSONUtils.createObjectJson(rspData);
	}
	
}
