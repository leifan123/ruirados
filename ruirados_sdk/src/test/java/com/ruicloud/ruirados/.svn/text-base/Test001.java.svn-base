package com.ruicloud.ruirados;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ruirados.pojo.BasicRuiradosCredentials;
import com.ruirados.pojo.OssAcl;
import com.ruirados.pojo.OssBucket;
import com.ruirados.pojo.OssZone;
import com.ruirados.service.AclService;
import com.ruirados.service.BucketService;
import com.ruirados.service.creatConnService;

import net.sf.json.JSONObject;

public class Test001 {
	
	@Test
	public void createBucket(){
		creatConnService createConn = new creatConnService();
		createConn.creatConnect(new BasicRuiradosCredentials("7RZBFITUNL53C1Y819SU", "leifen"), new OssZone().setZonename("bb"));
		BucketService bucketService = new BucketService();
		OssBucket ossBucket =  new OssBucket();
		ossBucket.setName("test001");
		ossBucket.setAccessrights(1);
		bucketService.createBucket(new OssZone().setZonename("bb"), ossBucket);
	}
	
	/*@Test
	public void getBuckets(){
		BucketService bucketService = new BucketService();
		String list = bucketService.getBuckets(new OssZone().setZonename("重庆一区"));
		System.out.println(list);
	}*/
		
	@Test
	public void selectByBucketName(){
		BucketService bucketService = new BucketService();
		OssBucket ossBucket =  new OssBucket();
		ossBucket.setName("test01");
		String str = bucketService.selectByBucketName(new OssZone().setZonename("重庆一区"), ossBucket);
		System.out.println(str);
	}
	
	/*
	@Test
	public void deleteByBucketName(){
		BucketService bucketService = new BucketService();
		OssBucket ossBucket =  new OssBucket();
		ossBucket.setName("test001");
		ossBucket.setAccessrights(1);
		bucketService.deleteByBucketName(new OssZone().setZonename("重庆一区"), ossBucket);
	}
	
	@Test
	public void createCustomAcl(){
		AclService aclService = new AclService();
		OssBucket ossBucket =  new OssBucket();
		ossBucket.setName("test01");
		ossBucket.setId(43);
		OssAcl ossAcl = new OssAcl();
		ossAcl.setUserauthorization("*");
		ossAcl.setDeletebucket(1);
		ossAcl.setDeleteobject(1);
		ossAcl.setGetobject(1);
		ossAcl.setPutobject(1);
		ossAcl.setListbucket(1);
		ossAcl.setResource("lll/123");
		ossAcl.setIseffectres(1);
		ossAcl.setIseffectrefip(0);
		ossAcl.setRefererip("");
		String str = aclService.createCustomAcl(ossAcl , new OssZone().setZonename("重庆一区"), ossBucket);
		System.out.println(str);
	}
	
	@Test
	public void selectFromBucketId(){
		AclService aclService = new AclService();
		String str = aclService.selectFromBucketId(new OssAcl().setCode("fe11f74bf53642fc8c705fd0fab498be"), new OssBucket().setCode("test01"));
		System.out.println(str);
		
	}
	
	@Test
	public void selectAclAll(){
		AclService aclService = new AclService();
		OssBucket ossBucket =  new OssBucket();
		ossBucket.setName("test01");
		ossBucket.setId(43);
		String str = aclService.selectAclAll(new OssZone().setZonename("重庆一区"), ossBucket);
		System.out.println(str);
	}
	
	@Test
	public void aclCut(){
		AclService aclService = new AclService();
		OssBucket ossBucket =  new OssBucket();
		ossBucket.setName("test01");
		ossBucket.setAccessrights(2);
		ossBucket.setId(43);
		String str = aclService.aclCut(new OssZone().setZonename("重庆一区"), ossBucket);
		System.out.println(str);
	}
	
	@Test
	public void deleteFromBucketId(){
		AclService aclService = new AclService();
		OssBucket ossBucket =  new OssBucket();
		ossBucket.setName("test01");
		String str = aclService.deleteFromBucketId(new OssAcl().setCode("2535f8ed15a141bd81e4481c3c2f2dc2"), ossBucket);
		System.out.println(str);
	}
	
	@Test
	public void test2(){
		Map<String, String> bucket_map = new HashMap<String, String>();
		bucket_map.put("bucketName", "123");
		bucket_map.put("accessrights", "1234");
		bucket_map.put("zoneId", "sda");
		JSONObject jsonObject = JSONObject.fromObject(bucket_map);  
        System.out.println("输出的结果是：" + jsonObject);  
        //3、将json对象转化为json字符串  
        String result = jsonObject.toString();  
        System.out.println(result);
	}*/
}
