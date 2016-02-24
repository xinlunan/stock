package com.winit.framework.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.winit.framework.log.BaseLog;

public class PropertyHelper {
	protected static final BaseLog log = BaseLog.getLogger(PropertyHelper.class);

	public static String getIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.info("get host IP address fail", e);
		}
		return null;
	}

}