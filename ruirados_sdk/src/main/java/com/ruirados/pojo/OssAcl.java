package com.ruirados.pojo;

import java.util.Date;

import java.io.Serializable;

public class OssAcl  implements Serializable{

	private static final long serialVersionUID = 1526355714628800226L;
	private Integer id;

	public OssAcl setId(Integer id){
		 this.id = id;
		 return this;
	}

	public Integer getId(){
		 return id;
	}
	private String code;

	public OssAcl setCode(String code){
		 this.code = code;
		 return this;
	}

	public String getCode(){
		 return code;
	}
	private String companyid;

	public OssAcl setCompanyid(String companyid){
		 this.companyid = companyid;
		 return this;
	}

	public String getCompanyid(){
		 return companyid;
	}
	private Integer bucketId;

	public OssAcl setBucketId(Integer bucketId){
		 this.bucketId = bucketId;
		 return this;
	}

	public Integer getBucketId(){
		 return bucketId;
	}
	private String zoneid;

	public OssAcl setZoneid(String zoneid){
		 this.zoneid = zoneid;
		 return this;
	}

	public String getZoneid(){
		 return zoneid;
	}
	private Integer version;

	public OssAcl setVersion(Integer version){
		 this.version = version;
		 return this;
	}

	public Integer getVersion(){
		 return version;
	}
	private Integer isdisplay;

	public OssAcl setIsdisplay(Integer isdisplay){
		 this.isdisplay = isdisplay;
		 return this;
	}

	public Integer getIsdisplay(){
		 return isdisplay;
	}
	private String userauthorization;

	public OssAcl setUserauthorization(String string){
		 this.userauthorization = string;
		 return this;
	}

	public String getUserauthorization(){
		 return userauthorization;
	}
	private Integer putobject;

	public OssAcl setPutobject(Integer putobject){
		 this.putobject = putobject;
		 return this;
	}

	public Integer getPutobject(){
		 return putobject;
	}
	private Integer getobject;

	public OssAcl setGetobject(Integer getobject){
		 this.getobject = getobject;
		 return this;
	}

	public Integer getGetobject(){
		 return getobject;
	}
	private Integer deleteobject;

	public OssAcl setDeleteobject(Integer deleteobject){
		 this.deleteobject = deleteobject;
		 return this;
	}

	public Integer getDeleteobject(){
		 return deleteobject;
	}
	private Integer listbucket;

	public OssAcl setListbucket(Integer listbucket){
		 this.listbucket = listbucket;
		 return this;
	}

	public Integer getListbucket(){
		 return listbucket;
	}
	private Integer deletebucket;

	public OssAcl setDeletebucket(Integer deletebucket){
		 this.deletebucket = deletebucket;
		 return this;
	}

	public Integer getDeletebucket(){
		 return deletebucket;
	}
	private String resource;

	public OssAcl setResource(String resource){
		 this.resource = resource;
		 return this;
	}

	public String getResource(){
		 return resource;
	}
	private Integer iseffectres;

	public OssAcl setIseffectres(Integer iseffectres){
		 this.iseffectres = iseffectres;
		 return this;
	}

	public Integer getIseffectres(){
		 return iseffectres;
	}
	private String refererip;

	public OssAcl setRefererip(String refererip){
		 this.refererip = refererip;
		 return this;
	}

	public String getRefererip(){
		 return refererip;
	}
	private Integer iseffectrefip;

	public OssAcl setIseffectrefip(Integer iseffectrefip){
		 this.iseffectrefip = iseffectrefip;
		 return this;
	}

	public Integer getIseffectrefip(){
		 return iseffectrefip;
	}
	private Integer state;

	public OssAcl setState(Integer state){
		 this.state = state;
		 return this;
	}

	public Integer getState(){
		 return state;
	}
	private Date createtime;

	public OssAcl setCreatetime(Date createtime){
		 this.createtime = createtime;
		 return this;
	}

	public Date getCreatetime(){
		 return createtime;
	}
	private String remark;

	public OssAcl setRemark(String remark){
		 this.remark = remark;
		 return this;
	}

	public String getRemark(){
		 return remark;
	}
}