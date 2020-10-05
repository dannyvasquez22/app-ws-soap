package com.admin.webservice.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getRequest", propOrder = { "childRequest" })
@Getter
@Setter
public class GRequest {

	@XmlElement(name = "childRequest", required = true, nillable = true)
	protected ChildRequest childRequest;
}
