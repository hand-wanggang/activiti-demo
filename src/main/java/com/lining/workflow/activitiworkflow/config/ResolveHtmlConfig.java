/**
 * ResolveHtmlConfig 2017/10/27 9:16
 * <p>
 * Copyright (C) HAND Enterprise Solutions Company Ltd.
 * All Rights Reserved
 */
package com.lining.workflow.activitiworkflow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author gang.wang
 * @Title: ResolveHtmlConfig
 * @Description: (描述此类的功能)
 * @date 2017/10/27 9:16
 */
@Configuration
public class ResolveHtmlConfig extends WebMvcConfigurerAdapter{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error").setViewName("error.html");
		registry.addViewController("/modeler").setViewName("modeler.html");
		registry.addViewController("/home").setViewName("home.html");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		super.configurePathMatch(configurer);
		configurer.setUseSuffixPatternMatch(false);
	}
}
