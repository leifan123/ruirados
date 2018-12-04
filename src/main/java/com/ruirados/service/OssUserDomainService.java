package com.ruirados.service;

import com.ruirados.pojo.OssUserDomain;

import java.util.List;

public interface OssUserDomainService {

	public void insert(OssUserDomain ossuserdomain);

	public List<OssUserDomain> select(OssUserDomain ossuserdomain);

	public void update(OssUserDomain ossuserdomain);

	public void delete(OssUserDomain ossuserdomain);

	public List<OssUserDomain> selectByParam(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);

}

