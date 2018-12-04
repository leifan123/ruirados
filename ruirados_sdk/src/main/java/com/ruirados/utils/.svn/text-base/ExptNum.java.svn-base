/**
 * NewHeight.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.ruirados.utils;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author yunrui006
 * @version $Id: SystemStatus.java, v 0.1 2018年5月17日 下午5:59:54 yunrui006 Exp $
 */
/**
 * <pre>
 * 
 * </pre>
 *
 * @author yunrui006
 * @version $Id: ExptNum.java, v 0.1 2018年5月17日 下午6:05:43 yunrui006 Exp $
 */
public enum ExptNum {
	
	SUCCESS(1,"处理成功"),
	FAIL(2,"失败"),
	USED(3,"当前空间名称已被使用，请重新输入！"),
	DONTHAVE(4,"该区域，您还没创建空间！"),
	NOT_LOGIN(5,"没有登录"),
	DFAIL(6,"删除失败，请确认该空间下没有任何文件！"),
	ERROR_NO_MONEY(7,"余额不足"),
	IPNOACL(8,"该Ip地址没有权限！"),
	PARAM_NO_ALL(9,"参数不完整"),
	WARN_STATUS(10,"询问"),
	TOO_MANY_WORDS(11,"字数过多"),
	FILE_NAME_REPEAT(12,"名称已存在，请重新输入"),
	IT_HAS_SONFILE(13,"删除失败，请确认该文件下没有任何其他文件夹或文件"),
	TOO_MANY_WORDS_20(13,"上传失败，文件名称不能大于等于20个字符"),
	FILE_TOO_BIG(14,"上传失败，文件太大，不能超过5000000bytes"),
	NO_BUCKETNAME(15,"该BucketName不存在，请重新创建"),
	EMPTY(16, "暂无数据"),
	NO_JURISDICTION(17, "没有权限"),
	NO_ACCESSSECRET(18, "当前用户没有Access Key"),
	;
	



	//错误码
	private int code;
	//描述
	private String desc;
	private ExptNum(int code, String desc) {
		this.code = code;
		this.setDesc(desc);
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	

}
