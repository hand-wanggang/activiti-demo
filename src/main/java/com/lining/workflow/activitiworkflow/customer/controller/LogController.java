/**
 * LogController 2017/10/30 9:54
 * <p>
 * Copyright (C) HAND Enterprise Solutions Company Ltd.
 * All Rights Reserved
 */
package com.lining.workflow.activitiworkflow.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gang.wang
 * @Title: LogController
 * @Description: (描述此类的功能)
 * @date 2017/10/30 9:54
 */
@Controller
@RequestMapping("/")
public class LogController {

	@PostMapping("/login")
	public void login(@RequestParam String userName, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.getSession().setAttribute("user", userName);
		response.sendRedirect("/home");
	}
}
