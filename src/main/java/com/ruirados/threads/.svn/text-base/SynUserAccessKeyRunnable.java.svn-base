package com.ruirados.threads;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.twonote.rgwadmin4j.RgwAdmin;
import org.twonote.rgwadmin4j.model.S3Credential;
import org.twonote.rgwadmin4j.model.User;

import com.ruirados.pojo.UserAccess;
import com.ruirados.service.UserAccessService;
import com.ruirados.service.UserCoreAccessService;
import com.ruirados.util.GlobalAttr;


public class SynUserAccessKeyRunnable implements Runnable {
	
	
	private UserAccessService userAccessService;
	private UserCoreAccessService userCoreAccessService;
	private ConcurrentLinkedQueue<String> companyIdQueue;
	
	
	public SynUserAccessKeyRunnable(UserAccessService userAccessService, UserCoreAccessService userCoreAccessService,ConcurrentLinkedQueue<String> companyIdQueue) {
		super();
		this.userAccessService = userAccessService;
		this.userCoreAccessService = userCoreAccessService;
		this.companyIdQueue = companyIdQueue;
	}

	public void run() {
		String companyId;
		while ((companyId = companyIdQueue.poll()) != null) {

			Map<String, RgwAdmin> rgwAdminMap = GlobalAttr.getInstance().getRgwAdminMap();
			RgwAdmin rgwAdmin = rgwAdminMap.values().iterator().next();
			Optional<User> userInfo = rgwAdmin.getUserInfo(companyId);
			if (userInfo.isPresent()) {
				User user = userInfo.get();
				List<S3Credential> s3Credentials = user.getS3Credentials();
				String userCoreAccessKey = "";
				String userCoreSecretkey = "";
				String subUserAccessKey = "";
				String subUserSecretKey = "";
				List<UserAccess> userAccessList = userAccessService.select(new UserAccess().setCompanyid(companyId));
				for (UserAccess subUser : userAccessList) {
					for (S3Credential s3c : s3Credentials) {
						if (companyId.equals(s3c.getUserId())) {
							userCoreAccessKey = s3c.getAccessKey();
							userCoreSecretkey = s3c.getSecretKey();
							userCoreAccessService
									.updateByParam("cephAccessKeyID ='" + userCoreAccessKey + "',cephAccessKeySecret='"
											+ userCoreSecretkey + "' where companyId = '" + companyId + "'");
						}
						if (s3c.getUserId().equals(companyId + ":" + subUser.getUserid())) {
							subUserAccessKey = s3c.getAccessKey();
							subUserSecretKey = s3c.getSecretKey();
							userAccessService.updateByParam("accessKeyID = '" + subUserAccessKey
									+ "' , accessKeySecret = '" + subUserSecretKey + "' where companyId = '" + companyId
									+ "' and userId = '" + subUser.getUserid() + "'");
						}

					}
				}
			}
		}

	}
	
	public UserAccessService getUserAccessService() {
		return userAccessService;
	}

	public void setUserAccessService(UserAccessService userAccessService) {
		this.userAccessService = userAccessService;
	}

	public UserCoreAccessService getUserCoreAccessService() {
		return userCoreAccessService;
	}

	public void setUserCoreAccessService(UserCoreAccessService userCoreAccessService) {
		this.userCoreAccessService = userCoreAccessService;
	}


	public ConcurrentLinkedQueue<String> getCompanyIdQueue() {
		return companyIdQueue;
	}

	public void setCompanyIdQueue(ConcurrentLinkedQueue<String> companyIdQueue) {
		this.companyIdQueue = companyIdQueue;
	}

}
