package com.ruirados.pojo;

import java.util.Date;

import java.io.Serializable;

public class OssBucket  implements Serializable{

	private static final long serialVersionUID = 1526355714628382124L;
	private Integer id;

	public OssBucket setId(Integer id){
		 this.id = id;
		 return this;
	}

	public Integer getId(){
		 return id;
	}
	private String code;

	public OssBucket setCode(String code){
		 this.code = code;
		 return this;
	}

	public String getCode(){
		 return code;
	}
	private String companyid;

	public OssBucket setCompanyid(String companyid){
		 this.companyid = companyid;
		 return this;
	}

	public String getCompanyid(){
		 return companyid;
	}
	private Integer version;

	public OssBucket setVersion(Integer version){
		 this.version = version;
		 return this;
	}

	public Integer getVersion(){
		 return version;
	}
	private Integer isdisplay;

	public OssBucket setIsdisplay(Integer isdisplay){
		 this.isdisplay = isdisplay;
		 return this;
	}

	public Integer getIsdisplay(){
		 return isdisplay;
	}
	private String name;

	public OssBucket setName(String name){
		 this.name = name;
		 return this;
	}

	public String getName(){
		 return name;
	}
	private Integer accessrights;

	public OssBucket setAccessrights(Integer accessrights){
		 this.accessrights = accessrights;
		 return this;
	}

	public Integer getAccessrights(){
		 return accessrights;
	}
	private String zoneid;

	public OssBucket setZoneid(String zoneid){
		 this.zoneid = zoneid;
		 return this;
	}

	public String getZoneid(){
		 return zoneid;
	}
	private Integer state;

	public OssBucket setState(Integer state){
		 this.state = state;
		 return this;
	}

	public Integer getState(){
		 return state;
	}
	private Date createtime;

	public OssBucket setCreatetime(Date createtime){
		 this.createtime = createtime;
		 return this;
	}

	public Date getCreatetime(){
		 return createtime;
	}
	private Date lasttime;

	public OssBucket setLasttime(Date lasttime){
		 this.lasttime = lasttime;
		 return this;
	}

	public Date getLasttime(){
		 return lasttime;
	}
	private String remark;

	public OssBucket setRemark(String remark){
		 this.remark = remark;
		 return this;
	}

	public String getRemark(){
		 return remark;
	}

	@Override
	public String toString() {
		return "OssBucket [id=" + id + ", code=" + code + ", companyid=" + companyid + ", version=" + version
				+ ", isdisplay=" + isdisplay + ", name=" + name + ", accessrights=" + accessrights + ", zoneid="
				+ zoneid + ", state=" + state + ", createtime=" + createtime + ", lasttime=" + lasttime + ", remark="
				+ remark + "]";
	}
	
}