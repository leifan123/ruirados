package com.ruirados.dao;

import com.ruirados.pojo.OssCors;

import java.util.List;

import java.util.Map;

public interface OssCorsDao {

	public void insert(OssCors osscors);

	public List<OssCors> select(OssCors osscors);

	public void update(OssCors osscors);

	public void delete(OssCors osscors);

	public List<OssCors> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

