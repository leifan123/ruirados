package com.ruirados.threads;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.ruirados.service.OssBucketService;
import com.ruirados.service.OssObjectService;


public class SynBucketCapacityRunnable implements Runnable {
	
	
	private OssBucketService ossBucketService;
	private OssObjectService ossObjectService;
	private ConcurrentLinkedQueue<String> bucketNameQueue;
	private String zoneId;
	
	

	public SynBucketCapacityRunnable(OssBucketService ossBucketService, OssObjectService ossObjectService,
			ConcurrentLinkedQueue<String> bucketNameQueue, String zoneId) {
		super();
		this.ossBucketService = ossBucketService;
		this.ossObjectService = ossObjectService;
		this.bucketNameQueue = bucketNameQueue;
		this.zoneId = zoneId;
	}

	public void run() {
		String bucketName;
		while ((bucketName = bucketNameQueue.poll()) != null) {
			Double size = ossObjectService.selectByfileName(
					"(CASE  WHEN SUM(fileSize)  IS NULL THEN 0 ELSE SUM(fileSize) END ) AS sum1 ",
					" zoneId ='" + zoneId + "'"+" and bucket_name = '"+bucketName+"'");
			ossBucketService.updateByParam("capacity =" + size + " where zoneId ='" + zoneId + "'"+" and name = '"+bucketName+"'");
		}

	}

	public OssBucketService getOssBucketService() {
		return ossBucketService;
	}

	public void setOssBucketService(OssBucketService ossBucketService) {
		this.ossBucketService = ossBucketService;
	}

	public OssObjectService getOssObjectService() {
		return ossObjectService;
	}

	public void setOssObjectService(OssObjectService ossObjectService) {
		this.ossObjectService = ossObjectService;
	}

	public ConcurrentLinkedQueue<String> getBucketNameQueue() {
		return bucketNameQueue;
	}

	public void setBucketNameQueue(ConcurrentLinkedQueue<String> bucketNameQueue) {
		this.bucketNameQueue = bucketNameQueue;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	
	
}
