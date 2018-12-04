package com.ruirados.service;

import com.ruirados.pojo.OssSyslog;

import java.util.List;

public interface OssSyslogService {

	public void insert(OssSyslog osssyslog);

	public List<OssSyslog> select(OssSyslog osssyslog);

	public void update(OssSyslog osssyslog);

	public void delete(OssSyslog osssyslog);

	public List<OssSyslog> selectByParam(String field,String param);

	public void updateByParam(String param);

	public void deleteByParam(String param);

}

