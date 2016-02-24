package com.winit.framework.config;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class DeployProperties {

	private Logger logger = Logger.getLogger(DeployProperties.class);
	
	private static List<String> propertyFileNames;// "config/deploy_config.properties";

	private static Properties properties = null;

	private static DeployProperties instance = new DeployProperties();;


	public static DeployProperties getInstance() {
		return instance;
	}

	private DeployProperties() {
		if (propertyFileNames == null || propertyFileNames.isEmpty()) {
			return;
		}
		properties = new Properties();
		for (String fileName : propertyFileNames) {
			try {

				Resource resource = new ClassPathResource(fileName);

				properties.load(resource.getInputStream());

			} catch (IOException e) {
				logger.error("can not read config file " + fileName);
			}
			logger.info(fileName + " loaded.");
		}
	}

	public final String getProperty(String key) {
		return getProperty(key, StringUtils.EMPTY);
	}

	public final String getProperty(String key, String defaultValue) {
		return properties.getProperty(key) == null ? defaultValue : properties.getProperty(key);
	}

	public static Properties getProperties() {
		return properties;
	}

	public static void setProperties(Properties properties) {
		DeployProperties.properties = properties;
	}

}
