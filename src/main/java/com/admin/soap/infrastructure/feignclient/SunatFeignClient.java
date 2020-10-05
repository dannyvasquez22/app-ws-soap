package com.admin.soap.infrastructure.feignclient;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "${component.SunatFeignClient.name}")
public interface SunatFeignClient {

	@PostMapping(value = "${component.SunatFeignClient.contextPath}/${component.SunatFeignClient.endPoint}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getPerson(@PathVariable("tipoDocumento") String tipoDocumento, @PathVariable("numeroDocumento") String numeroDocumento);
}
