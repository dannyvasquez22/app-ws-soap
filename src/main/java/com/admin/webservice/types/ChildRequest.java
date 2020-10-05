package com.admin.webservice.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Getter;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "childRequest", propOrder = { "usuario", "clave", "tipoDocumento", "numeroDocumento" })
@Getter
@Setter
public class ChildRequest {

	@XmlElement(name = "usuario")
	protected String usuario;
	
	@XmlElement(name = "clave")
	protected String clave;
	
	@XmlElement(name = "tipoDocumento")
	protected String tipoDocumento;
	
	@XmlElement(name = "numeroDocumento")
	protected String numeroDocumento;
}
