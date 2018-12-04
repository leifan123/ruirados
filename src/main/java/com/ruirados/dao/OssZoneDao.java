package com.ruirados.dao;

import com.ruirados.pojo.OssZone;

import java.util.List;

import java.util.Map;

public interface OssZoneDao {

	public void insert(OssZone osszone);

	public List<OssZone> select(OssZone osszone);

	public void update(OssZone osszone);

	public void delete(OssZone osszone);

	public List<OssZone> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

