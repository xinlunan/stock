package com.winit.framework.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础日志类，记录日志时输出SessionId
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * xulunan    1.0           Sep 25, 2012     Created
 * 
 * 
 *          </pre>
 * 
 * @since 1.
 */
public class BaseLog {
	Logger log = null;

	private BaseLog(Class<?> clazz) {
		log = LoggerFactory.getLogger(clazz);
	}

	public static BaseLog getLogger(Class<?> clazz) {
		return new BaseLog(clazz);
	}

	public void debug(String str) {
		log.debug(getTreadId().append(str).toString());
	}

	public void debug(Throwable e) {
		log.debug(getTreadId().toString(), e);
	}

	public void debug(String str, Throwable e) {
		log.debug(getTreadId().append(str).toString(), e);
	}

	public void info(String str) {
		log.info(getTreadId().append(str).toString());
	}

	public void info(Throwable e) {
		log.info(getTreadId().toString(), e);
	}

	public void info(String str, Throwable e) {
		log.info(getTreadId().append(str).toString(), e);
	}

	public void warn(String str) {
		log.warn(getTreadId().append(str).toString());
	}

	public void warn(Throwable e) {
		log.warn(getTreadId().toString(), e);
	}

	public void warn(String str, Throwable e) {
		log.warn(getTreadId().append(str).toString(), e);
	}

	public void error(String str) {
		log.error(getTreadId().append(str).toString());
	}

	public void error(Throwable e) {
		log.error(getTreadId().toString(), e);
	}

	public void error(String str, Throwable e) {
		log.error(getTreadId().append(str).toString(), e);
	}

	private StringBuffer getTreadId() {
		StringBuffer threadId = new StringBuffer();

		threadId.append("ThreadId:").append(Thread.currentThread().getId()).append(",");

		return threadId;
	}
}
