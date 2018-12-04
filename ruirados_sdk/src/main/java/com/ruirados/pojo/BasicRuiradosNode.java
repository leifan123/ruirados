/**
 * NewHeight.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.ruirados.pojo;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author yunrui006
 * @version $Id: BasicRuiradosCredentials.java, v 0.1 2018年6月4日 上午10:15:41 yunrui006 Exp $
 */
public class BasicRuiradosNode {
	private String nodeIP;
	private String nodePort;
	
	
	public BasicRuiradosNode(){}


	/**
	 * @param nodeIP 
	 * @param nodePort
	 */
	public BasicRuiradosNode(String nodeIP, String nodePort) {
		super();
		this.nodeIP = nodeIP;
		this.nodePort = nodePort;
	}


	
	public String getNodeIP() {
		return nodeIP;
	}


	
	public void setNodeIP(String nodeIP) {
		this.nodeIP = nodeIP;
	}


	
	public String getNodePort() {
		return nodePort;
	}


	public void setNodePort(String nodePort) {
		this.nodePort = nodePort;
	}
	
	
	
	
}
