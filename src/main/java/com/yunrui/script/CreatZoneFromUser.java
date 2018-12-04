package com.yunrui.script;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.twonote.rgwadmin4j.RgwAdmin;
import org.twonote.rgwadmin4j.RgwAdminBuilder;
import org.twonote.rgwadmin4j.model.CredentialType;
import org.twonote.rgwadmin4j.model.SubUser;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.ruirados.pojo.UserAccess;
import com.ruirados.pojo.UserCoreAccess;
import com.ruirados.service.UserAccessService;
import com.ruirados.service.UserCoreAccessService;


public class CreatZoneFromUser {
	
	public static void creatUserToCeph(String apiKey, String secretKey, String serverIp){
		String[] resources = { "classpath*:applicationContext.xml" };
		ApplicationContext ctx = new ClassPathXmlApplicationContext(resources);
		
		UserCoreAccessService userCoreAccessService = ctx.getBean("UserCoreAccessService", UserCoreAccessService.class);
		
		UserAccessService userAccessService = ctx.getBean("UserAccessService", UserAccessService.class);
		
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
	}
	
	public static void main(String[] args) {
		CreatZoneFromUser.creatUserToCeph("", "", "");
	}

}
