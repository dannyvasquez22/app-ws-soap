package com.admin.soap.infrastructure.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.admin.soap.infrastructure.configserver.ApplicationProperties;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class IpAddressInInterceptor extends AbstractPhaseInterceptor<Message> {

	@Autowired
	private ApplicationProperties properties;
	
	public IpAddressInInterceptor() {
		super(Phase.PRE_INVOKE);
	}

	@Override
	public void handleMessage(Message message) {
		HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
		String ipAddress = "";
		boolean flag = false;
		
		if (null != request) {
			ipAddress = getUserIpAddress(request); // The client IP Address
			log.info("Request for Ip: {}", ipAddress);
			for (String s : properties.getWhiteList()) {
				if (ipAddress != null && ipAddress.contains(s)) {
					flag = true;
					break;
				}
			}
		}
		
		if (!flag) {
			log.info("Acceso restringido para la IP: {}", ipAddress);
			throw new Fault(new IllegalAccessException("Acceso restringido para la IP: " + ipAddress));
		}
	}
	
	private String getUserIpAddress(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		if (ip != null && ip.contains(",")) {
			String[] arr = ip.split(",");
			ip = arr[arr.length - 1].trim();
		}
		
		return ip;
	}

}
