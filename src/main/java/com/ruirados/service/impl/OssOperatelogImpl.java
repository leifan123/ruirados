package com.ruirados.service.impl;

import com.ruirados.pojo.OssOperatelog;
import com.ruirados.dao.OssOperatelogDao;
import com.ruirados.service.OssOperatelogService;
import com.ruirados.util.NormName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OssOperatelogImpl implements OssOperatelogService {

	private OssOperatelogDao ossOperatelogDao;

	public void setOssOperatelogDao(OssOperatelogDao ossOperatelogDao){
		 this.ossOperatelogDao = ossOperatelogDao;
	}

	public void insert(OssOperatelog ossoperatelog){
		ossOperatelogDao.insert(ossoperatelog);
	}

	public List<OssOperatelog> select(OssOperatelog ossoperatelog){
		return ossOperatelogDao.select(ossoperatelog);
	}

	public void update(OssOperatelog ossoperatelog){
		ossOperatelogDao.update(ossoperatelog);
	}

	public void delete(OssOperatelog ossoperatelog){
		ossOperatelogDao.delete(ossoperatelog);
	}

	public List<OssOperatelog> selectByParam(String field,String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return ossOperatelogDao.selectByParam(map);
	}

	public void updateByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		  ossOperatelogDao.updateByParam(map);
	}

	public void deleteByParam(String param){
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", param);
		ossOperatelogDao.deleteByParam(map);
	}

	public int selectCount(String field, String param) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		return ossOperatelogDao.selectCount(map);
	}

	public List<String> selectOperateTarget(String field, String param) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("param", NormName.normSql(param));
		 return ossOperatelogDao.selectOperateTarget(map);
	}

}

