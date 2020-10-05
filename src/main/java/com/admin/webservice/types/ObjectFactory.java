package com.admin.webservice.types;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

	private static final String URL_WS_TYPES = "http://service.ws.sample/";
	private static final QName _request_QNAME = new QName(URL_WS_TYPES, "getRequest");
	private static final QName _response_QNAME = new QName(URL_WS_TYPES, "getResponse");
	
	public ObjectFactory() {
		//Do nothing
	}
	
	public GRequest getRequest() {
		return new GRequest();
	}
	
	public GResponse getResponse() {
		return new GResponse();
	}
	
	public ChildRequest getChildRequest() {
		return new ChildRequest();
	}
	
	public ChildResponse getChildResponse() {
		return new ChildResponse();
	}
	
	@XmlElementDecl(namespace = URL_WS_TYPES, name = "getRequest")
	public JAXBElement<GRequest> getRequest(GRequest value) {
		return new JAXBElement<>(_request_QNAME, GRequest.class, null, value);
	}
	
	@XmlElementDecl(namespace = URL_WS_TYPES, name = "getResponse")
	public JAXBElement<GResponse> getResponse(GResponse value) {
		return new JAXBElement<>(_response_QNAME, GResponse.class, null, value);
	}
}
