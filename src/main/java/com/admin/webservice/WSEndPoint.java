package com.admin.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.admin.webservice.types.GRequest;
import com.admin.webservice.types.GResponse;

@WebService(name = "EndPoint", targetNamespace = "http://admin.com/WebService")
@XmlSeeAlso({ com.admin.webservice.types.ObjectFactory.class })
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface WSEndPoint {

	@WebMethod(action = "urn:getRequest")
	@WebResult(name = "getResponse", targetNamespace = "http://service.ws.sample/", partName = "parameters")
	public GResponse getResponse(@WebParam(partName = "parameters", name = "getRequest", targetNamespace = "http://service.ws.sample/") GRequest parameters);
}
