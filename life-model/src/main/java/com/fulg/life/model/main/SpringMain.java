package com.fulg.life.model.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {
    private static final Logger LOG = LoggerFactory.getLogger(SpringMain.class);

    public void getBeans(ApplicationContext context) {

    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/applicationContext-model.xml");

        for (String beanName : context.getBeanDefinitionNames()) {
            LOG.info("Bean: " + beanName);
        }

        Object dutyRepository = context.getBean("dutyRepository");
        LOG.info("dutyRepository: " + dutyRepository);
    }
}