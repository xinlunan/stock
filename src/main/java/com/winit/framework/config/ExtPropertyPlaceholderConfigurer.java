package com.winit.framework.config;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class ExtPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties properties)
			throws BeansException {
		Properties fileProperties = DeployProperties.getProperties();

		if (fileProperties != null) {
			AppPropertyHandle.setProperties(fileProperties);
			AppPropertyHandle.addProperties(properties);
		} else {
			AppPropertyHandle.setProperties(properties);
		}

		super.processProperties(beanFactoryToProcess, properties);
	}

}