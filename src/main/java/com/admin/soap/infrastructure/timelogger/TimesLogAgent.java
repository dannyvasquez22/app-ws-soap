package com.admin.soap.infrastructure.timelogger;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.admin.soap.commons.utils.Constants;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class TimesLogAgent implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger TIMESLOGGER = LoggerFactory.getLogger("TIMELOGGER");
	
	public void logService(TimeRecord timeRecord) {
		try {
			MDC.put(Constants.TIME_LOGGER_DATETIME, new SimpleDateFormat(Constants.TIME_LOGGER_FORMAT_DATETIME).format(new Date(timeRecord.getDateTime())));
			MDC.put(Constants.TIME_LOGGER_ENDPOINTNAME, timeRecord.getEndPointName());
			MDC.put(Constants.TIME_LOGGER_USER, timeRecord.getUser());
			MDC.put(Constants.TIME_LOGGER_TYPEDOCUMENT, timeRecord.getTypeDocument());
			MDC.put(Constants.TIME_LOGGER_NUMBERDOCUMENT, timeRecord.getNumberDocument());
			MDC.put(Constants.TIME_LOGGER_STATE_TX, timeRecord.getState());
			MDC.put(Constants.TIME_LOGGER_ERRORMESSAGE, timeRecord.getErrorMessage());
			MDC.put(Constants.TIME_LOGGER_STARTIMECLIENT, new SimpleDateFormat(Constants.TIME_LOGGER_FORMAT_HOUR).format(new Date(timeRecord.getStartTimeClient())));
			MDC.put(Constants.TIME_LOGGER_ENDTIMECLIENT, new SimpleDateFormat(Constants.TIME_LOGGER_FORMAT_HOUR).format(new Date(timeRecord.getEndTimeClient())));
			MDC.put(Constants.TIME_LOGGER_ELAPSEDTIMECLIENT, getTimeTotal(timeRecord.getEndTimeClient(), timeRecord.getStartTimeClient()));
			MDC.put(Constants.TIME_LOGGER_STARTIMETOTAL, new SimpleDateFormat(Constants.TIME_LOGGER_FORMAT_HOUR).format(new Date(timeRecord.getStartTimeTotal())));
			MDC.put(Constants.TIME_LOGGER_ENDTIMETOTAL, new SimpleDateFormat(Constants.TIME_LOGGER_FORMAT_HOUR).format(new Date(timeRecord.getEndTimeTotal())));
			MDC.put(Constants.TIME_LOGGER_ELAPSEDTIMETOTAL, getTimeTotal(timeRecord.getEndTimeTotal(), timeRecord.getStartTimeTotal()));
			TIMESLOGGER.info(timeRecord.getEndPointName());
		} catch (Exception e) {
			log.error("Error al guardar en Timelogger. {}", e);
		} finally {
			MDC.remove(Constants.TIME_LOGGER_DATETIME);
			MDC.remove(Constants.TIME_LOGGER_ENDPOINTNAME);
			MDC.remove(Constants.TIME_LOGGER_USER);
			MDC.remove(Constants.TIME_LOGGER_TYPEDOCUMENT);
			MDC.remove(Constants.TIME_LOGGER_NUMBERDOCUMENT);
			MDC.remove(Constants.TIME_LOGGER_STATE_TX);
			MDC.remove(Constants.TIME_LOGGER_ERRORMESSAGE);
			MDC.remove(Constants.TIME_LOGGER_STARTIMECLIENT);
			MDC.remove(Constants.TIME_LOGGER_ENDTIMECLIENT);
			MDC.remove(Constants.TIME_LOGGER_ELAPSEDTIMECLIENT);
			MDC.remove(Constants.TIME_LOGGER_STARTIMETOTAL);
			MDC.remove(Constants.TIME_LOGGER_ENDTIMETOTAL);
			MDC.remove(Constants.TIME_LOGGER_ELAPSEDTIMETOTAL);
		}
	}

	private String getTimeTotal(long endTime, long startTime) {
		String timeTotal = "0";
		if ( endTime != 0 && startTime != 0) timeTotal = String.valueOf(endTime - startTime);
		return timeTotal;
	}
}
