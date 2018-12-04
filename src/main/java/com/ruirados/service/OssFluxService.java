package com.ruirados.service;

import com.ruirados.pojo.OssFlux;

import java.util.List;

public interface OssFluxService {

	public void insert(OssFlux ossflux);

	public List<OssFlux> select(OssFlux ossflux);

	public void update(OssFlux ossflux);

	public void delete(OssFlux ossflux);

	public List<OssFlux> selectByParam(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);

}

