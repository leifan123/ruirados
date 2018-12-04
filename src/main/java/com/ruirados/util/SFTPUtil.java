/**
 * @author YuLong.Dai
 * @time 2017年12月27日 下午2:37:40
 * TODO
 */
package com.ruirados.util;

/**
 * @author YuLong.Dai
 * @time 2017年12月27日 下午2:37:40
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * SFTP帮助类
 * @author wangbailin
 *
 */
public class SFTPUtil {
	
	private static Log log = LogFactory.getLog(SFTPUtil.class);
	
	/**
	 * 连接sftp服务器
	 * @param host 远程主机ip地址
	 * @param port sftp连接端口，null 时为默认端口
	 * @param user 用户名
	 * @param password 密码
	 * @return
	 * @throws JSchException 
	 */
	public static Session connect(String host, Integer port, String user, String password) throws JSchException{
		Session session = null;
		try {
			JSch jsch = new JSch();
			if(port != null){
				session = jsch.getSession(user, host, port.intValue());
			}else{
				session = jsch.getSession(user, host);
			}
			session.setPassword(password);
			//设置第一次登陆的时候提示，可选值:(ask | yes | no)
			session.setConfig("StrictHostKeyChecking", "no");
			//30秒连接超时
			session.connect(30000);
		} catch (JSchException e) {
			e.printStackTrace();
			log.error("SFTPUitl 获取连接发生错误");
			throw e;
		}
		return session;
	}
	
	/**
	 * sftp上传文件(夹)
	 * @param directory
	 * @param uploadFile
	 * @param sftp
	 * @throws Exception 
	 */
	public static void upload(String directory, String uploadFile, ChannelSftp sftp) throws Exception{
		log.debug("sftp upload file [directory] : "+directory);
		log.debug("sftp upload file [uploadFile] : "+ uploadFile);
		File file = new File(uploadFile);
		if(file.exists()){
			//这里有点投机取巧，因为ChannelSftp无法去判读远程linux主机的文件路径,无奈之举
			try {
				Vector content = sftp.ls(directory);
				if(content == null){
					sftp.mkdir(directory);
				}
			} catch (SftpException e) {
				sftp.mkdir(directory);
			}
			//进入目标路径
			sftp.cd(directory);
			if(file.isFile()){
				InputStream ins = new FileInputStream(file);
				//中文名称的
				sftp.put(ins, new String(file.getName().getBytes(),"UTF-8"));
				//sftp.setFilenameEncoding("UTF-8");
			}else{
				File[] files = file.listFiles();
				for (File file2 : files) {
					String dir = file2.getAbsolutePath();
					if(file2.isDirectory()){
						String str = dir.substring(dir.lastIndexOf(file2.separator));
						directory = normalize(directory + str);
					}
					upload(directory,dir,sftp);
				}
			}
		}
	}
	
