package com.ruirados.dao;

import com.ruirados.pojo.OssPrice;

import java.util.List;

import java.util.Map;

public interface OssPriceDao {

	public void insert(OssPrice ossprice);

	public List<OssPrice> select(OssPrice ossprice);

	public void update(OssPrice ossprice);

	public void delete(OssPrice ossprice);

	public List<OssPrice> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

