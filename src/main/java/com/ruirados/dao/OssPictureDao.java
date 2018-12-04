package com.ruirados.dao;

import com.ruirados.pojo.OssPicture;

import java.util.List;

import java.util.Map;

public interface OssPictureDao {

	public void insert(OssPicture osspicture);

	public List<OssPicture> select(OssPicture osspicture);

	public void update(OssPicture osspicture);

	public void delete(OssPicture osspicture);

	public List<OssPicture> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

