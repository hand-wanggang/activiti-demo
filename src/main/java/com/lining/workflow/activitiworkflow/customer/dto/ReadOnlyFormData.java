/**
 * ReadOnlyFormData 2017/10/31 10:10
 * <p>
 * Copyright (C) HAND Enterprise Solutions Company Ltd.
 * All Rights Reserved
 */
package com.lining.workflow.activitiworkflow.customer.dto;

/**
 * @author gang.wang
 * @Title: ReadOnlyFormData
 * @Description: (描述此类的功能)
 * @date 2017/10/31 10:10
 */
public class ReadOnlyFormData {

	private String name;
	private Object value;
	private String type;

	public ReadOnlyFormData(){

	}

	public ReadOnlyFormData(String name,String type,Object value){
		this.name = name;
		this.value =value;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
