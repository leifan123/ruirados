package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ddf.EscherColorRef.SysIndexProcedure;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import com.amazonaws.services.s3.model.CORSRule;
import com.amazonaws.services.s3.model.DeleteBucketCrossOriginConfigurationRequest;
import com.ruirados.util.Uuid;

public class CORSTest {

	public static void main(String[] args) {
		String accessKey = "7RZBFITUNL53C1Y819SU";
		String secretKey = "leifen";
//		String accessKey = "TK33GTT9EM4ZXZAH4G9J";
//		String secretKey = "tanshunting";
		// 如果默认的端口不是80（假如是90）
		// String endpoint = "10.41.25.35:90";
		String endpoint = "oss.xrcloud.net";

		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setSignerOverride("S3SignerType");
		clientConfig.setProtocol(Protocol.HTTPS);
		AmazonS3 conn = new AmazonS3Client(credentials, clientConfig);
		conn.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build());
		// conn.getBucketLocation("zhouyi");
		// conn.setS3ClientOptions(S3ClientOptions.builder().build());
		conn.setEndpoint(endpoint);

//		List<Bucket> buckets = conn.listBuckets();
//		for (Bucket bucket : buckets) {
//			System.out.println(bucket.getName());
//		}
		
		BucketCrossOriginConfiguration configuration = new BucketCrossOriginConfiguration();
//		conn.getBucketCrossOriginConfiguration("");

		// 创建两条CORS 规则
//		List<CORSRule.AllowedMethods> rule1AM = new ArrayList<CORSRule.AllowedMethods>();
//		rule1AM.add(CORSRule.AllowedMethods.DELETE);
//		rule1AM.add(CORSRule.AllowedMethods.PUT);
//		rule1AM.add(CORSRule.AllowedMethods.DELETE);
//		CORSRule rule1 = new CORSRule().withId("CORSRule1").withAllowedMethods(rule1AM)
//				.withAllowedOrigins(Arrays.asList(new String[] { "https://www.jd.com" })).withMaxAgeSeconds(3000)
//				.withExposedHeaders(Arrays.asList(new String[] { "x-amz-server-side-encryption" }));
//
//		List<CORSRule.AllowedMethods> rule2AM = new ArrayList<CORSRule.AllowedMethods>();
//		rule2AM.add(CORSRule.AllowedMethods.DELETE);
//		CORSRule rule2 = new CORSRule().withId("CORSRule2").withAllowedMethods(rule2AM)
//				.withAllowedOrigins(Arrays.asList(new String[] { "*" })).withMaxAgeSeconds(3000)
//				.withExposedHeaders(Arrays.asList(new String[] { "x-amz-server-side-encryption" }));
//
//		List<CORSRule.AllowedMethods> rule3AM = new ArrayList<CORSRule.AllowedMethods>();
//		CORSRule rule3 = new CORSRule().withId("CORSRule3").withAllowedMethods(rule3AM)
//				.withAllowedOrigins(Arrays.asList(new String[] { "*" })).withMaxAgeSeconds(3000)
//				.withExposedHeaders(Arrays.asList(new String[] { "x-amz-server-side-encryption" }));
//		
//		List<CORSRule> rules = new ArrayList<CORSRule>();
//		rules.add(rule1);
//		rules.add(rule2);
//		rules.add(rule3);

//		System.out.println(rules.toString());
//		System.out.println("id --- >" +  rule1.getId());
		
		// 将规则添加到新的CORS配置中
//		configuration.setRules(rules);

		// 将配置添加到bucket中
//		conn.setBucketCrossOriginConfiguration("dddds150010207", configuration);

