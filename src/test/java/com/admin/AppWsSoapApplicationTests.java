package com.admin;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

import com.admin.soap.AppWsSoapApplication;
import com.admin.soap.infrastructure.feignclient.SunatFeignClient;
import com.admin.webservice.WSEndPoint;
import com.admin.webservice.types.ChildRequest;
import com.admin.webservice.types.GRequest;
import com.admin.webservice.types.GResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(classes = AppWsSoapApplication.class)
@TestPropertySource("classpath:application.properties")
@WebAppConfiguration
@Slf4j
class AppWsSoapApplicationTests {

	@Autowired
	private WSEndPoint wSEndPoint;
	
	@MockBean
	private SunatFeignClient sunatFeignClient;
	
	@Value("classpath:client-response.json")
	private Resource clientResponse;
	
	@Autowired
	private ObjectMapper objMapper;
	
	@Test
	@DisplayName("gResponse_200")
	void gResponse_200() throws Exception {
		log.info("Init gResponse_200");
		String stringFromStream = CharStreams.toString(new InputStreamReader(clientResponse.getInputStream(), StandardCharsets.UTF_8));
		Map<String, Object> mapFromString = objMapper.readValue(stringFromStream, new TypeReference<Map<String, Object>>(){});
		when(sunatFeignClient.getPerson(any(), any())).thenReturn(ResponseEntity.ok(mapFromString));
		
		
		GRequest request = new GRequest();
		ChildRequest childRequest = new ChildRequest();
		childRequest.setUsuario("WSUSER");
		childRequest.setClave("PASSWD");
		childRequest.setTipoDocumento("2");
		childRequest.setNumeroDocumento("10710305205");
		request.setChildRequest(childRequest);
		GResponse gResponse = wSEndPoint.getResponse(request);
		
		assertNotNull(gResponse);
		log.info("Finish gResponse_200");
	}

}
