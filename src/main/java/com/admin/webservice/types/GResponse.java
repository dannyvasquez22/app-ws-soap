package com.admin.webservice.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getResponse", propOrder = { "childResponse" })
@Getter
@Setter
public class GResponse {

	@XmlElement(name = "childResponse", required = true, nillable = true)
	protected ChildResponse childResponse;
}
