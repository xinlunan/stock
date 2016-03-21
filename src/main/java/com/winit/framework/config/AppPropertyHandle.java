package com.winit.framework.config;

import java.util.Properties;

public class AppPropertyHandle {
	private static Properties properties;

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static String getString(String key) {
		return properties.getProperty(key);
	}

	public static String getString(String key, String defaultValue) {
		String value = properties.getProperty(key);
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}

	public static Integer getInt(String key) {
		String value = properties.getProperty(key);
		if (value == null) {
			return -10000;
		} else {
			return Integer.valueOf(value);
		}
	}

	public static Integer getInt(String key, int defaultValue) {
		String value = properties.getProperty(key);
		if (value == null) {
			return defaultValue;
		} else {
			return Integer.valueOf(value);
		}
	}

	public static Float getFloat(String key) {
		String value = properties.getProperty(key);
		if (value == null) {
			return -10000f;
		} else {
			return Float.valueOf(value);
		}
	}

	public static Float getFloat(String key, Float defaultValue) {
		String value = properties.getProperty(key);
		if (value == null) {
			return defaultValue;
		} else {
			return Float.valueOf(value);
		}
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