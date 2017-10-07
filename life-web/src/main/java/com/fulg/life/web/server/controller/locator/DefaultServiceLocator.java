package com.fulg.life.web.server.controller.locator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

public class DefaultServiceLocator implements ServiceLocator {
	private static Logger LOG = LoggerFactory.getLogger(DefaultServiceLocator.class);

	@Override
	public Object getInstance(Class<?> clazz) {
		try {
			LOG.info("looking for Service for class: " + clazz);
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
