package com.ruirados.service.impl;

import com.ruirados.pojo.OssArrearage;
import com.ruirados.dao.OssArrearageDao;
import com.ruirados.service.OssArrearageService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OssArrearageImpl implements OssArrearageService {

	private OssArrearageDao ossArrearageDao;

	public void setOssArrearageDao(OssArrearageDao ossArrearageDao){
		 this.ossArrearageDao = ossArrearageDao;
	}

	public void insert(OssArrearage ossarrearage){
		ossArrearageDao.insert(ossarrearage);
	}

	public List<OssArrearage> select(OssArrearage ossarrearage){
		return ossArrearageDao.select(ossarrearage);
	}

	public void update(OssArrearage ossarrearage){
		ossArrearageDao.update(ossarrearage);
	}

	public void delete(OssArrearage ossarrearage){
		ossArrearageDao.delete(ossarrearage);
	}

	public List<OssArrearage> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return ossArrearageDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossArrearageDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  ossArrearageDao.deleteByParam(map);
	}

}

