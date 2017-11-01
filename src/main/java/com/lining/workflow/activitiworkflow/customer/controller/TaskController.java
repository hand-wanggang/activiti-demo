/**
 * TaskController 2017/10/28 17:49
 * <p>
 * Copyright (C) HAND Enterprise Solutions Company Ltd.
 * All Rights Reserved
 */
package com.lining.workflow.activitiworkflow.customer.controller;

import com.lining.workflow.activitiworkflow.customer.dto.BasicTaskInfo;
import com.lining.workflow.activitiworkflow.customer.dto.FormData;
import com.lining.workflow.activitiworkflow.customer.dto.ReadOnlyFormData;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gang.wang
 * @Title: TaskController
 * @Description: (描述此类的功能)
 * @date 2017/10/28 17:49
 */
@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private FormService formService;

	@Autowired
	private HistoryService historyService;

	/**
	 * 获取当前用户任务
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("/list")
	public ResponseEntity listTask(HttpServletRequest request) {
		String userName = (String) request.getSession().getAttribute("user");
		List<Task> result = taskService.createTaskQuery().taskAssignee(userName).list();
		List<BasicTaskInfo> infos = new ArrayList<>();
		result.forEach(item -> {
			BasicTaskInfo basic = new BasicTaskInfo();
			BeanUtils.copyProperties(item, basic);
			basic.setProcessVariables(item.getProcessVariables());
			basic.setTaskLocalVariables(item.getTaskLocalVariables());
			infos.add(basic);
		});
		return ResponseEntity.ok(infos);
	}

	/**
	 * 任务认领
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("/claim")
	public void claimTask(@RequestParam String taskId, HttpServletRequest request) {
		String userId = (String) request.getSession().getAttribute("user");
		taskService.claim(taskId, userId);
	}

	/**
	 * 执行任务
	 * 
	 * @param taskId
	 * @param values
	 */
	@PostMapping("/exec/{taskId}")
	public void execTask(@PathVariable("taskId") String taskId, @RequestParam Map<String, String> values) {
		formService.submitTaskFormData(taskId, values);
	}

	/**
	 * 获取任务提交的表单
	 * 
	 * @param taskId
	 * @return
	 */
	@GetMapping("/form")
	public ResponseEntity taskForm(@RequestParam String taskId) {
		List<ReadOnlyFormData> data = dynamicFormPropertiesAndValue(taskId);
		List<FormProperty> properties = formService.getTaskFormData(taskId).getFormProperties();
		FormData formData = new FormData();
		formData.setRead(data);
		formData.setWrite(properties);
		return ResponseEntity.ok(formData);
	}

	/**
	 * 获取只读的表单信息
	 * 
	 * @param taskId
	 * @return
	 */
	private List<ReadOnlyFormData> dynamicFormPropertiesAndValue(String taskId) {
		List<ReadOnlyFormData> readOnlyFormData = new ArrayList<>();
		// 获取该流程中的变量
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		List<HistoricVariableInstance> result = historyService.createHistoricVariableInstanceQuery()
				.processInstanceId(processInstanceId).list();
		List<FormProperty> properties = formService.getStartFormData(task.getProcessDefinitionId()).getFormProperties();
		// 存入字段
		Map<String, Object> valuesMap = result.stream()
				.collect(Collectors.toMap(HistoricVariableInstance::getVariableName, p -> {
					if (null != p.getValue()) {
						return p.getValue();
					} else {
						return "";
					}
				}));
		Map<String, FormProperty> columMap = properties.stream().collect(Collectors.toMap(FormProperty::getId, f -> f));
		columMap.forEach((k, v) -> {
			if (v.getType().getName().equals("date")) {
				Date date = (Date) valuesMap.get(k);
				readOnlyFormData.add(new ReadOnlyFormData(v.getName(), v.getType().getName(),
						DateFormatUtils.format(date, "yyyy-MM-dd")));
			} else {
				readOnlyFormData.add(new ReadOnlyFormData(v.getName(), v.getType().getName(), valuesMap.get(k)));
			}
		});
		return readOnlyFormData;
	}
}
