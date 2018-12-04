package com.ruirados.pojo;

import java.util.Date;

import java.io.Serializable;

public class OssUserFlux  implements Serializable{

	private static final long serialVersionUID = 1537252862851154638L;
	private Integer id;

	public OssUserFlux setId(Integer id){
		 this.id = id;
		 return this;
	}

	public Integer getId(){
		 return id;
	}
	private String companyid;

	public OssUserFlux setCompanyid(String companyid){
		 this.companyid = companyid;
		 return this;
	}

	public String getCompanyid(){
		 return companyid;
	}
	private Integer fluxId;

	public OssUserFlux setFluxId(Integer fluxId){
		 this.fluxId = fluxId;
		 return this;
	}

	public Integer getFluxId(){
		 return fluxId;
	}
	private Date expiretime;

	public OssUserFlux setExpiretime(Date expiretime){
		 this.expiretime = expiretime;
		 return this;
	}

	public Date getExpiretime(){
		 return expiretime;
	}
	private double originprice;

	public OssUserFlux setOriginprice(double originprice){
		 this.originprice = originprice;
		 return this;
	}

	public double getOriginprice(){
		 return originprice;
	}
	private double realprice;

	public OssUserFlux setRealprice(double realprice){
		 this.realprice = realprice;
		 return this;
	}

	public double getRealprice(){
		 return realprice;
	}
	private String zoneid;

	public OssUserFlux setZoneid(String zoneid){
		 this.zoneid = zoneid;
		 return this;
	}

	public String getZoneid(){
		 return zoneid;
	}
	private Date createtime;

	public OssUserFlux setCreatetime(Date createtime){
		 this.createtime = createtime;
		 return this;
	}

	public Date getCreatetime(){
		 return createtime;
	}
	
	private String name;
	private Integer size;
	private Integer timeType;
	private Integer expiredays;
	private Integer zoneId;

	public String getName() {
		return name;
	}

	public OssUserFlux setName(String name) {
		this.name = name;
		return this;
	}

	public Integer getSize() {
		return size;
	}

	public OssUserFlux setSize(Integer size) {
		this.size = size;
		return this;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public OssUserFlux setTimeType(Integer timeType) {
		this.timeType = timeType;
		return this;
	}

	public Integer getExpiredays() {
		return expiredays;
	}

	public OssUserFlux setExpiredays(Integer expiredays) {
		this.expiredays = expiredays;
		return this;
	}

	public Integer getZoneId() {
		return zoneId;
	}

	public OssUserFlux setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
		return this;
	}
	
}