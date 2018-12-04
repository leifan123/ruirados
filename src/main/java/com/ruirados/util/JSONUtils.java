package com.ruirados.util;
 

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ruirados.pojo.ReqData;
import com.ruirados.pojo.RspData;
 
 
 
  
public class JSONUtils {
 
	private static final int String = 0;

	private static Gson gson = new Gson();
	
	private static final Log log = LogFactory.getLog(JSONUtils.class);
	
	public static String addAttribute(String srcKey,Object src,Map<String,Object> pram){
	    pram.put(srcKey, src);
		return gson.toJson(pram);
	}
	
	
	public static String createObjectJson(Object object) {
		if (object != null) {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();  
			return gson.toJson(object);
		} else {
			return "{}";
		}
	}
	public static String createObjectJsonDateYearToDay(Object object) {
		if (object != null) {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();  
			return gson.toJson(object);
		} else {
			return "{}";
		}
	}
	
	public static Object getObjectByJson(String  json,Class<?> classs) {
	 
		Gson gson = new Gson();
		Object obj = gson.fromJson(json, classs);
		 
		return obj;
	}
 
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> getMapByJson(String json) {	 
		Gson gson = new Gson();
		Map<String,String> result = gson.fromJson(json, new TypeToken<Map<String,String>>(){}.getType());
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String,String> getLinkedHashMapByJson(String json) {	 
		Gson gson = new Gson();
		LinkedHashMap<String,String> result = gson.fromJson(json, new TypeToken<LinkedHashMap<String,String>>(){}.getType());
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getMapObjectByJson(String json) {	 
		Gson gson = new Gson();
		Map<String,Object> result = gson.fromJson(json, new TypeToken<Map<String,Object>>(){}.getType());
		return result;
	}
 
	
	@SuppressWarnings("unchecked")
	public static List<Map<String,String>> getListMapByJson(String json) {	 
		 
		return (List<Map<String,String>>)getObjectByJson(json, List.class);
		
	}
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> getListMapForOrderByJson(String json) {	 
		 
		return (List<Map<String,Object>>)getObjectByJson(json, List.class);
		
	}
 
	public static ReqData getReqDataByJson(String  json) {	 
		 
		return (ReqData)getObjectByJson(json, ReqData.class);
		
	}
	
	public static RspData getRspDataByJson(String  json) {	 
		 
		return (RspData)getObjectByJson(json, RspData.class);
		
	}
	
	
	public static String getJobIdMapByJson(String  json) {	 
		Gson gson = new Gson();
		Map<String,Map<String,String>> result = gson.fromJson(json, new TypeToken<Map<String,Map<String,String>>>(){}.getType());
		String resul = result.get("resizevolumeresponse").get("jobid");
		return resul;
		
	}

	public static String getJobIdMapByJson1(String type,String  json) {	 
		Gson gson = new Gson();
		Map<String,Map<String,Object>> result = gson.fromJson(json, new TypeToken<Map<String,Map<String,Object>>>(){}.getType());
		String resul = (String)result.get(type).get("jobid");
		return resul;
		
	}
	public static String getIdMapByJson(String type,String  json) {	 
		Gson gson = new Gson();
		Map<String,Map<String,String>> result = gson.fromJson(json, new TypeToken<Map<String,Map<String,String>>>(){}.getType());
		String resul = result.get(type).get("id");
		return resul;
		
	}
	
	public static String getJobIdMapByJson2(String type,String param,String  json) {	 
		Gson gson = new Gson();
		Map<String,Map<String,String>> result = gson.fromJson(json, new TypeToken<Map<String,Map<String,String>>>(){}.getType());
//		System.out.println(result);
//		System.out.println(type);
		String resul = result.get(type).get(param);
		return resul;
		
	}
	
	public static int getStatusByJson(String  json) {	 
		  int cont = json.indexOf("jobstatus");
		if(cont == -1){return -1;}
	    else{
	    	/*System.out.println(json.substring(cont+11,cont+12));*/
    	  int s = Integer.valueOf(json.substring(cont+11,cont+12));
		     return s;
		  }
	}
	
	public static String getVMidByJson(String  json) {	 
		  int startcont = json.indexOf("\"virtualmachine\":{\"id\":");
		  int endcont = json.indexOf("\",\"name\":\"");
		  String id = null;
		  if(startcont != -1 && endcont != -1){
	      id = json.substring(startcont+24, endcont);
		  }
	    return id;
	
	}
	
	public static String getSPidByJson(String  json) {	 
		  int startcont = json.indexOf("\"snapshot\":{\"id\":");
		  
		  int endcont = json.indexOf("\",\"account\":\"");
		  String id = null;
		  if(startcont != -1 && endcont != -1){
	      id = json.substring(startcont+18, endcont);
		  }
	    return id;
	
	}
	public static String getNicIpByJson(String  json,String networkid) {	
		  int startcont = json.indexOf("ipaddress", json.indexOf(networkid));

		  int endcont = json.indexOf("isolationuri", json.indexOf(networkid));
		  if(endcont==-1){
			  endcont = json.indexOf("traffictype", json.indexOf(networkid));
			}

		  String id = null;
		  if(startcont != -1 && endcont != -1){
	      id = json.substring(startcont+12, endcont-3);
		  }
	    return id;
	
	}
	public static String getIpsecKey(String  json) {	 
		  int startcont = json.indexOf("\"presharedkey\":\"");

		  int endcont = json.indexOf("\",\"account\"");
		  String id = "";
		  if(startcont != -1 && endcont != -1){
	      id = json.substring(startcont+16, endcont);
		  }
	    return id;
	
	}


	
	
	
	public static String getVMSystemUserNameByJson(String  json) {	 
		  int startcont = json.indexOf("\"ostypename\":\"");
		  int endcont = json.indexOf("\",\"account\"");
		  String SystemType = null;
		  if(startcont != -1 && endcont != -1){
			  SystemType = json.substring(startcont+14, endcont);
		  }
		  int iswindows = SystemType.indexOf("window");
		  if(iswindows == -1){
			  SystemType = "root";
		  }
		  else{
			  SystemType = "administrator"; 
		  }
	    return SystemType;
	
	}
	
	public static String getVMSystemPasswordByJson(String  json) {	 
		  int startcont = json.indexOf("\"password\":\"");
		  int endcont = json.indexOf("\",\"nic\":");
		  String password = "";
		  if(startcont != -1 && endcont != -1){
			  password = json.substring(startcont+12, endcont);
		  }
		
	    return password;
	
	}
	public static String getVMSnapshotIdByJson(String  json) {	 
		  int startcont = json.indexOf("\"vmsnapshot\":{\"id\":\"");
		  int endcont = json.indexOf("\",\"name\"");
		  String password = null;
		  if(startcont != -1 && endcont != -1){
			  password = json.substring(startcont+20, endcont);
		  }
		
	    return password;
	
	}
	public static Integer getresultStatusByJson(String  json) {	 
		  int startcont = json.indexOf("\"status\":\"");
		  String status = null;
		  if(startcont != -1){
			  status = json.substring(startcont+10, startcont+11);
			  //System.out.println(status);
		  }
	    return Integer.valueOf(status);
	
	}
	public static String getDiskIdByJson(String  json) {	 
		  int startcont = json.indexOf("\"volume\":{\"id\":\"");
		  int endcont = json.indexOf("\",\"name\"");
		  String DiskId = null;
		  if(startcont != -1 && endcont != -1){
			  DiskId = json.substring(startcont+16, endcont);
		  }
	    return DiskId;
	
	}
	public static String getVMPrivateIpAddressByJson(String  json) {	 
		  int startcont = json.indexOf("\",\"ipaddress\":\"");
		  int endcont = json.indexOf("\",\"isolationuri\":\"");
		  String PrivateIpAddress = null;
		  if(startcont != -1 && endcont != -1){
			  PrivateIpAddress = json.substring(startcont+15, endcont);
		  }
	    return PrivateIpAddress;
	
	}
	
	public static String getVMPublicIpAddressByJson(String  json) {	 
		int startcont = json.indexOf("\",\"ipaddress\":\"");
		   
		String PrivateIpAddress = null;
		 
		PrivateIpAddress = json.substring(startcont+15,json.length());
		
		PrivateIpAddress = PrivateIpAddress.substring(0, PrivateIpAddress.indexOf("\""));
		
	    return PrivateIpAddress;
	
	}
	public static String getVpcIdByJson(String  json) {	 
		int startcont = json.indexOf("\"vpc\":{\"id\":\"");
		int endcount = json.indexOf("\",\"name\""); 

		String vpcId = json.substring(startcont+13,endcount);
		
		
		
	    return vpcId;
	
	}
	public static String getNetWorkIdByJson(String  json) {	 
		int startcont = json.indexOf("\":{\"id\":\"");
		int endcount = json.indexOf("\",\"name\""); 
        if(startcont == -1 || endcount == -1){
    	  return null;
        }
		String vpcId = json.substring(startcont+9,endcount);
	    return vpcId;
	
	}
	public static String getPublicIpIdByJson(String  json) {	 
		int startcont = json.indexOf("\":{\"id\":\"");
		int endcount = json.indexOf("\",\"ipaddress\""); 
        if(startcont == -1 || endcount == -1){
    	  return null;
        }
		String vpcId = json.substring(startcont+9,endcount);
	    return vpcId;
	
	}
	public static int getenableStaticNatResultByJson(String  json) {	 
		int startcont = json.indexOf("\"success\":\"");
		int endcount = json.indexOf("\"}}"); 
        if(startcont == -1 || endcount == -1){
    	  return 2;
        }
		String vpcId = json.substring(startcont+11,endcount);
		if("true".equals(vpcId)){
			 return 1;
		}
		if("false".equals(vpcId)){
			 return 2;
		}
	    return 2;
	
	}
	public static String getInstanceNameByJson(String  json) {	 
		int startcont = json.indexOf("\"instancename\":\"");
		int endcount = json.indexOf("\",\"affinitygroup\""); 
        if(startcont == -1 || endcount == -1){
    	  return null;
        }
		String instanceName = json.substring(startcont+16,endcount);
		
	    return instanceName;
	
	}
	public static String getTemplateIdByJson(String  json) {	 
		int startcont = json.indexOf("\"template\":{\"id\":\"");
		int endcount = json.indexOf("\",\"name\":\""); 
        if(startcont == -1 || endcount == -1){
    	  return null;
        }
		String instanceName = json.substring(startcont+18,endcount);
		
	    return instanceName;
	
	}

	public static String getIntelLBSourceipaddress(String  json) {	 
		int startcont = json.indexOf("sourceipaddress\":\"");
		int endcount = json.indexOf("\",\"sourceipaddressnetworkid\":\""); 
        if(startcont == -1 || endcount == -1){
    	  return null;
        }
		String instanceName = json.substring(startcont+18,endcount);
		
	    return instanceName;
	
	}
	
	
	public static String getNicIdByJson(String  json,String networkid) {	 
		  int startcont = json.indexOf("id", json.indexOf(networkid)-80);

		  int endcont = json.indexOf("networkid", json.indexOf(networkid)-80);
		  String id = null;
		  if(startcont != -1 && endcont != -1){
	      id = json.substring(startcont+5, endcont-3);
		  }
	    return id;
	
	}
	
	
	public static Long getTemplateSizeByJson(String  json) {	 
		  int startcont = json.indexOf("\"size\":");
		  int endcont = json.indexOf("\"templatetype\"");
		  Long size = 0l;
		  if(startcont != -1 && endcont != -1){
			  //转换异常  则size为0
			  try{
			  size = Long.valueOf(json.substring(startcont+7, endcont-1));
			  }catch(Exception e){
				  log.error(e);
			  }
		  }
		 
	    return size;
	
	}
	

	public static void main(String[] args) {

			String s="{\"queryasyncjobresultresponse\":{\"accountid\":\"e488150f-21df-11e8-a07f-005056ad4e90\",\"userid\":\"e4882065-21df-11e8-a07f-005056ad4e90\",\"cmd\":\"org.apache.cloudstack.api.command.admin.vm.AddNicToVMCmdByAdmin\",\"jobstatus\":1,\"jobprocstatus\":0,\"jobresultcode\":0,\"jobresulttype\":\"object\",\"jobresult\":{\"virtualmachine\":{\"id\":\"1131fd16-64e9-42ed-a057-1e531ac968b6\",\"name\":\"VM-1131fd16-64e9-42ed-a057-1e531ac968b6\",\"displayname\":\"VM-1131fd16-64e9-42ed-a057-1e531ac968b6\",\"account\":\"admin\",\"userid\":\"e4882065-21df-11e8-a07f-005056ad4e90\",\"username\":\"admin\",\"domainid\":\"6919d17b-21db-11e8-a07f-005056ad4e90\",\"domain\":\"ROOT\",\"created\":\"2018-03-14T14:51:41+0800\",\"state\":\"Stopped\",\"haenable\":false,\"zoneid\":\"75218bb2-9bfe-4c87-91d4-0b90e86a8ff2\",\"zonename\":\"华中二区\",\"guestosid\":\"cd6af3f4-21df-11e8-a07f-005056ad4e90\",\"securitygroup\":[],\"nic\":[{\"id\":\"4099a559-b125-405d-8b95-4ddb58e419db\",\"networkid\":\"bbf43309-37c7-43fb-a600-f2b4f69aeba2\",\"networkname\":\"15089873503120180314151955\",\"netmask\":\"255.255.255.0\",\"gateway\":\"192.168.3.1\",\"ipaddress\":\"192.168.3.136\",\"traffictype\":\"Guest\",\"type\":\"Isolated\",\"isdefault\":false,\"macaddress\":\"02:00:47:94:00:10\",\"secondaryip\":[]},{\"id\":\"eb6fc846-ca25-4d8f-8e0a-081da657b0a4\",\"networkid\":\"02ea3186-c54e-4cfb-8054-cc6c4422f0ff\",\"networkname\":\"15089873503120180314145406\",\"netmask\":\"255.255.255.0\",\"gateway\":\"192.168.0.1\",\"ipaddress\":\"192.168.0.62\",\"traffictype\":\"Guest\",\"type\":\"Isolated\",\"isdefault\":true,\"macaddress\":\"02:00:28:85:00:03\",\"secondaryip\":[]},{\"id\":\"0482b9f9-ee02-4628-8ecc-cb6f33b19d2d\",\"networkid\":\"e48dfa86-b447-4f50-8dec-c362c2862878\",\"networkname\":\"15089873503120180314152018\",\"netmask\":\"255.255.255.0\",\"gateway\":\"192.168.56.1\",\"ipaddress\":\"192.168.56.141\",\"traffictype\":\"Guest\",\"type\":\"Isolated\",\"isdefault\":false,\"macaddress\":\"02:00:16:95:00:03\",\"secondaryip\":[]}],\"hypervisor\":\"VMware\",\"instancename\":\"i-2-179-VM\",\"affinitygroup\":[],\"displayvm\":true,\"isdynamicallyscalable\":false,\"ostypeid\":246,\"tags\":[]}},\"created\":\"2018-03-22T18:19:16+0800\",\"jobid\":\"b10d1811-4f36-47fe-a0b8-af635b94f455\"}}";
		
			//String s1="{\"queryasyncjobresultresponse\":{\"accountid\":\"e488150f-21df-11e8-a07f-005056ad4e90\",\"userid\":\"e4882065-21df-11e8-a07f-005056ad4e90\",\"cmd\":\"org.apache.cloudstack.api.command.admin.vm.AddNicToVMCmdByAdmin\",\"jobstatus\":1,\"jobprocstatus\":0,\"jobresultcode\":0,\"jobresulttype\":\"object\",\"jobresult\":{\"virtualmachine\":{\"id\":\"516844f6-e9b2-4494-9a8b-57aa35e99b41\",\"name\":\"VM-516844f6-e9b2-4494-9a8b-57aa35e99b41\",\"displayname\":\"VM-516844f6-e9b2-4494-9a8b-57aa35e99b41\",\"account\":\"admin\",\"userid\":\"e4882065-21df-11e8-a07f-005056ad4e90\",\"username\":\"admin\",\"domainid\":\"6919d17b-21db-11e8-a07f-005056ad4e90\",\"domain\":\"ROOT\",\"created\":\"2018-03-09T13:58:28+0800\",\"state\":\"Running\",\"haenable\":false,\"zoneid\":\"75218bb2-9bfe-4c87-91d4-0b90e86a8ff2\",\"zonename\":\"华中二区\",\"hostid\":\"f6f6a1b3-2482-4426-b7a5-502834899d54\",\"hostname\":\"172.20.19.3\",\"guestosid\":\"cd6af3f4-21df-11e8-a07f-005056ad4e90\",\"securitygroup\":[],\"nic\":[{\"id\":\"41bb5f5b-c121-4421-91ba-26cbf96dfa46\",\"networkid\":\"17c95d30-229e-4b31-a96a-743e667f1e2c\",\"networkname\":\"232323\",\"netmask\":\"255.255.255.0\",\"gateway\":\"192.168.13.1\",\"ipaddress\":\"192.168.13.66\",\"isolationuri\":\"vlan://2761\",\"broadcasturi\":\"vlan://2761\",\"traffictype\":\"Guest\",\"type\":\"Isolated\",\"isdefault\":true,\"macaddress\":\"02:00:38:f8:00:01\",\"secondaryip\":[]},{\"id\":\"b6659674-c29f-443c-bc54-e5bfb9c1196c\",\"networkid\":\"b75e7820-e22e-4ff1-a62c-0b5310fecb2b\",\"networkname\":\"sdf\",\"netmask\":\"255.255.255.0\",\"gateway\":\"192.168.66.1\",\"ipaddress\":\"192.168.66.198\",\"isolationuri\":\"vlan://3763\",\"broadcasturi\":\"vlan://3763\",\"traffictype\":\"Guest\",\"type\":\"Isolated\",\"isdefault\":false,\"macaddress\":\"02:00:24:16:00:01\",\"secondaryip\":[]}],\"hypervisor\":\"VMware\",\"instancename\":\"i-2-127-VM\",\"affinitygroup\":[],\"displayvm\":true,\"isdynamicallyscalable\":false,\"ostypeid\":246,\"tags\":[]}},\"created\":\"2018-03-22T11:32:12+0800\",\"jobid\":\"39d22c6a-94b1-439d-82eb-cfe76cf7bb40\"}}";
			

			//System.out.println(JSONUtils.getNicIpByJson(s1, "b75e7820-e22e-4ff1-a62c-0b5310fecb2b"));
	System.out.println(JSONUtils.getNicIpByJson(s, "e48dfa86-b447-4f50-8dec-c362c2862878"));
		//c3d826af-f4ab-4184-9e25-78af4b442ac3
		//c3d826af-f4ab-4184-9e25-78af4b442ac3
		//String result=  "{\"queryasyncjobresultresponse\":{\"accountid\":\"6d0fca4a-19c1-11e7-aac7-005056ac4aa8\",\"userid\":\"6d0fff1a-19c1-11e7-aac7-005056ac4aa8\",\"cmd\":\"org.apache.cloudstack.api.command.admin.address.AssociateIPAddrCmdByAdmin\",\"jobstatus\":1,\"jobprocstatus\":0,\"jobresultcode\":0,\"jobresulttype\":\"object\",\"jobresult\":{\"ipaddress\":{\"id\":\"32934837-506a-4e03-8c57-903dbbca2349\",\"ipaddress\":\"192.168.2.14\",\"allocated\":\"2017-04-27T19:03:14+0800\",\"zoneid\":\"7b899cb6-b128-4328-a820-2f765d7d74ad\",\"zonename\":\"[0xe5][0x8c][0x97][0xe4][0xba][0xac]1[0xe5][0x8c][0xba]\",\"issourcenat\":false,\"account\":\"zengxin\",\"domainid\":\"6d0fa696-19c1-11e7-aac7-005056ac4aa8\",\"domain\":\"ROOT\",\"forvirtualnetwork\":true,\"vlanid\":\"c79cbf4d-da3e-412a-a419-31c5bfa3c68e\",\"vlanname\":\"vlan://50\",\"isstaticnat\":false,\"issystem\":false,\"networkid\":\"d40c7940-45e1-45e2-b83c-b6af8671efc6\",\"state\":\"Allocated\",\"physicalnetworkid\":\"035edf8b-c9af-4dbe-8ec0-481c67b0943f\",\"vpcid\":\"4d8aec68-5ec3-41b9-baa2-9c80c86aa07d\",\"tags\":[],\"isportable\":false,\"fordisplay\":true}},\"jobinstancetype\":\"IpAddress\",\"jobinstanceid\":\"32934837-506a-4e03-8c57-903dbbca2349\",\"created\":\"2017-04-27T19:03:14+0800\",\"jobid\":\"8b8b3d2d-877b-44aa-a59d-941f6ef42e23\"}}";
	//	String re="{\"queryasyncjobresultresponse\":{\"accountid\":\"72f64aee-b01d-11e7-806c-005056934a80\",\"userid\":\"72f653f4-b01d-11e7-806c-005056934a80\",\"cmd\":\"org.apache.cloudstack.api.command.admin.template.CreateTemplateCmdByAdmin\",\"jobstatus\":1,\"jobprocstatus\":0,\"jobresultcode\":0,\"jobresulttype\":\"object\",\"jobresult\":{\"template\":{\"id\":\"68be2c31-b569-4549-bbf7-f26fbe1aebec\",\"name\":\"test\",\"displaytext\":\"abac\",\"ispublic\":false,\"created\":\"2017-10-17T15:26:17+0800\",\"isready\":true,\"passwordenabled\":false,\"format\":\"OVA\",\"isfeatured\":false,\"crossZones\":false,\"ostypeid\":\"e636a3ac-b01b-11e7-806c-005056934a80\",\"ostypename\":\"Debian GNU/Linux 5.0 (64-bit)\",\"account\":\"admin\",\"zoneid\":\"a0a7df65-dec3-48da-82cb-cff9a55a4b6d\",\"zonename\":\"北京一区\",\"status\":\"Download Complete\",\"size\":21474836480,\"templatetype\":\"USER\",\"hypervisor\":\"VMware\",\"domain\":\"ROOT\",\"domainid\":\"e633fe1e-b01b-11e7-806c-005056934a80\",\"isextractable\":true,\"sourcetemplateid\":\"655116e1-1666-49a9-809f-35290e7ac859\",\"details\":{\"Message.ReservedCapacityFreed.Flag\":\"false\",\"dataDiskController\":\"osdefault\",\"rootDiskController\":\"ide\"},\"sshkeyenabled\":false,\"isdynamicallyscalable\":false,\"tags\":[]}},\"jobinstancetype\":\"Template\",\"jobinstanceid\":\"68be2c31-b569-4549-bbf7-f26fbe1aebec\",\"created\":\"2017-10-17T15:26:17+0800\",\"jobid\":\"784c654d-a9a0-415a-a256-e384facf60ab\"}}";
		//String stri="ecb041a9-b29a-449c-800d-7cf3bb61d3d6";
		//int a =re.indexOf("ipaddress", re.indexOf(stri));
		//int b=re.indexOf("isolationuri", re.indexOf(stri));
		//System.out.println(JSONUtils.getTemplateSizeByJson(re)/1000.0/1000.0);
	//	Map<String, Object> mao=JSONUtils.getMapObjectByJson("{\"message\":\"test\",\"status\":1}");
	//	System.out.println(mao.get("status").equals(1));
		//System.out.println(re.indexOf("addd0d70-0ed0-47fb-96ec-e27fc63b969b")-50);
		//System.out.println(re.indexOf("networkid",re.indexOf("addd0d70-0ed0-47fb-96ec-e27fc63b969b")-80));
		//System.out.println(getRspDataByJson(re));	
		
	}
}
