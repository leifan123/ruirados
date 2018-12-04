package com.ruirados.pojo;

import java.util.Date;

import java.io.Serializable;

public class OssActivity  implements Serializable{

	private static final long serialVersionUID = 1538982949929525817L;
	private Integer id;

	public OssActivity setId(Integer id){
		 this.id = id;
		 return this;
	}

	public Integer getId(){
		 return id;
	}
	private String name;

	public OssActivity setName(String name){
		 this.name = name;
		 return this;
	}

	public String getName(){
		 return name;
	}
	private Integer size;

	public OssActivity setSize(Integer size){
		 this.size = size;
		 return this;
	}

	public Integer getSize(){
		 return size;
	}
	private Float price;

	public OssActivity setPrice(Float price){
		 this.price = price;
		 return this;
	}

	public Float getPrice(){
		 return price;
	}
	private Integer expiredays;

	public OssActivity setExpiredays(Integer expiredays){
		 this.expiredays = expiredays;
		 return this;
	}

	public Integer getExpiredays(){
		 return expiredays;
	}
	private Date createtime;

	public OssActivity setCreatetime(Date createtime){
		 this.createtime = createtime;
		 return this;
	}

	public Date getCreatetime(){
		 return createtime;
	}
	private String zoneid;

	public OssActivity setZoneid(String zoneid){
		 this.zoneid = zoneid;
		 return this;
	}

	public String getZoneid(){
		 return zoneid;
	}
	private Integer status;

	public OssActivity setStatus(Integer status){
		 this.status = status;
		 return this;
	}

	public Integer getStatus(){
		 return status;
	}
}