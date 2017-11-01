/**
 * WritableFormData 2017/10/31 10:11
 * <p>
 * Copyright (C) HAND Enterprise Solutions Company Ltd.
 * All Rights Reserved
 */
package com.lining.workflow.activitiworkflow.customer.dto;

import org.activiti.engine.form.FormProperty;

import java.util.List;

/**
 * @author gang.wang
 * @Title: WritableFormData
 * @Description: (描述此类的功能)
 * @date 2017/10/31 10:11
 */
public class FormData {

	private List<ReadOnlyFormData> read;
	private List<FormProperty> write;

	public List<ReadOnlyFormData> getRead() {
		return read;
	}

	public void setRead(List<ReadOnlyFormData> read) {
		this.read = read;
	}

	public List<FormProperty> getWrite() {
		return write;
	}

	public void setWrite(List<FormProperty> write) {
		this.write = write;
	}
}
