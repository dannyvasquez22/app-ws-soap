package com.admin.soap.infrastructure.timelogger;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TimeRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	private long dateTime;
	private String endPointName;
	private String user;
	private String typeDocument;
	private String numberDocument;
	private String state;
	private String errorMessage;
	private long startTimeClient;
	private long endTimeClient;
	private long elapsedTimeClient;
	private long startTimeTotal;
	private long endTimeTotal;
	private long elapsedTimeTotal;

}
