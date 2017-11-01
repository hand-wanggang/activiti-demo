/**
 * ApplicationConfig 2017/10/26 16:36
 * <p>
 * Copyright (C) HAND Enterprise Solutions Company Ltd.
 * All Rights Reserved
 */
package com.lining.workflow.activitiworkflow.config;

import org.activiti.engine.*;
import org.activiti.engine.impl.form.*;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lining.workflow.activitiworkflow.basic.rest.filter.JsonpCallbackFilter;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author gang.wang
 * @Title: ApplicationConfig
 * @Description: (描述此类的功能)
 * @date 2017/10/26 16:36
 */
@Configuration
@ImportResource(locations = {"classpath:activiti-login-context.xml","classpath:activiti-ui-context.xml"})
public class ApplicationConfig {

	@Autowired
	protected Environment environment;

	@Autowired
	private DataSourceProperties dataSourceProperties;

	@Bean
	public HikariConfig hikariConfig() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(dataSourceProperties.getJdbcUrl());
		hikariConfig.setDriverClassName(dataSourceProperties.getDriverClassName());
		hikariConfig.setUsername(dataSourceProperties.getUserName());
		hikariConfig.setPassword(dataSourceProperties.getPassword());
		hikariConfig.setMaximumPoolSize(dataSourceProperties.getMaximumPoolSize());
		hikariConfig.setMaxLifetime(dataSourceProperties.getMaxLifeTime());
		hikariConfig.setConnectionTimeout(dataSourceProperties.getConnectionTimeout());
		hikariConfig.setIdleTimeout(dataSourceProperties.getIdelTimeout());
		return hikariConfig;
	}

	@Bean(destroyMethod = "shutdown")
	public HikariDataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource(hikariConfig());
		return dataSource;
	}

	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}

	@Bean
	public FormTypes formTypes(){
		FormTypes formTypes = new FormTypes();
		formTypes.addFormType(new StringFormType());
		formTypes.addFormType(new LongFormType());
		formTypes.addFormType(new DateFormType("yyyy-MM-dd"));
		formTypes.addFormType(new BooleanFormType());
		formTypes.addFormType(new DoubleFormType());
		return formTypes;
	}

	@Bean
	public SpringProcessEngineConfiguration processEngineConfiguration() {
		SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
		processEngineConfiguration.setDataSource(dataSource());
		processEngineConfiguration.setTransactionManager(dataSourceTransactionManager());
		processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		processEngineConfiguration.setJobExecutorActivate(Boolean.FALSE);
		processEngineConfiguration.setHistory("full");
		processEngineConfiguration.setFormTypes(formTypes());
		return processEngineConfiguration;
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public ProcessEngine processEngine() throws Exception {
		ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
		processEngineFactoryBean.setProcessEngineConfiguration(processEngineConfiguration());
		return processEngineFactoryBean.getObject();
	}

	@Bean
	public RepositoryService repositoryService(ProcessEngine processEngine) {
		return processEngine.getRepositoryService();
	}

	@Bean
	public RuntimeService runtimeService(ProcessEngine processEngine) {
		return processEngine.getRuntimeService();
	}

	@Bean
	public TaskService taskService(ProcessEngine processEngine) {
		return processEngine.getTaskService();
	}

	@Bean
	public HistoryService historyService(ProcessEngine processEngine) {
		return processEngine.getHistoryService();
	}

	@Bean
	public ManagementService managementService(ProcessEngine processEngine) {
		return processEngine.getManagementService();
	}

	@Bean
	public IdentityService identityService(ProcessEngine processEngine) {
		return processEngine.getIdentityService();
	}

	@Bean
	public FormService formService(ProcessEngine processEngine) {
		return processEngine.getFormService();
	}

	@Bean
	public JsonpCallbackFilter jsonpCallbackFilter() {
		return new JsonpCallbackFilter();
	}
}
