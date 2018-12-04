package com.ruirados.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {

	public static final String  VOLUME = "硬盘";
	public static final String  SNAPSHOT = "快照";
	public static final String  VM = "主机";
	public static final String  VPC = "VPC";
	public static final String  NAT = "NAT网关";
	public static final String  NETWORK = "子网";
	public static final String  REQUEST_SUCCESS = "请求成功";
	public static final String  REQUEST_ERROR = "请求失败";
	public static final String  REQUEST_Param_IS_NULL = "请求参数不完整";
	public static final String  RESOURCE_OVER = "该区域资源不足，请更换区域或者稍后再试!";
	public static final Integer PAGE_SIZE = 15;
	public static final String  PUBLICNETWORK = "公网";
	public static final String  NOTHAVERESOURCE = "对不起，您没有该资源！";
	public static final String  FIREWALL = "防火墙";
	public static final String  LOADBALANCE = "负载均衡";
	public static final String  INTERNALLOADBALANCE = "内网负载均衡";
	public static final String  NOTHAVEZONE = "没有该区域，请检查参数！";
	public static final String  EXISTNAME = "名称重复，请重新输入!";
	public static final String  NOCHANGENAME = "你未更改名称，请重新输入!";
	public static final String  OPTIONNOOVER = "	还有其他操作未完成，待执行完成后再试!";


	public static final String  SUBNET_MASK = "255.255.255.0";
	
	public static final String  ACCOUNT = "账户";
	public static final String  GOST = "镜像";
	public static final String  CONTENT_PATH = "ruirados";
	public static final Integer OPEN = 1;
	
	public static final Integer CLOSE = 0;
	public static final String  SYS_ERROR = "平台开小差了，请稍后再试!";
	public static final String  UPVMERROR = "升级失败，该主机下存在快照,请删除后再试!";
	public static final String  VMSNAPSHOTERROR = "备份失败，该主机下挂载的磁盘存在快照,请删除后再试!";
	public static final String  NOTCURRENT = "该资源不是实时计费，不能删除!";
	public static final String  INPUT_IS_TOO_LONG = "输入的内容太长,请重新输入!";
	public static final String  DISK_SIZE_CANNOT_BE_LESS_THAN_0 = "磁盘大小不能小于0";
	
	
	public static final String  TIME_YEAR = "year";
	public static final String  TIME_MONTH = "month";
	public static final String  TIME_CURRENT = "current";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static final String  host = "云主机";
	public static final String  snapshot = "云主机快照";
	public static final String  mirror = "镜像";
	public static final String  disk = "云硬盘";
	public static final String  diskBackup = "云硬盘备份";
	public static final String  vpc = "虚拟私有云VPC";
	public static final String  nat = "NAT网关";
	public static final String  ip = "弹性IP";
	public static final String  balance = "负载均衡";
	public static final String  remoteVpn = "远程VPC";
	public static final String  tunnelVpn = "隧道VPN";
	public static final String  firewall = "防火墙";
	public static final String  recycle = "回收站";
	public static final String  userCenter = "用户中心";
	public static final String  work = "工单";
	
	
	
    private static Properties properties = new Properties();
 
    public static String getString(String key, String defaultValue) {
        String result = properties.getProperty(key, defaultValue);
        return (result != null) ? result.trim() : null;
    }

    public static Boolean getBoolean(String key, Boolean defaultValue) {
        String valueString = properties.getProperty(key).trim();
        return valueString.equals("true");
    }

    public static String[] getList(String key) {
        String[] list = new String[0];
        String vals = properties.getProperty(key).trim();

        if (vals.length() > 0) {
            list = vals.split(",");
        }

        return list;
    }

    public static int getInt(String key, int defaultInt) {
        String val = properties.getProperty(key);

        if (val == null || val.length() == 0)
            return defaultInt;
        return Integer.valueOf(val.trim());
    }

    public static Long getLong(String key, Long defaultLong) {
        String val = properties.getProperty(key);

        if (val == null || val.length() == 0)
            return defaultLong;
        return Long.valueOf(val.trim());
    }

    public static double getDouble(String key, double defaultInt) {
        String val = properties.getProperty(key);

        if (val == null || val.length() == 0)
            return defaultInt;
        return Double.valueOf(val.trim());
    }

}
