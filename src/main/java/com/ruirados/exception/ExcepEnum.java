/**
 * @author YuLong.Dai
 * @time Sep 28, 2016 4:21:47 PM
 * TODO
 */
package com.ruirados.exception;

/**
 * @author YuLong.Dai
 * @time Sep 28, 2016 4:21:47 PM
 */
public enum ExcepEnum {

		//请求成功
		SUCCESS("AAAAAAA")
		/**
		 * 系统类错误
		 */
		//系统内部异常
		,ERROR_SY_500("ESY0001")
		//未查询到信息
	    ,ERROR_SY_NOMSG("ESY0002")
	    //参数异常
	    ,ERROR_SY_PARA_EXCEPTION("ESY0003")
	    //字段为必传字段,不能为空
	    ,ERROR_SY_ISNOTNULL("ESY0004")
	    //操作非法
	    ,ERROR_SY_ILLEGAL("ESY0005")
		/**
		 * 客户类错误
		 */
		//当前用户不存在
		,ERROR_CM_NOUSER("ECM0001")
		//用户名密码错误
		,ERROR_CM_PW("ECM0002")
		//用户状态异常,请联系客服
		,ERROR_CM_STATUS("ECM0003")
		//用户状态异常,请联系客服
		,ERROR_CM_CZUSER("ECM0004")
		//当前用户已认证,不能重复认证
		,ERROR_CM_GRRZCF("ECM0005")
		//当前企业已认证,不能重复认证
		,ERROR_CM_QYRZCF("ECM0006")
		//用户密保校验失败
		,ERROR_CM_MBYZ("ECM0007")
		//用户密钥校验失败
		,ERROR_CM_MYSB("ECM0008")
		//用户密钥超时
		,ERROR_CM_MYCS("ECM0009")
		/**
		 * PORTALONE
		 */
		//当前企业已认证,不能重复认证
		,ERROR_PT_MODISBUY("EPT0001")
		//当前用户上传权素材限被禁用
		,ERROR_PT_SCSCJY("EPT0002")	
		//当前用户存储空间不足
		,ERROR_PT_USERCCBZ("EPT0003")	
		/**
		 * 广告池类错误
		 */
		//保证金余额不足
		,ERROR_AD_BAIL_NOT_ENOUGH("EAD0001")
 
		/**
	     * 财务类错误
	     */
		//当前企业支付状态异常
	   ,ERROR_AC_ZFSTATUS("EAC0001")
	 	//调用微信组件失败
	   ,ERROR_AC_WXSMZF("EAC0002")  
	   //当前流水号不存在
	   ,ERROR_AC_LSHNOTFIND("EAC0003")  
	   //调用支付宝组件失败
	   ,ERROR_AC_ZFBZF("EAC0004")  
	 	/**
	     * 权限角色错误
	     */
		//角色不存在
	   ,ERROR_RP_ROLE_NOT_EXIST("ERP0001")
	   //该用户名已经存在
	   ,ERROR_RP_ROL_AL_EXIST("ERP0002")
	    /**
	     * 日志错误
	     */
	    
	   /**
	     * 用户中心错误
	     */
	   //企业用户不存在
	   ,ERROR_UC_NOUSER("EUC0001")
	   //企业用户账户不存在
	   ,ERROR_UC_NOACCOUNT("EUC0002")
	   //该分组不存在
	   ,ERROR_UC_NOSUCHGROUP("EUC0003")
	    /**
	     * 短信邮箱
	     */
	   //用户不存在
	   ,ERROR_SE_NOUSER("ESE0001")
	   //短信套餐不存在
	   ,ERROR_SE_NOSPAC("ESE0002")
	   //企业用户余额不足
	   ,ERROR_SE_BAIL_NOT_ENOUGH("ESE0003")
	   	//邮件发送失败
	   ,ERROR_SE_SENDEMAIL("ESE0004")
	   	//短信发送失败
	   ,ERROR_SE_SENDSMS("ESE0005")
	   //剩余短信不足
	   ,ERROR_REMAIDER_SMS_NOT_ENOUGH("ESE0006")
	   /**
	     * 提示用户语句——曾鑫
	     */
	   //参数为空
	   ,ERROR_YR_REQUEST_Param_IS_NULL("EYR0001")
	   //没有该资源
	   ,ERROR_YR_NOTHAVERESOURCE("EYR0002")
	   //系统错误
	   ,ERROR_YR_SYS_ERROR("EYR0003")
	   //输入内容超长，请重新输入
	   ,ERROR_YR_CONTENT_IS_LONG("EYR0004")
	   //已经存在该名称
	   ,ERROR_YR_NAME_ALREADY_EXIST("EYR0005")
	   //资源不存在货不是实时计费
	   ,ERROR_YR_NOT_EXIST_OR_NOT_CURRENT("EYR0006")
	   //您创建的资源数量达到最大，如需再创建请提交工单
	   ,ERROR_YR_RESOURCE_IS_FULL("EYR0007")
	   //该磁盘挂载的虚拟机有备份，请将备份删除后再试
	   ,ERROR_YR_VM_HAVE_SNAPSHOT("EYR0008")
	   //该磁盘已经挂载主机!
	   ,ERROR_YR_DISK_ALREADY_ATTACH("EYR0009")
	   //负载均衡规则名称或者公有端口重复，请从新输入!
	   ,ERROR_YR_LOADBALANCE_NAME_ALREADY_EXIST("EYR0010")
	   //负载均衡下有主机，不能删除
	   ,ERROR_YR_LOADBALANCE_HAVE_VM("EYR0011")
	   //不能删除VPC，该VPC下含有子网,请删除子网后再试!
	   ,ERROR_YR_NOT_DEL_VPC_HAVE_NETWORK("EYR0012")
	   //不能删除vpc，该vpc下含有公网ip,请删除公网ip后再试!
	   ,ERROR_YR_NOT_DEL_VPC_HAVE_PUBLIC("EYR0013")
	   //该虚拟机已经在子网内!
	   ,ERROR_YR_VM_ALREADY_IN_NETWORK("EYR0014")
	   //该网卡是虚拟机的主网卡，请将其设置为非主网卡再试!
	   ,ERROR_YR_IS_MAIN_CARDNETWORK("EYR0015")
	   //处在该网络的虚拟机的网卡已经绑定公网ip，请解绑公网ip后再试！
	   ,ERROR_YR_ALREADY_BAND_PUBLIC("EYR0016")
	 //处在该网络的虚拟机的网卡存在端口转发，请删除端口转发后再试
	   ,ERROR_YR_ALREADY_HAVE_PORTTRACE("EYR0017")
		 //该网络下还有虚拟机，请移除后再试!
	   ,ERROR_YR_ALREADY_HAVE_VM("EYR0018")
		 //该公网的端口已经存在，请换一个端口
	   ,ERROR_YR_PUBLICPORT_IS_EXIST("EYR0019")
		 //该防火墙还有未删除的规则，不能删除！
	   ,ERROR_YR_HAVE_ACLLIST("EYR0020")
		 //还有子网应用这个防火墙，不能删除,子网请更换防火墙后再试!
	   ,ERROR_YR_NETWORK_USE_FIRE("EYR0021")
		 //你输入的优先级已经存在，请换一个优先级!
	   ,ERROR_YR_ALREADY_FIRST("EYR0022")
		;
		//
	    public String VAL;
	 
	    private ExcepEnum(String value) {
	        this.VAL = value;
	    }
 
}
