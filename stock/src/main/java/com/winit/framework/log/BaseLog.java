package com.winit.framework.log;

import org.apache.log4j.Logger;

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
 * </pre>
 * 
 * @since 1.
 */
public class BaseLog extends Logger {
	Logger log = null;

	private BaseLog(Class<?> clazz) {
		super(clazz.getName());
		log = Logger.getLogger(clazz);
	}

	public static BaseLog getLogger(Class<?> clazz) {
		return new BaseLog(clazz);
	}

	public void debug(String str) {
		log.debug(getTreadId().append(str));
	}

	public void debug(Throwable e) {
		log.debug(getTreadId(), e);
	}

	public void debug(String str, Throwable e) {
		log.debug(getTreadId().append(str), e);
	}

	public void info(String str) {
		log.info(getTreadId().append(str));
	}

	public void info(Throwable e) {
		log.info(getTreadId(), e);
	}

	public void info(String str, Throwable e) {
		log.info(getTreadId().append(str), e);
	}

	public void warn(String str) {
		log.warn(getTreadId().append(str));
	}

	public void warn(Throwable e) {
		log.warn(getTreadId(), e);
	}

	public void warn(String str, Throwable e) {
		log.warn(getTreadId().append(str), e);
	}

	public void error(String str) {
		log.error(getTreadId().append(str));
	}

	public void error(Throwable e) {
		log.error(getTreadId(), e);
	}

	public void error(String str, Throwable e) {
		log.error(getTreadId().append(str), e);
	}

	public void fatal(String str) {
		log.fatal(getTreadId().append(str));
	}

	public void fatal(Throwable e) {
		log.fatal(getTreadId(), e);
	}

	public void fatal(String str, Throwable e) {
		log.fatal(getTreadId().append(str), e);
	}

	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

	private StringBuffer getTreadId() {
		StringBuffer threadId = new StringBuffer();

		threadId.append("ThreadId:").append(Thread.currentThread().getId()).append(",");

		return threadId;
	}
}
