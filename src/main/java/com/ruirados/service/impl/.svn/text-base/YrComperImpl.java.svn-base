package com.ruirados.service.impl;

import com.ruirados.pojo.YrComper;
import com.ruirados.dao.YrComperDao;
import com.ruirados.service.YrComperService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YrComperImpl implements YrComperService {

	private YrComperDao yrComperDao;

	public void setYrComperDao(YrComperDao yrComperDao){
		 this.yrComperDao = yrComperDao;
	}

	public void insert(YrComper yrcomper){
		yrComperDao.insert(yrcomper);
	}

	public List<YrComper> select(YrComper yrcomper){
		return yrComperDao.select(yrcomper);
	}

	public void update(YrComper yrcomper){
		yrComperDao.update(yrcomper);
	}

	public void delete(YrComper yrcomper){
		yrComperDao.delete(yrcomper);
	}

	public List<YrComper> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return yrComperDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  yrComperDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  yrComperDao.deleteByParam(map);
	}

}

