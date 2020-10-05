package com.admin.soap.ws;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Holder;
import javax.xml.ws.http.HTTPException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.admin.soap.commons.utils.MessagesError;
import com.admin.soap.commons.utils.Utils;
import com.admin.soap.infrastructure.configserver.ApplicationProperties;
import com.admin.soap.infrastructure.feignclient.SunatFeignClientGateway;
import com.admin.soap.infrastructure.timelogger.TimeRecord;
import com.admin.soap.infrastructure.timelogger.TimesLogAgent;
import com.admin.webservice.types.ChildRequest;
import com.admin.webservice.types.ChildResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class ServiceSoap {

	private final ApplicationProperties applicationProperties;
	private final SunatFeignClientGateway sunatFeignClientGateway;
	
	public ChildResponse getResult(ChildRequest request, TimeRecord timeRecord, TimesLogAgent timesLogAgent) {
		ChildResponse response = null;
		MDC.put("Correlation-Id", String.valueOf(System.currentTimeMillis()));
		
		logAudit(request.getUsuario());
		
		try {
			boolean validateRequest = Utils.validateRequest(request);
			
			if (validateRequest) {
				Holder<String> userLogin = new Holder<>(request.getUsuario());
				Holder<String> userPassword = new Holder<>(request.getClave());
				
				response = preValidateCredentials(userLogin, userPassword);
				
				if (response != null) {
					timeRecord.setState(MessagesError.S200.getNroError()); //Porque es un error de usuario, y cuando kibana lo lea no lo interprete como error de servidor
					timeRecord.setErrorMessage(response.getBody());
					timeRecord.setEndTimeTotal(System.currentTimeMillis());
					
					timesLogAgent.logService(timeRecord);
					
					return response;
				}
				
				response = new ChildResponse();
				timeRecord.setStartTimeClient(System.currentTimeMillis());
				Map<String, Object> responseApi = sunatFeignClientGateway.searchPerson(request.getTipoDocumento(), request.getNumeroDocumento());
				timeRecord.setEndTimeClient(System.currentTimeMillis());
				response.setStatus(MessagesError.S200.getNroError());
				response.setBody((String) responseApi.get("razon_social"));
				timeRecord.setState(MessagesError.S200.getNroError());
				timeRecord.setEndTimeTotal(System.currentTimeMillis());
				
				timesLogAgent.logService(timeRecord);
				
				return response;
			} else {
				response = new ChildResponse();
				response.setStatus(MessagesError.S001.getNroError());
				response.setBody(MessagesError.S001.getMsjError());
			}
			
			timesLogAgent.logService(timeRecord);
		} catch(HTTPException ex) {
			log.info("Ha ocurrido una exception HTTPException: {}", ex.getMessage());
		}
		return response;
	}
	
	private ChildResponse preValidateCredentials(Holder<String> userLogin, Holder<String> userPassword) {
		String usuario = userLogin.value;
		String clave = userPassword.value;
		return validateCredentials(usuario, clave);
	}
	
	private ChildResponse validateCredentials(String usuario, String clave) {
		ChildResponse childResponse = null;
		
		try {
			if (StringUtils.isBlank(usuario) || StringUtils.isBlank(clave)) {
				childResponse = new ChildResponse();
				childResponse.setStatus(MessagesError.S003.getNroError());
				childResponse.setBody(MessagesError.S003.getMsjError());
				return childResponse;
			}
			
			String[] listUsers = applicationProperties.getUsuarios().split(",");
			int indexUser = ArrayUtils.indexOf(listUsers, usuario);
			String[] listPass = applicationProperties.getClaves().split(",");
			
			List<String> listPassw = Arrays.asList(listPass);
			
			String passw = listPassw.get(indexUser);
			
			if (!passw.equals(clave)) {
				childResponse = new ChildResponse();
				childResponse.setStatus(MessagesError.S004.getNroError());
				childResponse.setBody(MessagesError.S004.getMsjError());
			}
			
			return childResponse;
		} catch (Exception ex) {
			log.info("Ha ocurrido una excepcion al validar las credenciales :: {}", ex.getMessage());
			childResponse = new ChildResponse();
			childResponse.setStatus(MessagesError.S002.getNroError());
			childResponse.setBody(MessagesError.S002.getMsjError());
			return childResponse;
		}
	}
	
	private void logAudit(String user) {
		RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = null;
		if (RequestContextHolder.getRequestAttributes() != null) request = ((ServletRequestAttributes) attribs).getRequest();
		
		String protocol = request == null ? "" : request.getProtocol();
		String port = request == null ? "" : String.valueOf(request.getLocalPort());
		String localIp = request == null ? "" : request.getLocalAddr();
		String sourceIp = request == null ? "" : getSourceIp(request);
		log.info("===========");
		log.info("AUDIT");
		log.info("===========");
		log.info("[USER] = {}", user);
		log.info("[IP_CLIENT] = {}", sourceIp);
		log.info("[SOCKET_DESTINATION] = {}", protocol + " (" + localIp + ":" + port + ")");
	}
	
	private String getSourceIp(HttpServletRequest request) {
		String ipAddress = null;
		ipAddress = request.getHeader("X-FORWARDED-FOR"); //proxy
		if (ipAddress == null) ipAddress = request.getRemoteAddr();
		return ipAddress;
	}
}
