package com.ruirados.service;

import com.ruirados.pojo.OssBucket;

import java.util.List;
import java.util.Map;

public interface OssBucketService {

	public int insert(OssBucket ossbucket);

	public List<OssBucket> select(OssBucket ossbucket);

	public void update(OssBucket ossbucket);

	public int delete(OssBucket ossbucket);

	public List<OssBucket> selectByParam(String field,String param);
	
	public int selectCount(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);

}

