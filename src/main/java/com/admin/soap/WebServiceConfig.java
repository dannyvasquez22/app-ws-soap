package com.admin.soap;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.jaxws.support.JaxWsServiceConfiguration;
import org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean;
import org.apache.cxf.wsdl.service.factory.AbstractServiceConfiguration;
import org.apache.cxf.wsdl.service.factory.DefaultServiceConfiguration;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import com.admin.soap.infrastructure.interceptor.IpAddressInInterceptor;
import com.admin.soap.infrastructure.interceptor.OutFaultInterceptor;
import com.admin.webservice.WSEndPoint;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebServiceConfig {

	private final IpAddressInInterceptor ipAddressInInterceptor;
	private final WSEndPoint wsEndpoint;
	private final OutFaultInterceptor outFaultInterceptor;
	private final Environment env;
	
	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		return new SpringBus();
	}
	
	@Bean
	@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public JaxWsServiceFactoryBean jaxWsServiceFactory() {
		List<AbstractServiceConfiguration> listServiceConfiguration = new ArrayList<>();
		listServiceConfiguration.add(new JaxWsServiceConfiguration());
		listServiceConfiguration.add(new DefaultServiceConfiguration());
		
		JaxWsServiceFactoryBean jaxWsServiceFactoryBean = new JaxWsServiceFactoryBean();
		jaxWsServiceFactoryBean.setServiceConfigurations(listServiceConfiguration);
		return jaxWsServiceFactoryBean;
	}
	
	@Bean
	public Endpoint endPointAdmin() {
		List<Interceptor<?>> listOutFaultInterceptor = new ArrayList<>();
		listOutFaultInterceptor.add(outFaultInterceptor);
		EndpointImpl endpoint = new EndpointImpl(springBus(), wsEndpoint);
		endpoint.getInInterceptors().add(ipAddressInInterceptor);
		endpoint.setServiceFactory(jaxWsServiceFactory());
		endpoint.setOutFaultInterceptors(listOutFaultInterceptor);
		endpoint.setPublishedEndpointUrl(env.getProperty("endpoint.admin.publishedEndpointUrl"));
		endpoint.publish(env.getProperty("endpoint.admin.publish"));
		return endpoint;
	}
	
}
