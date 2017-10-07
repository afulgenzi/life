package com.fulg.life.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fulg.life.service.SessionService;
import com.fulg.life.service.exception.WrongSessionTypeException;
import org.springframework.stereotype.Service;

@Service
public class DefaultSessionService implements SessionService {
	private Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();
	
	@Override
	public <T> T getSessionAttribute(String sessionId, String attributeName, Class expectedClass) {
		Object obj = attributes.get(getAttributeName(sessionId, attributeName));
		if (obj != null) {
			if (expectedClass.isInstance(obj)) {
				return (T) obj;
			} else {
				throw new WrongSessionTypeException(obj, expectedClass);
			}
		}
		return null;
	}

	@Override
	public synchronized void setSessionAttribute(String sessionId, String attributeName, Object attributeValue) {
		attributes.put(getAttributeName(sessionId, attributeName), attributeValue);
	}
	
	private String getAttributeName(String sessionId, String sessionAttribute){
		return sessionId + "_" + sessionAttribute;
	}
	
}
