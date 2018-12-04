package com.ruicloud.ruirados;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.ruirados.pojo.BasicRuiradosCredentials;
import com.ruirados.pojo.OssObject;
import com.ruirados.pojo.OssZone;
import com.ruirados.service.ObjectService;
import com.ruirados.service.ZoneService;
import com.ruirados.service.creatConnService;
import com.ruirados.utils.uploadfileUtils;

import sun.misc.BASE64Encoder;


public class testobject {
	//@Test
	public void createobject(){
		creatConnService createConn = new creatConnService();
		createConn.creatConnect(new BasicRuiradosCredentials("7RZBFITUNL53C1Y819SU", "leifen"), new OssZone().setZonename("bb"));
	
	}
	//@Test
	public void createobject1(){
		ObjectService ob = new ObjectService();
		OssZone oz = new OssZone();
		oz.setZonename("bb");
		OssObject oo = new OssObject();
		oo.setBucketName("123");
		oo.setFilename("tanjun81");
		oo.setId(70);
		String x = ob.createObject(oz, oo);
		System.out.println(x);
	}
	//@Test
	public void deleteFile(){
		ObjectService ob = new ObjectService();
		OssZone oz = new OssZone();
		oz.setZonename("bb");
		OssObject oo = new OssObject();
		oo.setBucketName("123");
		oo.setFilename("tanjun");
		oo.setId(39);
		String x = ob.deleteFile(oz, oo);
		System.out.println("x:"+x);
	}
	//@Test
	public void zonelist(){
		ZoneService zs = new ZoneService();
		zs.zoneList();
		System.out.println(zs.zoneList());
	}
	@Test
	public void uploadfile() throws Exception{
		ObjectService ub = new ObjectService();
		String masage = ub.uploadObject( "123","70","bb","G:/ppt-主打胶片/赠送的一套.pptx");
		System.out.println(masage);
	}
	//@Test
	public void getSrc(){
		ObjectService ob = new ObjectService();
		OssZone oz = new OssZone();
		oz.setZonename("bb");
		OssObject oo = new OssObject();
		oo.setBucketName("123");

		String x = ob.geturl(oz, oo, "tanjun1","1" );
		System.out.println("x:"+x);
	}
	
	  
	

}
