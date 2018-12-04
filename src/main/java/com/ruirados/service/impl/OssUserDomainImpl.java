package com.ruirados.service.impl;

import com.ruirados.pojo.OssUserDomain;
import com.ruirados.dao.OssUserDomainDao;
import com.ruirados.service.OssUserDomainService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OssUserDomainImpl implements OssUserDomainService {

	private OssUserDomainDao ossUserDomainDao;

	public void setOssUserDomainDao(OssUserDomainDao ossUserDomainDao){
		 this.ossUserDomainDao = ossUserDomainDao;
	}

	public void insert(OssUserDomain ossuserdomain){
		ossUserDomainDao.insert(ossuserdomain);
	}

	public List<OssUserDomain> select(OssUserDomain ossuserdomain){
		return ossUserDomainDao.select(ossuserdomain);
	}

	public void update(OssUserDomain ossuserdomain){
		ossUserDomainDao.update(ossuserdomain);
	}

	public void delete(OssUserDomain ossuserdomain){
		ossUserDomainDao.delete(ossuserdomain);
	}

	public List<OssUserDomain> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return ossUserDomainDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  ossUserDomainDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossUserDomainDao.deleteByParam(map);
	}

}

