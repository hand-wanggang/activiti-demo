/**
 * CreateUserController 2017/10/27 15:42
 * <p>
 * Copyright (C) HAND Enterprise Solutions Company Ltd.
 * All Rights Reserved
 */
package com.lining.workflow.activitiworkflow.customer.controller;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gang.wang
 * @Title: CreateUserController
 * @Description: (描述此类的功能)
 * @date 2017/10/27 15:42
 */
@RestController
@RequestMapping("/")
public class CreateUserController {

	@Autowired
	private IdentityService identityService;

	@GetMapping("/addUser")
	public void initUserInfo(){
		User person1 = new UserEntity();
		person1.setId("person1");
		person1.setFirstName("员工");
		person1.setPassword("123");
		identityService.saveUser(person1);

		User person2 = new UserEntity();
		person2.setId("person2");
		person2.setFirstName("组长");
		person2.setPassword("123");
		identityService.saveUser(person2);

		User person3 = new UserEntity();
		person3.setId("person3");
		person3.setFirstName("经理");
		person3.setPassword("123");
		identityService.saveUser(person3);
	}
}
