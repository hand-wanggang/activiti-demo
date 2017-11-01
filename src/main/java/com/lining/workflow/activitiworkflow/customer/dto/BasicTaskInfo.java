/**
 * BasicTaskInfo 2017/10/30 11:41
 * <p>
 * Copyright (C) HAND Enterprise Solutions Company Ltd.
 * All Rights Reserved
 */
package com.lining.workflow.activitiworkflow.customer.dto;

import java.util.Date;
import java.util.Map;

/**
 * @author gang.wang
 * @Title: BasicTaskInfo
 * @Description: (描述此类的功能)
 * @date 2017/10/30 11:41
 */
public class BasicTaskInfo {

	private String id;

	private String name;

	private String description;

	private Date createTime;

	private Map<String, Object> processVariables;

	private Map<String,Object> taskLocalVariables;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Map<String, Object> getProcessVariables() {
		return processVariables;
	}

	public void setProcessVariables(Map<String, Object> processVariables) {
		this.processVariables = processVariables;
	}

	public Map<String, Object> getTaskLocalVariables() {
		return taskLocalVariables;
	}

	public void setTaskLocalVariables(Map<String, Object> taskLocalVariables) {
		this.taskLocalVariables = taskLocalVariables;
	}
}
