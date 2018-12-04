package com.ruirados.pojo;

import java.io.Serializable;
import java.util.Map;

/**
 * 请求报文结构
 * @author yunrui003
 *
 */
public class ReqData  implements Serializable{
	
	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	private static final long serialVersionUID = 8256714125084787191L;

	private String sysno;
	
	private String token;
	
	private String encoding;
	
	private String reqdata;
	
	private String reqtime;
	
	private String operator;
	
	private Map<String,String> data;

	public String getSysno() {
		return sysno;
	}

	public ReqData setSysno(String sysno) {
		this.sysno = sysno;
		return this;
	}

	public String getToken() {
		return token;
	}

	public ReqData setToken(String token) {
		this.token = token;
		return this;
	}

	public String getEncoding() {
		return encoding;
	}

	public ReqData setEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}

	public String getReqdata() {
		return reqdata;
	}

	public ReqData setReqdata(String reqdata) {
		this.reqdata = reqdata;
		return this;
	}

	public String getReqtime() {
		return reqtime;
	}

	public ReqData setReqtime(String reqtime) {
		this.reqtime = reqtime;
		return this;
	}

	public String getOperator() {
		return operator;
	}

	public ReqData setOperator(String operator) {
		this.operator = operator;
		return this;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
	  
}
