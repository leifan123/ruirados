package com.ruirados.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;

public class Cache {
	
	private static Properties instance;
	
	public static String get(String key) throws IOException{
		if(instance==null){
			instance = new Properties();
			FileInputStream in = new FileInputStream("src/main/resource/cloudStack.properties");
			instance.load(in);
			in.close();
		}
		return instance.getProperty(key);
	}
	
	public static void main(String[] args) throws IOException{
		File file = new File("src/main/resource/cloudStack.properties");
		FileInputStream fis = new FileInputStream(file);
		
		File dist = new File("src/main/resource/cloudStack1.properties");
		FileOutputStream fos = new FileOutputStream(dist);
		 
		byte[] by = new byte[512];
		
		while(fis.available()>512){
			fis.read(by);
			//ByteBuffer src = ByteBuffer.wrap(by);
			fos.write(by);
		}
		int remain = fis.available();
		by = new byte[remain];
		fis.read(by);
		//ByteBuffer src = ByteBuffer.wrap(by);
		fos.write(by);
		fis.close();
		fos.close();
		
	}
}
