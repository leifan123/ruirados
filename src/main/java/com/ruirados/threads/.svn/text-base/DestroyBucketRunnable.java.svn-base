/**
 * NewHeight.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.ruirados.threads;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ruirados.bean.DestroyBucketBean;
import com.ruirados.util.BucketAclUtil;
import com.ruirados.util.GlobalAttr;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author yunrui006
 * @version $Id: DestroyBucketRunnable.java, v 0.1 2018年6月25日 下午5:07:32 yunrui006 Exp $
 */
public class DestroyBucketRunnable implements Runnable {

	private Logger log = Logger.getLogger(DestroyBucketRunnable.class);
	public void run() {
		while(true){
			try {
				ConcurrentLinkedQueue<DestroyBucketBean> destroyBucketqueue = GlobalAttr.getInstance().getDestroyBucketqueue();
				DestroyBucketBean destroyBucketBean = destroyBucketqueue.poll();
				if(destroyBucketBean != null){
					String companyId = destroyBucketBean.getCompanyId();
					String zoneId = destroyBucketBean.getZoneId();
					String bucketName = destroyBucketBean.getBucketName();
					AmazonS3 conn = BucketAclUtil.getConnByCache(companyId, zoneId, 0, true);
					ObjectListing exitedObjects = conn.listObjects(bucketName);
					List<S3ObjectSummary> sums = exitedObjects.getObjectSummaries();
					for (S3ObjectSummary s3 : sums) {
						conn.deleteObject(bucketName, s3.getKey());
					}
					conn.deleteBucket(bucketName);
				} else {
					Thread.sleep(5000);
				}
				
			} catch (Exception e) {
				log.error(e);
			}
		}
	}

}
