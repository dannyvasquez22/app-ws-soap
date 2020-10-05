package com.admin.soap;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan({"com.admin.soap"})
public class AppWsSoapApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppWsSoapApplication.class);
	}
	
	@Bean
	public ServletRegistrationBean<CXFServlet> servletRegistrationBean(ApplicationContext context) {
		ServletRegistrationBean<CXFServlet> servletRegistrationBean = new ServletRegistrationBean<>(new CXFServlet(), "/*");
		servletRegistrationBean.setLoadOnStartup(1);
		return servletRegistrationBean;
	}
	

}
