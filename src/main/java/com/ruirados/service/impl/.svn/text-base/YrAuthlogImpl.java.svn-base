package com.ruirados.service.impl;


import com.ruirados.dao.YrAuthlogDao;
import com.ruirados.service.YrAuthlogService;
import com.ruirados.util.NormName;
import com.yunrui.pojo.YrAuthlog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YrAuthlogImpl implements YrAuthlogService {

	private YrAuthlogDao yrAuthlogDao;

	public void setYrAuthlogDao(YrAuthlogDao yrAuthlogDao){
		 this.yrAuthlogDao = yrAuthlogDao;
	}

	public void insert(YrAuthlog yrauthlog){
		yrAuthlogDao.insert(yrauthlog);
	}

	public List<YrAuthlog> select(YrAuthlog yrauthlog){
		return yrAuthlogDao.select(yrauthlog);
	}

	public void update(YrAuthlog yrauthlog){
		yrAuthlogDao.update(yrauthlog);
	}

	public void delete(YrAuthlog yrauthlog){
		yrAuthlogDao.delete(yrauthlog);
	}

	public List<YrAuthlog> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return yrAuthlogDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  yrAuthlogDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  yrAuthlogDao.deleteByParam(map);
	}

}

