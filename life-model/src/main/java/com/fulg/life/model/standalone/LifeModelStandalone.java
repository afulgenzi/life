package com.fulg.life.model.standalone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class LifeModelStandalone {
	private static final Logger LOG = LoggerFactory.getLogger(LifeModelStandalone.class);

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/applicationContext-model.xml");
		LifeModelStandalone standalone = context.getBean(LifeModelStandalone.class);
		standalone.execute(args);
	}

	public void execute(String[] args){
		LOG.info("LifeModelStandalone.EXECUTE");
	}
}
