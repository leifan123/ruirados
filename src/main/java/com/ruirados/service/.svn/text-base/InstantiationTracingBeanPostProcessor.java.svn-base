/**
 * NewHeight.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.ruirados.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.ruirados.threads.DestroyBucketRunnable;
import com.ruirados.util.Prop;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author yunrui006
 * @version $Id: asda.java, v 0.1 2018年5月15日 上午11:30:48 yunrui006 Exp $
 */
@Service
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

	private Logger log = Logger.getLogger(InstantiationTracingBeanPostProcessor.class);

	@Autowired
	private OssZoneService ossZoneService;
	@Autowired
	private ConfigureInitation configureInitation;

	/**
	 * @param event
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (event.getApplicationContext().getParent() == null) {

			// 初始化系统配置
			configureInitation.initSystemConfigure();

			
			try {
				// 强制销毁bucket线程模块
				int destroyBucketThreadNum = Integer
						.valueOf(Prop.getInstance().getPropertiesByPro("ruirados.properties", "destroyBucket.thread.num"));

				for (int i = 0; i < destroyBucketThreadNum; i++) {
					new Thread(new DestroyBucketRunnable()).start();
				}
				log.info("强制销毁bucket线程模块成功!");
			} catch (Exception e) {
				log.error("强制销毁bucket线程模块失败!");
			}

		}

	}

}
