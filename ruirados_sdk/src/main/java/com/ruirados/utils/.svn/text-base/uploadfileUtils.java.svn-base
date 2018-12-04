package com.ruirados.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class uploadfileUtils {
	public String upload(String url,String fileSrc,String bucketName, String zoneId,String dirId) throws Exception{
		  HttpClient httpclient = new DefaultHttpClient();

		    // HttpHost proxy = new HttpHost(p.getHost(),
		    // Integer.valueOf(p.getPort()), "http");
		    // httpclient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY,
		    // proxy);
		    // 请求路径
		    HttpPost post = new HttpPost(url);

		    post.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;)");

		    post.setHeader("Host", "****");
		    post.setHeader("Accept-Encoding", "gzip");
		    post.setHeader("charset", "utf-8");
		    FileBody fileBody = new FileBody(new File(fileSrc));
		    MultipartEntity entity = new MultipartEntity();

		    entity.addPart("bucketName",  new StringBody(bucketName, Charset.forName("UTF-8"))); 
		    entity.addPart("zoneId",  new StringBody(zoneId, Charset.forName("UTF-8"))); 
		    entity.addPart("dirId",  new StringBody(dirId, Charset.forName("UTF-8"))); 
		    entity.addPart("uploadFile", fileBody);
		    
		    post.setEntity(entity);
		    HttpResponse response = httpclient.execute(post);
		    HttpEntity entitys=null;
		    if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
		     entitys = response.getEntity();
		    }
		    
		    httpclient.getConnectionManager().shutdown();
		    return EntityUtils.toString(entitys);
			
	}

}
