package com.admin.webservice.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "childResponse", propOrder = { "status", "body" })
@Getter
@Setter
public class ChildResponse {
	
	@XmlElement(name = "status")
	protected String status;
	
	@XmlElement(name = "body")
	protected String body;

}
