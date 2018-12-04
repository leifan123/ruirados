package com.ruirados.dao;

import com.ruirados.pojo.OssActivity;

import java.util.List;

import java.util.Map;

public interface OssActivityDao {

	public void insert(OssActivity ossactivity);

	public List<OssActivity> select(OssActivity ossactivity);

	public void update(OssActivity ossactivity);

	public void delete(OssActivity ossactivity);

	public List<OssActivity> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

