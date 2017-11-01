/**
 * DataSourceProperties 2017/8/28 15:16
 * <p>
 * Copyright (C) HAND Enterprise Solutions Company Ltd.
 * All Rights Reserved
 */
package com.lining.workflow.activitiworkflow.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author gang.wang
 * @Title: DataSourceProperties
 * @Description: (描述此类的功能)
 * @date 2017/8/28 15:16
 */
@Component
@ConfigurationProperties(prefix = "hikari.dataSource")
public class DataSourceProperties {

	private String jdbcUrl;

	private String userName;

	private String password;

	private String readOnly;

	private Integer connectionTimeout;

	private Integer idelTimeout;

	private Integer maxLifeTime;

	private Integer maximumPoolSize;

	private String driverClassName;

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public DataSourceProperties setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public DataSourceProperties setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public DataSourceProperties setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getReadOnly() {
		return readOnly;
	}

	public DataSourceProperties setReadOnly(String readOnly) {
		this.readOnly = readOnly;
		return this;
	}

	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}

	public DataSourceProperties setConnectionTimeout(Integer connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
		return this;
	}

	public Integer getIdelTimeout() {
		return idelTimeout;
	}

	public DataSourceProperties setIdelTimeout(Integer idelTimeout) {
		this.idelTimeout = idelTimeout;
		return this;
	}

	public Integer getMaxLifeTime() {
		return maxLifeTime;
	}

	public DataSourceProperties setMaxLifeTime(Integer maxLifeTime) {
		this.maxLifeTime = maxLifeTime;
		return this;
	}

	public Integer getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public DataSourceProperties setMaximumPoolSize(Integer maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
		return this;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public DataSourceProperties setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
		return this;
	}
}
