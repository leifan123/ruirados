/**
 * NewHeight.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.ruirados.pojo;

import java.util.Date;

import com.amazonaws.services.s3.AmazonS3;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author yunrui006
 * @version $Id: AmazonCacheBean.java, v 0.1 2018年6月7日 下午1:45:28 yunrui006 Exp $
 */
public class AmazonCacheBean {
	/**
	 * @param amazonConn
	 * @param expireTime
	 */
	public AmazonCacheBean(AmazonS3 amazonConn, Date expireTime) {
		super();
		AmazonConn = amazonConn;
		this.expireTime = expireTime;
	}
	private AmazonS3 AmazonConn;
	private Date expireTime;
	
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	public AmazonS3 getAmazonConn() {
		return AmazonConn;
	}
	public void setAmazonConn(AmazonS3 amazonConn) {
		AmazonConn = amazonConn;
	}
}
