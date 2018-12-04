package com.ruirados.service;

import com.ruirados.pojo.OssPicture;

import java.util.List;

public interface OssPictureService {

	public void insert(OssPicture osspicture);

	public List<OssPicture> select(OssPicture osspicture);

	public void update(OssPicture osspicture);

	public void delete(OssPicture osspicture);

	public List<OssPicture> selectByParam(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);

}

