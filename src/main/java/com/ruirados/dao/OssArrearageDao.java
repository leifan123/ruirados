package com.ruirados.dao;

import com.ruirados.pojo.OssArrearage;

import java.util.List;

import java.util.Map;

public interface OssArrearageDao {

	public void insert(OssArrearage ossarrearage);

	public List<OssArrearage> select(OssArrearage ossarrearage);

	public void update(OssArrearage ossarrearage);

	public void delete(OssArrearage ossarrearage);

	public List<OssArrearage> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

