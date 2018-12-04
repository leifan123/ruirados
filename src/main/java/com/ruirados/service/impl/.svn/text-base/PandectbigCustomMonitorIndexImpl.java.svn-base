package com.ruirados.service.impl;

import com.ruirados.pojo.PandectbigCustomMonitorIndex;
import com.ruirados.dao.PandectbigCustomMonitorIndexDao;
import com.ruirados.service.PandectbigCustomMonitorIndexService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PandectbigCustomMonitorIndexImpl implements PandectbigCustomMonitorIndexService {

	private PandectbigCustomMonitorIndexDao pandectbigCustomMonitorIndexDao;

	public void setPandectbigCustomMonitorIndexDao(PandectbigCustomMonitorIndexDao pandectbigCustomMonitorIndexDao){
		 this.pandectbigCustomMonitorIndexDao = pandectbigCustomMonitorIndexDao;
	}

	public void insert(PandectbigCustomMonitorIndex pandectbigcustommonitorindex){
		pandectbigCustomMonitorIndexDao.insert(pandectbigcustommonitorindex);
	}

	public List<PandectbigCustomMonitorIndex> select(PandectbigCustomMonitorIndex pandectbigcustommonitorindex){
		return pandectbigCustomMonitorIndexDao.select(pandectbigcustommonitorindex);
	}

	public void update(PandectbigCustomMonitorIndex pandectbigcustommonitorindex){
		pandectbigCustomMonitorIndexDao.update(pandectbigcustommonitorindex);
	}

	public void delete(PandectbigCustomMonitorIndex pandectbigcustommonitorindex){
		pandectbigCustomMonitorIndexDao.delete(pandectbigcustommonitorindex);
	}

	public List<PandectbigCustomMonitorIndex> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return pandectbigCustomMonitorIndexDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  pandectbigCustomMonitorIndexDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", NormName.normSql(param));
		  pandectbigCustomMonitorIndexDao.deleteByParam(map);
	}

}

