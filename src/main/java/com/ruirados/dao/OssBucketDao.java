package com.ruirados.dao;

import com.ruirados.pojo.OssBucket;

import java.util.List;

import java.util.Map;

public interface OssBucketDao {

	public int insert(OssBucket ossbucket);

	public List<OssBucket> select(OssBucket ossbucket);

	public void update(OssBucket ossbucket);

	public int delete(OssBucket ossbucket);

	public List<OssBucket> selectByParam(Map<String, String> param);
	
	public int selectCount(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

