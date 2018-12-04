/**
 * @author YuLong.Dai
 * @time 2018年5月3日 下午4:29:59
 * TODO
 */
package com.test;


import java.io.IOException;
import java.net.URISyntaxException;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
///////// add by Yang Honggang  
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

/**
 * @author YuLong.Dai
 * @time 2018年5月3日 下午4:29:59
 */
public class CephGetUrl {
 

    public static void main(String[] args) throws IOException, SdkClientException, URISyntaxException {  
  
//     String accessKey = "17E0IMF1M9XLCL4FIXJZ";  
//     String secretKey = "Q8Gbxnl5ZDyFIXhc8a0I1swZb8pEpB9GyVRlUW80";  
//    	String accessKey = "8GNT1014BEXHUGKPIA22";  
//		  String secretKey = "r2CsQB5Me993VMkgUqnkwu51s10tHRSUoAVWwPze";
    	String accessKey = "UCWR7F6X2TUG09IL78F0";  
	 	String secretKey = "wKyxMLX833esNFiSdHBeK3bzZ28AoiWzhDlXu0QE";
     // 如果默认的端口不是80（假如是90）  
     // String endpoint = "10.41.25.35:90";  
     String endpoint = "oss.xrcloud.net";  
      
     AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);  
     ClientConfiguration clientConfig = new ClientConfiguration();  
     clientConfig.setSignerOverride("S3SignerType");  
     clientConfig.setProtocol(Protocol.HTTPS);  
     
     AmazonS3 conn = new AmazonS3Client(credentials, clientConfig);  
//     AmazonS3 conn = new AmazonS3Client(clientConfig);
     conn.setS3ClientOptions(S3ClientOptions.builder().setPathStyleAccess(true).build());
     conn.setEndpoint(endpoint);
     
     GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest("sdf150899743737", "a/b/plupload.zip");
    
     System.out.println(conn.generatePresignedUrl(request).toString()); 
     System.out.println(conn.getUrl("from-java", "firstobj")); 
     
     System.out.println("all done!");  
    }  
}
