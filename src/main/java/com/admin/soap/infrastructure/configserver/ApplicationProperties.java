package com.admin.soap.infrastructure.configserver;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@RefreshScope
@Getter
public class ApplicationProperties {

	@Value("${service.usuarios}")
	private String usuarios;
	
	@Value("${service.claves}")
	private String claves;
	
	@Value("#{'${service.whitelist}'.split(',')}")
	private List<String> whiteList;
	
	@Value("${service.endpoint.timelogger}")
	private String endPoint;
}
