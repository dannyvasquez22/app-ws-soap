package com.admin.soap.ws;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.admin.soap.infrastructure.configserver.ApplicationProperties;
import com.admin.soap.infrastructure.timelogger.TimeRecord;
import com.admin.soap.infrastructure.timelogger.TimesLogAgent;
import com.admin.webservice.WSEndPoint;
import com.admin.webservice.types.ChildResponse;
import com.admin.webservice.types.GRequest;
import com.admin.webservice.types.GResponse;

import lombok.AllArgsConstructor;

@WebService(targetNamespace = "http://admin.com/WebService", serviceName = "WebServiceAdmin")
@Component
@AllArgsConstructor
public class WSEndPointImpl implements WSEndPoint {
	
	private final ServiceSoap serviceSoap;
	private final TimesLogAgent timesLogAgent;
	private final ApplicationProperties applicationProperties;
	
	@Override
	public GResponse getResponse(GRequest request) {
		TimeRecord timeRecord = new TimeRecord();
		GResponse response = new GResponse();
		
		timeRecord.setDateTime(System.currentTimeMillis());
		timeRecord.setUser(request.getChildRequest().getUsuario());
		timeRecord.setTypeDocument(request.getChildRequest().getTipoDocumento());
		timeRecord.setNumberDocument(request.getChildRequest().getNumeroDocumento());
		timeRecord.setEndPointName(applicationProperties.getEndPoint());
		timeRecord.setStartTimeTotal(System.currentTimeMillis());
		
		ChildResponse childResponse = serviceSoap.getResult(request.getChildRequest(), timeRecord, timesLogAgent);
		response.setChildResponse(childResponse);
		return response;
	}

}
