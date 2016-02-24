package com.winit.framework.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 从数据库里读取配置
 * 
 * 实现参考:http://blog.csdn.net/maoxiang/article/details/4829553
 */
@SuppressWarnings("rawtypes")
public class DatabaseProperties implements InitializingBean, FactoryBean {
	static Logger log = Logger.getLogger(DatabaseProperties.class);

	private String propertySql;
	private DataSource dataSource;
	private Properties props = new Properties();

	public void afterPropertiesSet() throws Exception {
		initProperties();
	}

	public Object getObject() throws Exception {
		return props;
	}

	public Class<Properties> getObjectType() {
		return Properties.class;
	}

	public boolean isSingleton() {
		return true;
	}

	private void initProperties() {
		log.info("从property文件读取系统配置。。。。。.");
		DeployProperties.getInstance();
		log.info("从数据库读取系统配置。。。。。.");
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement(propertySql);
			ResultSet rs = ps.executeQuery();

			String localIp = PropertyHelper.getIp();
			String localPort = "";
			while (rs.next()) {
				String ip = rs.getString("IP");
				String port = rs.getString("PORT");
				String key = rs.getString("NAME");
				String value = rs.getString("VALUE");
				ip = ip == null ? "" : ip.trim();
				port = port == null ? "" : port.trim();
				key = key == null ? "" : key.trim();
				value = value == null ? "" : value.trim();

				if (!"".equals(key)) {
					if ((ip.equals("") && port.equals("")) || (ip.equals("") && port.equals(localPort))
							|| (ip.equals(localIp) && port.equals(""))
							|| (ip.equals(localIp) && port.equals(localPort))) {
						log.info("load property. Key=" + key + ",Value=" + value);
						props.put(key, value);
					}
				}
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			log.error("从数据库读取系统配置出错", e);
		} finally {
			closeConnection(connection);
		}
	}

	private void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				connection = null;
				log.error(e);
			}
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getPropertySql() {
		return propertySql;
	}

	public void setPropertySql(String propertySql) {
		this.propertySql = propertySql;
	}

}