package com.ruirados.service.impl;

import com.ruirados.pojo.PandectCustomMonitorIndex;
import com.ruirados.dao.PandectCustomMonitorIndexDao;
import com.ruirados.service.PandectCustomMonitorIndexService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PandectCustomMonitorIndexImpl implements PandectCustomMonitorIndexService {

	private PandectCustomMonitorIndexDao pandectCustomMonitorIndexDao;

	public void setPandectCustomMonitorIndexDao(PandectCustomMonitorIndexDao pandectCustomMonitorIndexDao){
		 this.pandectCustomMonitorIndexDao = pandectCustomMonitorIndexDao;
	}

	public void insert(PandectCustomMonitorIndex pandectcustommonitorindex){
		pandectCustomMonitorIndexDao.insert(pandectcustommonitorindex);
	}

	public List<PandectCustomMonitorIndex> select(PandectCustomMonitorIndex pandectcustommonitorindex){
		return pandectCustomMonitorIndexDao.select(pandectcustommonitorindex);
	}

	public void update(PandectCustomMonitorIndex pandectcustommonitorindex){
		pandectCustomMonitorIndexDao.update(pandectcustommonitorindex);
	}

	public void delete(PandectCustomMonitorIndex pandectcustommonitorindex){
		pandectCustomMonitorIndexDao.delete(pandectcustommonitorindex);
	}

	public List<PandectCustomMonitorIndex> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return pandectCustomMonitorIndexDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  pandectCustomMonitorIndexDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  pandectCustomMonitorIndexDao.deleteByParam(map);
	}

}

