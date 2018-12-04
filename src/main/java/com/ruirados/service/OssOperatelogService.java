package com.ruirados.service;

import com.ruirados.pojo.OssOperatelog;

import java.util.List;
import java.util.Map;

public interface OssOperatelogService {

	public void insert(OssOperatelog ossoperatelog);

	public List<OssOperatelog> select(OssOperatelog ossoperatelog);

	public void update(OssOperatelog ossoperatelog);

	public void delete(OssOperatelog ossoperatelog);

	public List<OssOperatelog> selectByParam(String field,String param);
	
	public int selectCount(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);
	
	public List<String> selectOperateTarget(String field,String param);

}

