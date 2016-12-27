package com.vs.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Slf4j
public class ServletInitializer extends SpringBootServletInitializer {


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		log.info("==========CONFIGURE==============");
		System.out.println("==========CONFIGURE==============");
		return application.sources(ApplicationBootstrap.class);
	}
//
//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		log.info("Servlet Context: {} {}.", servletContext.getContextPath(), servletContext.getRealPath("/"));
//		System.out.println("OH MAN!!!");
//	}

}
