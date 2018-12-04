package com.yunrui.pojo;
import java.io.Serializable;
public class Getfreevminformation  implements Serializable{
private static final long serialVersionUID = 8270746988420006L;

	private Integer id;

	public Getfreevminformation setId(Integer id){
		 this.id = id;
		 return this;
	}

	public Integer getId(){
		 return id;
	}
	
	private String createtime;

	public Getfreevminformation setCreatetime(String createtime){
		 this.createtime = createtime;
		 return this;
	}

	public String getCreatetime(){
		 return createtime;
	}
	private String companyid;

	public Getfreevminformation setCompanyid(String companyid){
		 this.companyid = companyid;
		 return this;
	}

	public String getCompanyid(){
		 return companyid;
	}
	private String companyname;

	public Getfreevminformation setCompanyname(String companyname){
		 this.companyname = companyname;
		 return this;
	}

	public String getCompanyname(){
		 return companyname;
	}
	private Integer companytype;

	public Getfreevminformation setCompanytype(Integer companytype){
		 this.companytype = companytype;
		 return this;
	}

	public Integer getCompanytype(){
		 return companytype;
	}
	private String computerid;

	public Getfreevminformation setComputerid(String computerid){
		 this.computerid = computerid;
		 return this;
	}

	public String getComputerid(){
		 return computerid;
	}
	private String computername;

	public Getfreevminformation setComputername(String computername){
		 this.computername = computername;
		 return this;
	}

	public String getComputername(){
		 return computername;
	}
	private String computerinstancename;

	public Getfreevminformation setComputerinstancename(String computerinstancename){
		 this.computerinstancename = computerinstancename;
		 return this;
	}

	public String getComputerinstancename(){
		 return computerinstancename;
	}
	private Integer activitynum;

	public Getfreevminformation setActivitynum(Integer activitynum){
		 this.activitynum = activitynum;
		 return this;
	}

	public Integer getActivitynum(){
		 return activitynum;
	}
	private Integer costflag;

	public Getfreevminformation setCostflag(Integer costflag){
		 this.costflag = costflag;
		 return this;
	}

	public Integer getCostflag(){
		 return costflag;
	}
	private Integer vmconfigid;

	public Getfreevminformation setVmconfigid(Integer vmconfigid){
		 this.vmconfigid = vmconfigid;
		 return this;
	}

	public Integer getVmconfigid(){
		 return vmconfigid;
	}
	private String phone;

	public Getfreevminformation setPhone(String phone){
		 this.phone = phone;
		 return this;
	}

	public String getPhone(){
		 return phone;
	}
}