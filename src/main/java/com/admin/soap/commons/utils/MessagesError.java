package com.admin.soap.commons.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessagesError {

	S001("001", "Error al validar los datos de entrada"),
	S002("002", "Las credenciales no estan configuradas, por favor revisar la configuración de usuario."),
	S003("003", "Favor de ingresar las credenciales."),
	S004("004", "La clave secreta no corresponde al usuario ingresado."),
	S200("200", ""),
	S500("500", "ERROR INTERNO"),
	S404("404", "Servicio no encontrado"),
	S600("600", "Valor nulo no permitido para el campo {1}");
	
	private String nroError;
	private String msjError;
}
