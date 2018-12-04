package com.ruirados.util;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.Permission;

public class RuiradosUtil {
	
	/**
	 * 连接AmazonS3
	 * @param accessKeyID
	 * @param accessKeySecret
	 * @param edpoint
	 * @return
	 */
	public static AmazonS3 getConn(String accessKeyID, String accessKeySecret, String edpoint){
		AWSCredentials credentials = new BasicAWSCredentials(accessKeyID, accessKeySecret);
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setSignerOverride("S3SignerType");
		clientConfig.setProtocol(Protocol.HTTPS);
		AmazonS3 conn = new AmazonS3Client(credentials,clientConfig);
		conn.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build());
		conn.setEndpoint(edpoint);
		return conn;
	}
	
	/**
	 * 根据用户选择对应权限
	 * @param accessrights	1 ：只读， 2：只写，3：公有读写
	 * @return
	 */
	public static int getAccessrights(String permission){
		int accessrights = 0;
		
		if(permission.equals("READ_ACP")){
			accessrights = 1;
		}	else if(permission.equals("FULL_CONTROL")){
			accessrights = 3;
		}	else if(permission.equals("READ")){
			accessrights = 1;
		}	else if(permission.equals("WRITE")){
			accessrights = 2;
		}	else if(permission.equals("WRITE_ACP")){
			accessrights = 2;
		}	else{
			accessrights = 3;
		}
		
		return accessrights;
	}
	
	/**
	 * 根据用户选择对应权限
	 * @param accessrights	1 ：只读， 2：只写，3：公有读写
	 * @return
	 */
	public static Map<String, Integer> objectAcl(int accessrights){
		Map<String, Integer> map = new HashMap<String, Integer>(); 
		/*
		 * 对除自己外其他用户
		 * 0:	无权限
		 * 1：有权限
		 */
		if(accessrights == 1){
			map.put("putObject", 0);
			map.put("getObject", 1);	
			map.put("deleteObject", 0);
			map.put("listBucket", 0);
			map.put("deleteBucket", 0);
			return	map;
		}	else if(accessrights == 2){
			map.put("putObject", 1);
			map.put("getObject", 0);
			map.put("deleteObject", 0);
			map.put("listBucket", 0);
			map.put("deleteBucket", 0);
			return	map;
		}	else if(accessrights == 3){
			map.put("putObject", 1);
			map.put("getObject", 1);
			map.put("deleteObject", 1);
			map.put("listBucket", 1);
			map.put("deleteBucket", 1);
			return	map;
		}
		return map;
	}
	
}
