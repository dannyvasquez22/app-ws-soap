package com.admin.soap.infrastructure.interceptor;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.admin.soap.commons.exceptions.GenericException;
import com.admin.soap.commons.utils.MessagesError;
import com.admin.soap.infrastructure.timelogger.TimeRecord;
import com.admin.soap.infrastructure.timelogger.TimesLogAgent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OutFaultInterceptor extends AbstractSoapInterceptor {

	@Autowired
	private TimesLogAgent timesLogAgent;
	
	@Autowired
	private TimeRecord timeRecord;
	
	public OutFaultInterceptor() {
		super(Phase.MARSHAL);
	}
	
	@Override
	public void handleMessage(SoapMessage message) {
		Fault fault = (Fault) message.getContent(Exception.class);
		String statusCode = MessagesError.S500.getNroError();
		Throwable ex = fault.getCause();
		log.error("Error -> {}", ex.getMessage());
		if (ex instanceof GenericException) {
			GenericException exception = (GenericException) fault.getCause();
			statusCode = exception.getStatusCode();
			
			timeRecord.setEndTimeTotal(System.currentTimeMillis());
			timeRecord.setState(statusCode.equals("500") ? "500" : "200");
			timeRecord.setErrorMessage(exception.getMessage());
		}
		
		timesLogAgent.logService(timeRecord);
		
		fault.setFaultCode(new QName(XMLConstants.NULL_NS_URI, statusCode));
	}

}
