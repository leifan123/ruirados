package com.ruirados.service;

import com.ruirados.pojo.OssActivity;

import java.util.List;

public interface OssActivityService {

	public void insert(OssActivity ossactivity);

	public List<OssActivity> select(OssActivity ossactivity);

	public void update(OssActivity ossactivity);

	public void delete(OssActivity ossactivity);

	public List<OssActivity> selectByParam(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);

}

