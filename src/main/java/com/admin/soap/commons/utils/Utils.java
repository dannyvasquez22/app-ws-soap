package com.admin.soap.commons.utils;

import com.admin.webservice.types.ChildRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	private Utils() {}
	
	public static String convertToJson(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch(Exception e) {
			return "";
		}
	}
	
	public static boolean validateRequest(ChildRequest request) {
		return (request.getUsuario() != null && request.getClave() != null && request.getTipoDocumento() != null && request.getNumeroDocumento() != null);
	}
}
