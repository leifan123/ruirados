package com.ruirados.pojo;

import java.util.Date;

import java.io.Serializable;

public class OssObject  implements Serializable{

	private static final long serialVersionUID = 1526355857418516373L;
	private Integer id;

	public OssObject setId(Integer id){
		 this.id = id;
		 return this;
	}

	public Integer getId(){
		 return id;
	}
	private String code;

	public OssObject setCode(String code){
		 this.code = code;
		 return this;
	}

	public String getCode(){
		 return code;
	}
	private String companyid;

	public OssObject setCompanyid(String companyid){
		 this.companyid = companyid;
		 return this;
	}

	public String getCompanyid(){
		 return companyid;
	}
	private Integer version;

	public OssObject setVersion(Integer version){
		 this.version = version;
		 return this;
	}

	public Integer getVersion(){
		 return version;
	}
	private Integer isdisplay;

	public OssObject setIsdisplay(Integer isdisplay){
		 this.isdisplay = isdisplay;
		 return this;
	}

	public Integer getIsdisplay(){
		 return isdisplay;
	}
	private String filename;

	public OssObject setFilename(String filename){
		 this.filename = filename;
		 return this;
	}

	public String getFilename(){
		 return filename;
	}
	private String filesrc;

	public OssObject setFilesrc(String filesrc){
		 this.filesrc = filesrc;
		 return this;
	}

	public String getFilesrc(){
		 return filesrc;
	}
	private Integer filetype;

	public OssObject setFiletype(Integer filetype){
		 this.filetype = filetype;
		 return this;
	}

	public Integer getFiletype(){
		 return filetype;
	}
	private Double filesize;

	public OssObject setFilesize(Double size){
		 this.filesize = size;
		 return this;
	}

	public Double getFilesize(){
		 return filesize;
	}
	private Integer isfile;

	public OssObject setIsfile(Integer isfile){
		 this.isfile = isfile;
		 return this;
	}

	public Integer getIsfile(){
		 return isfile;
	}
	private Integer fId;

	public OssObject setFId(Integer fId){
		 this.fId = fId;
		 return this;
	}

	public Integer getFId(){
		 return fId;
	}
	private Integer bucketId;

	public OssObject setBucketId(Integer bucketId){
		 this.bucketId = bucketId;
		 return this;
	}

	public Integer getBucketId(){
		 return bucketId;
	}
	private String bucketName;

	public OssObject setBucketName(String bucketName){
		 this.bucketName = bucketName;
		 return this;
	}

	public String getBucketName(){
		 return bucketName;
	}
	private String zoneid;

	public OssObject setZoneid(String zoneid){
		 this.zoneid = zoneid;
		 return this;
	}

	public String getZoneid(){
		 return zoneid;
	}
	private Date createtime;

	public OssObject setCreatetime(Date createtime){
		 this.createtime = createtime;
		 return this;
	}

	public Date getCreatetime(){
		 return createtime;
	}
	private Date lasttime;

	public OssObject setLasttime(Date lasttime){
		 this.lasttime = lasttime;
		 return this;
	}

	public Date getLasttime(){
		 return lasttime;
	}
	private Integer accessrights;

	public OssObject setAccessrights(Integer accessrights){
		 this.accessrights = accessrights;
		 return this;
	}

	public Integer getAccessrights(){
		 return accessrights;
	}
	private Integer state;

	public OssObject setState(Integer state){
		 this.state = state;
		 return this;
	}

	public Integer getState(){
		 return state;
	}
	private String remark;

	public OssObject setRemark(String remark){
		 this.remark = remark;
		 return this;
	}

	public String getRemark(){
		 return remark;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OssObject [id=" + id + ", code=" + code + ", companyid=" + companyid + ", version=" + version
				+ ", isdisplay=" + isdisplay + ", filename=" + filename + ", filesrc=" + filesrc + ", filetype="
				+ filetype + ", filesize=" + filesize + ", isfile=" + isfile + ", fId=" + fId + ", bucketId=" + bucketId
				+ ", bucketName=" + bucketName + ", zoneid=" + zoneid + ", createtime=" + createtime + ", lasttime="
				+ lasttime + ", accessrights=" + accessrights + ", state=" + state + ", remark=" + remark + "]";
	}
	
	
}