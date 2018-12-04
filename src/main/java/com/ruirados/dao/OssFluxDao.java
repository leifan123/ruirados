package com.ruirados.dao;

import com.ruirados.pojo.OssFlux;

import java.util.List;

import java.util.Map;

public interface OssFluxDao {

	public void insert(OssFlux ossflux);

	public List<OssFlux> select(OssFlux ossflux);

	public void update(OssFlux ossflux);

	public void delete(OssFlux ossflux);

	public List<OssFlux> selectByParam(Map<String, String> param);

	public void updateByParam(Map<String, String> params);

	public void deleteByParam(Map<String, String> params);

}

