package com.ruirados.dao;

import com.ruirados.pojo.OssOperatelog;

import java.util.List;

import java.util.Map;

public interface OssOperatelogDao {

	public void insert(OssOperatelog ossoperatelog);

	public List<OssOperatelog> select(OssOperatelog ossoperatelog);

	public void update(OssOperatelog ossoperatelog);

	public void delete(OssOperatelog ossoperatelog);

	public List<OssOperatelog> selectByParam(Map<String, String> param);
	
	public int selectCount(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);
	
	public List<String> selectOperateTarget(Map<String, String> params);

}

