package com.admin.soap.infrastructure.interceptor;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Component;

import com.admin.soap.commons.exceptions.GenericException;
import com.admin.soap.commons.utils.MessagesError;

@Component
public class OutFaultInterceptor extends AbstractSoapInterceptor {

	public OutFaultInterceptor() {
		super(Phase.MARSHAL);
	}
	
	@Override
	public void handleMessage(SoapMessage message) {
		Fault fault = (Fault) message.getContent(Exception.class);
		String statusCode = MessagesError.S500.getNroError();
		Throwable ex = fault.getCause();
		if (ex instanceof GenericException) {
			GenericException exception = (GenericException) fault.getCause();
			statusCode = exception.getStatusCode();
		}
		fault.setFaultCode(new QName(XMLConstants.NULL_NS_URI, statusCode));
	}

}
