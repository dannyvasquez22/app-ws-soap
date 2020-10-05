package com.admin.soap.infrastructure.feignclient;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.admin.soap.commons.utils.Utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class SunatFeignClientGateway {

	private final SunatFeignClient sunatFeignClient;
	
	public Map<String, Object> searchPerson(String tipoDocumento, String numeroDocumento) {
		log.debug("Request for SunatFeignClient :: tipoDocumento -> {}, numeroDocumento -> {}", tipoDocumento, numeroDocumento);
		Map<String, Object> response = sunatFeignClient.getPerson(tipoDocumento, numeroDocumento).getBody();
		log.debug("Response for SunatFeignClient :: {}", Utils.convertToJson(response));
		return response;
	}
}