	/**
	 * sftp上传文件(夹)根据流
	 * @param directory
	 * @param ins   输入流
	 * @param sftp
	 * @throws Exception 
	 */
	public static void uploadByStream(String directory, InputStream ins, String filename, ChannelSftp sftp) throws Exception{
		log.debug("sftp upload file [directory] : "+directory);
		log.debug("sftp upload file [uploadFile] : "+ filename);
		
		//这里有点投机取巧，因为ChannelSftp无法去判读远程linux主机的文件路径,无奈之举
		try {
			Vector content = sftp.ls(directory);
			if(content == null){
				sftp.mkdir(directory);
			}
		} catch (SftpException e) {
			sftp.mkdir(directory);
		}
		//进入目标路径
		sftp.cd(directory);
		
//		ins = new FileInputStream(file);
		//中文名称的
		sftp.put(ins, new String(filename.getBytes(),"UTF-8"));
			//sftp.setFilenameEncoding("UTF-8");
		
		
	}
	/**
	 * sftp下载文件（夹）
	 * @param directory 下载文件上级目录
	 * @param srcFile 下载文件完全路径
	 * @param saveFile 保存文件路径
	 * @param sftp ChannelSftp
	 * @throws UnsupportedEncodingException
	 */
	public static void download(String directory,String srcFile, String saveFile, ChannelSftp sftp) throws UnsupportedEncodingException {
		Vector conts = null;
		try{
			conts = sftp.ls(srcFile);
		} catch (SftpException e) {
			e.printStackTrace();
			log.debug("ChannelSftp sftp罗列文件发生错误",e);
		}
		File file = new File(saveFile);
		if(!file.exists()) file.mkdir();
		//文件
		if(srcFile.indexOf(".") > -1){
			try {
				sftp.get(srcFile, saveFile);
			} catch (SftpException e) {
				e.printStackTrace();
				log.debug("ChannelSftp sftp下载文件发生错误",e);
			}
		}else{
		//文件夹(路径)
			for (Iterator iterator = conts.iterator(); iterator.hasNext();) {
				LsEntry obj =  (LsEntry) iterator.next();
				String filename = new String(obj.getFilename().getBytes(),"UTF-8");
				if(!(filename.indexOf(".") > -1)){
					directory = normalize(directory + System.getProperty("file.separator") + filename);
					srcFile = directory;
					saveFile = normalize(saveFile + System.getProperty("file.separator") + filename);
				}else{
					//扫描到文件名为".."这样的直接跳过
					String[] arrs = filename.split("\\.");
					if((arrs.length > 0) && (arrs[0].length() > 0)){
						srcFile = normalize(directory + System.getProperty("file.separator") + filename);
					}else{
						continue;
					}
				}
				download(directory, srcFile, saveFile, sftp);
			}
		}
	}
	
	
	public static String normalize(String str){
		return str;
	}
	
	

	public static void remoteTransportFile(String remoteUplodPath, String filePath) {
		ChannelSftp sftp = null;
		Session session = null;
		String remoteIp = Prop.getInstance().getPropertiesByPro("cloudStack.properties", "sftp.remoteIp");
		String remoteUser = Prop.getInstance().getPropertiesByPro("cloudStack.properties", "sftp.remoteUser");;
		String remotePassword = Prop.getInstance().getPropertiesByPro("cloudStack.properties", "sftp.remotePassword");;
		try {
			session = SFTPUtil.connect(remoteIp,22, remoteUser, remotePassword);
			Channel channel = session.openChannel("sftp");
			channel.connect();
			log.debug("session--->"+session.toString());
			sftp = (ChannelSftp) channel;
			SFTPUtil.upload(remoteUplodPath, filePath, sftp);
			log.debug("完成");
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("备份文件到远程主机发生错误");
		}finally{
			if(sftp != null)sftp.disconnect();
			if(session != null)session.disconnect();
		}
	}
	
	public static void remoteTransportFileByStream(String remoteUplodPath, InputStream ins,String filename) {
		ChannelSftp sftp = null;
		Session session = null;
		String remoteIp = Prop.getInstance().getPropertiesByPro("cloudStack.properties", "sftp.remoteIp");
		String remoteUser = Prop.getInstance().getPropertiesByPro("cloudStack.properties", "sftp.remoteUser");;
		String remotePassword = Prop.getInstance().getPropertiesByPro("cloudStack.properties", "sftp.remotePassword");;
		try {
			session = SFTPUtil.connect(remoteIp,22, remoteUser, remotePassword);
			Channel channel = session.openChannel("sftp");
			channel.connect();
			log.debug("session--->"+session.toString());
			sftp = (ChannelSftp) channel;
			SFTPUtil.uploadByStream(remotePassword, ins, filename, sftp);
			log.debug("完成");
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("备份文件到远程主机发生错误");
		}finally{
			if(sftp != null)sftp.disconnect();
			if(session != null)session.disconnect();
		}
	}
	public static void main(String[] args) {
		
		String remoteUplodPath = "/tmp/20121221";
		String filePath = "F:/Outer.java";
		
//		remoteTransportFile(remoteUplodPath, filePath);
		
		
		try {
			remoteTransportFileByStream(remoteUplodPath, new FileInputStream(new File(filePath)),"test.java");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		 
	}
}