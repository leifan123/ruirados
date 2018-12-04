package com.ruirados.pojo;

import java.util.Date;

import java.io.Serializable;

public class OssOperatelog  implements Serializable{

	private static final long serialVersionUID = 1526355857418418033L;
	private Integer id;

	public OssOperatelog setId(Integer id){
		 this.id = id;
		 return this;
	}

	public Integer getId(){
		 return id;
	}
	private String companyid;

	public OssOperatelog setCompanyid(String companyid){
		 this.companyid = companyid;
		 return this;
	}

	public String getCompanyid(){
		 return companyid;
	}
	private String operator;

	public OssOperatelog setOperator(String operator){
		 this.operator = operator;
		 return this;
	}

	public String getOperator(){
		 return operator;
	}
	private String operatorip;

	public OssOperatelog setOperatorip(String operatorip){
		 this.operatorip = operatorip;
		 return this;
	}

	public String getOperatorip(){
		 return operatorip;
	}
	private String operatetarget;

	public OssOperatelog setOperatetarget(String operatetarget){
		 this.operatetarget = operatetarget;
		 return this;
	}

	public String getOperatetarget(){
		 return operatetarget;
	}
	private String operatetype;

	public OssOperatelog setOperatetype(String operatetype){
		 this.operatetype = operatetype;
		 return this;
	}

	public String getOperatetype(){
		 return operatetype;
	}
	private String operatedes;

	public OssOperatelog setOperatedes(String operatedes){
		 this.operatedes = operatedes;
		 return this;
	}

	public String getOperatedes(){
		 return operatedes;
	}
	private Integer operatestatus;

	public OssOperatelog setOperatestatus(Integer operatestatus){
		 this.operatestatus = operatestatus;
		 return this;
	}

	public Integer getOperatestatus(){
		 return operatestatus;
	}
	private String errormessage;

	public OssOperatelog setErrormessage(String errormessage){
		 this.errormessage = errormessage;
		 return this;
	}

	public String getErrormessage(){
		 return errormessage;
	}
	private Date operatortime;

	public OssOperatelog setOperatortime(Date operatortime){
		 this.operatortime = operatortime;
		 return this;
	}

	public Date getOperatortime(){
		 return operatortime;
	}
	private String zoneid;

	public OssOperatelog setZoneid(String zoneid){
		 this.zoneid = zoneid;
		 return this;
	}

	public String getZoneid(){
		 return zoneid;
	}
	private String remark;

	public OssOperatelog setRemark(String remark){
		 this.remark = remark;
		 return this;
	}

	public String getRemark(){
		 return remark;
	}
}