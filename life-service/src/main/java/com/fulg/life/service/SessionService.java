package com.fulg.life.service;

public interface SessionService {
	static final String SESSION_ATTRIBUTE_USER = "life_session_attribute_user";
	
	<T> T getSessionAttribute(String sessionId, String attributeName, Class expectedClass);
	
	void setSessionAttribute(String sessionId, String attributeName, Object attributeValue);
}
