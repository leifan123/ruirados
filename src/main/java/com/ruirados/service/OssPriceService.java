package com.ruirados.service;

import com.ruirados.pojo.OssPrice;

import java.util.List;

public interface OssPriceService {

	public void insert(OssPrice ossprice);

	public List<OssPrice> select(OssPrice ossprice);

	public void update(OssPrice ossprice);

	public void delete(OssPrice ossprice);

	public List<OssPrice> selectByParam(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);

}

