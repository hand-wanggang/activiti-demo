/**
 * ModelerController 2017/10/27 9:56
 * <p>
 * Copyright (C) HAND Enterprise Solutions Company Ltd.
 * All Rights Reserved
 */
package com.lining.workflow.activitiworkflow.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author gang.wang
 * @Title: ModelerController
 * @Description: (描述此类的功能)
 * @date 2017/10/27 9:56
 */
@RestController
@RequestMapping("/models")
public class ModelerController {

	@Autowired
	RepositoryService repositoryService;
	@Autowired
	ObjectMapper objectMapper;

	/**
	 * 新建一个空模型
	 *
	 * @return @throws
	 */
	@PostMapping("/create")
	public void newModel(@RequestParam String name, @RequestParam String key, @RequestParam String description,
			HttpServletResponse response) throws IOException {
		Assert.notNull(key, "模型标识不能为空");
		Assert.notNull(name, "模型名称不能为空");
		Assert.notNull(description, "模型标识不能为空");
		// 初始化一个空模型
		Model model = repositoryService.newModel();
		int revision = 1;
		ObjectNode modelNode = objectMapper.createObjectNode();
		modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
		modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

		model.setName(name);
		model.setKey(key);
		model.setMetaInfo(modelNode.toString());

		repositoryService.saveModel(model);
		String id = model.getId();

		// 完善ModelEditorSource
		ObjectNode editorNode = objectMapper.createObjectNode();
		editorNode.put("id", "canvas");
		editorNode.put("resourceId", "canvas");
		ObjectNode stencilSetNode = objectMapper.createObjectNode();
		stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		editorNode.put("stencilset", stencilSetNode);
		repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
		response.sendRedirect("/modeler.html?modelId=" + id);
	}

	/**
	 * 流程模型列表
	 * @return
	 */
	@GetMapping("/list")
	public ResponseEntity listModels() {
		List<Model> list = repositoryService.createModelQuery().list();
		return ResponseEntity.ok(list);
	}

	/**
	 * 部署流程
	 * @param modelId
	 * @throws IOException
	 */
	@GetMapping("/deploy/{modelId}")
	public void deploy(@PathVariable("modelId") String modelId) throws IOException {
		Model modelData = repositoryService.getModel(modelId);
		ObjectNode modelNode = (ObjectNode) new ObjectMapper()
				.readTree(repositoryService.getModelEditorSource(modelData.getId()));
		byte[] bpmnBytes = null;
		BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
		bpmnBytes = new BpmnXMLConverter().convertToXML(model);
		String processName = modelData.getName() + ".bpmn20.xml";
		System.out.println(new String(bpmnBytes,"UTF-8"));
		repositoryService.createDeployment().name(modelData.getName())
				.addString(processName, new String(bpmnBytes, "UTF-8")).deploy();
	}
}
