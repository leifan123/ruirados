/**
 * NewHeight.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.ruirados.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.twonote.rgwadmin4j.RgwAdmin;
import org.twonote.rgwadmin4j.RgwAdminBuilder;

import com.ruirados.pojo.OssZone;
import com.ruirados.util.GlobalAttr;



@Service
public class ConfigureInitation {

	private Logger log = Logger.getLogger(ConfigureInitation.class);
	@Autowired
	private OssZoneService ossZoneService;
	
	public void initSystemConfigure(){
		
		List<OssZone> ozList = ossZoneService.select(null);

		Map<String, OssZone> zoneMap = GlobalAttr.getInstance().getZoneMap();
		Map<String, RgwAdmin> rwgAdminMap = GlobalAttr.getInstance().getRgwAdminMap();
		zoneMap.clear();
		rwgAdminMap.clear();
		
		for (OssZone oz : ozList) {
			zoneMap.put(oz.getZoneid(), oz);
			String endpoint = "https://" + oz.getInnerserverip() + "/admin";
			String accessKey = oz.getApikey();
			String secretkey = oz.getSecretkey();
			RgwAdmin rgwAdmin = new RgwAdminBuilder().accessKey(accessKey).secretKey(secretkey).endpoint(endpoint)
					.build();
			rwgAdminMap.put(oz.getZoneid(), rgwAdmin);
		}
		
	}

	
}
