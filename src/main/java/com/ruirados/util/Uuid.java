package com.ruirados.util;

import java.util.UUID;

public class Uuid {
	public static String getGenerateCode() {
		UUID uuid = UUID.randomUUID();
		String tmpid = uuid.toString();
		tmpid = tmpid.replace("-", "");
		return tmpid;
	}
}
