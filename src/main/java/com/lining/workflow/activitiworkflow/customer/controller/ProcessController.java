/**
 * ProcessController 2017/10/27 16:04
 * <p>
 * Copyright (C) HAND Enterprise Solutions Company Ltd.
 * All Rights Reserved
 */
package com.lining.workflow.activitiworkflow.customer.controller;

import com.lining.workflow.activitiworkflow.customer.dto.BasicProcessInfo;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gang.wang
 * @Title: ProcessController
 * @Description: (描述此类的功能)
 * @date 2017/10/27 16:04
 */
@RestController
@RequestMapping("/processes")
public class ProcessController {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private FormService formService;

	/**
	 * 查看流程定义列表
	 * 
	 * @return
	 */
	@GetMapping("/list")
	public ResponseEntity listProcessDefine() {
		List<BasicProcessInfo> processInfoList = new ArrayList<>();
		List<ProcessDefinition> result = repositoryService.createProcessDefinitionQuery().list();
		result.forEach(item -> {
			BasicProcessInfo info = new BasicProcessInfo();
			BeanUtils.copyProperties(item, info);
			processInfoList.add(info);
		});
		return ResponseEntity.ok(processInfoList);
	}

	/**
	 * 开始一个流程前获取表单(动态表单)
	 * 
	 * @param processDefinitionId
	 */
	@PostMapping("/pre-start/{processDefinitionId}")
	public ResponseEntity processDynamicForm(@PathVariable("processDefinitionId") String processDefinitionId) {
		StartFormData formData = formService.getStartFormData(processDefinitionId);
		List<FormProperty> formProperties = formData.getFormProperties();
		return ResponseEntity.ok(formProperties);
	}

	/**
	 * 提交表单并启动流程实例
	 * 
	 * @param processDefinitionId
	 * @param values
	 */
	@PostMapping("/start/{processDefinitionId}")
	public void start(HttpServletRequest request, @PathVariable("processDefinitionId") String processDefinitionId,
			@RequestParam Map<String, String> values) {
		String applyUser = (String) request.getSession().getAttribute("user");
		if(null == values){
			values = new HashMap<>();
		}
		values.put("applyUser",applyUser);
		ProcessInstance instance = formService.submitStartFormData(processDefinitionId, values);
		ResponseEntity.ok(instance.getId());
	}
}