		// 添加另一个新规则。
		
//		  List<CORSRule.AllowedMethods> rule4AM = new ArrayList<CORSRule.AllowedMethods>();
//		  rule4AM.add(CORSRule.AllowedMethods.GET); 
//		  
//		  CORSRule rule3 = new CORSRule().withId("CORSRule4").withAllowedMethods(rule4AM)
//					.withAllowedOrigins(Arrays.asList(new String[] { "*" })).withMaxAgeSeconds(3000)
//					.withExposedHeaders(Arrays.asList(new String[] { "x-amz-server-side-encryption" }));
//		 
		  //configuration = conn.getBucketCrossOriginConfiguration("test01150010207");
//		  System.out.println(configuration.getRules().toString());
//		  System.out.println(configuration == null);
//		  List<CORSRule> rules = new ArrayList<CORSRule>();
		  //configuration = conn.getBucketCrossOriginConfiguration("test01150010207");
//		  if(configuration == null){
//			  rules = new ArrayList<CORSRule>();
//		  }	else{
//			  rules = configuration.getRules();
//		  }
		  
//		  String orgins = "www.jd.com,www.baidu.com";
//			List<CORSRule.AllowedMethods> rule1AM = new ArrayList<CORSRule.AllowedMethods>();
//			rule1AM.add(CORSRule.AllowedMethods.DELETE);
//			rule1AM.add(CORSRule.AllowedMethods.PUT);
//			rule1AM.add(CORSRule.AllowedMethods.DELETE);
//		  
//		  CORSRule rule1 = null;
//			System.out.println(" orgins.contains -- >" + orgins.contains(","));
//			if(orgins.contains(",")){
//				String[] strs = orgins.split(",");
//				for(int i = 0; i < strs.length; i++){
//					rule1 = new CORSRule().withId("CORS" + Uuid.getGenerateCode().substring(0, 5)).withAllowedMethods(rule1AM)
//							.withAllowedOrigins(Arrays.asList(new String[] { strs[i] })).withMaxAgeSeconds(100)
//							.withExposedHeaders(Arrays.asList(new String[] { "123" }));
//					rules.add(rule1);
//					System.out.println("rules -- >" + rules);
//				}
//			}	else{
//				rule1 = new CORSRule().withId("CORS" + Uuid.getGenerateCode().substring(0, 5)).withAllowedMethods(rule1AM)
//						.withAllowedOrigins(Arrays.asList(new String[] { orgins })).withMaxAgeSeconds(100)
//						.withExposedHeaders(Arrays.asList(new String[] { "123" }));
//				
//				rules.add(rule1);
//				System.out.println("rules -- >" + rules);
//			}
//			
//			System.out.println("rules -- >" + rules.size());
//			// 将规则添加到新的CORS配置中
//			configuration.setRules(rules);
//
//			// 将配置添加到bucket中
//			conn.setBucketCrossOriginConfiguration("test01150010207", configuration);
//		  rules.add(num , new CORSRule().withId("CORSRule5").withAllowedMethods(rule4AM)
//					.withAllowedOrigins(Arrays.asList(new String[] { "*" })).withMaxAgeSeconds(3000)
//					.withExposedHeaders(Arrays.asList(new String[] { "x-amz-server-side-encryption" })));
//		  
//		  configuration.setRules(rules);
//		  conn.setBucketCrossOriginConfiguration("ccc", configuration);
//		  
//		  rules.add(rule3);
//		  configuration.setRules(rules);
//		  conn.setBucketCrossOriginConfiguration("ccc", configuration);
		 
			// 删除配置。
//			conn.deleteBucketCrossOriginConfiguration("test01150010207");
			
		// 查看CORS
		configuration = conn.getBucketCrossOriginConfiguration("dddds150010207");
		List<CORSRule> rules = configuration.getRules();
		for(int i = 0; i < rules.size(); i++){
			if(rules.get(i).getId().equals("CORSRule2")){
				rules.remove(rules.get(i));
				i++;
			}
		}
		configuration.setRules(rules);
		conn.setBucketCrossOriginConfiguration("dddds150010207", configuration);
		printCORSConfiguration(configuration);
		
		
//		conn.deleteBucketCrossOriginConfiguration();
//		conn.deleteBucketCrossOriginConfiguration("ccc");
//		System.out.println("Removed CORS configuration.");

		System.out.println("all done!");

	}

	private static void printCORSConfiguration(BucketCrossOriginConfiguration configuration) {
		if (configuration == null) {
			System.out.println("Configuration is null.");
		} else {
			// 通过检查配置中的规则数量来验证新规则的添加
			System.out.println("Configuration has " + configuration.getRules().size() + " rules\n");

			for (CORSRule rule : configuration.getRules()) {
				System.out.println("Rule ID: " + rule.getId());
				System.out.println("MaxAgeSeconds: " + rule.getMaxAgeSeconds());
				System.out.println("AllowedMethod: " + rule.getAllowedMethods());
				System.out.println("AllowedOrigins: " + rule.getAllowedOrigins());
				System.out.println("AllowedHeaders: " + rule.getAllowedHeaders());
				System.out.println("ExposeHeader: " + rule.getExposedHeaders());
				System.out.println();
			}
		}
	}
}
