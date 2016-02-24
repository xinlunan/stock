package com.winit.framework.config;

import java.util.Properties;

public class AppPropertyHandle {
	private static Properties properties;

	public static String getProperty(String key) {
		return (String) properties.getProperty(key);
	}

	public static Properties getProperties() {
		return properties;
	}

	public static void addProperties(Properties properties) {
		AppPropertyHandle.properties.putAll(properties);
	}

	public static void setProperties(Properties properties) {
		AppPropertyHandle.properties = properties;
	}
}