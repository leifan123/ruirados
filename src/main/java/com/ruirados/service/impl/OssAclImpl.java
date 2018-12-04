package com.ruirados.service.impl;

import com.ruirados.pojo.OssAcl;
import com.ruirados.dao.OssAclDao;
import com.ruirados.service.OssAclService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OssAclImpl implements OssAclService {

	private OssAclDao ossAclDao;

	public void setOssAclDao(OssAclDao ossAclDao){
		 this.ossAclDao = ossAclDao;
	}

	public void insert(OssAcl ossacl){
		ossAclDao.insert(ossacl);
	}

	public List<OssAcl> select(OssAcl ossacl){
		return ossAclDao.select(ossacl);
	}

	public void update(OssAcl ossacl){
		ossAclDao.update(ossacl);
	}

	public void delete(OssAcl ossacl){
		ossAclDao.delete(ossacl);
	}

	public List<OssAcl> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return ossAclDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossAclDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossAclDao.deleteByParam(map);
	}

	public int selectCount(String field, String param) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		return ossAclDao.selectCount(map);
	}

}

