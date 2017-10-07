package com.fulg.life.model.repository.locator;

import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fulg.life.model.entities.Item;

public class RepositoryLocator {
	private static final Logger LOG = LoggerFactory.getLogger(RepositoryLocator.class);

	@Resource(name = "lifeModelProperties")
	Properties props;

	@Autowired
	private ApplicationContext applicationContext;

	public JpaRepository<Item, Long> getInstance(Class clazz) {
		// // Package
		// String repositoryPackage =
		// props.getProperty("spring.data.repository.package");
		// LOG.info("spring.data.repository.package: " + repositoryPackage);

		// Name
		String repositoryName = clazz.getSimpleName() + "Repository";
		repositoryName = repositoryName.substring(0, 1).toLowerCase() + repositoryName.substring(1);
		LOG.info("Looking for repository: " + repositoryName);

		// Retrieve
		// String repositoryFullName = repositoryPackage + "." + repositoryName;
		return (JpaRepository) applicationContext.getBean(repositoryName);
	}

	public JpaRepository<Item, Long> getInstance(String className) {
		// // Package
		// String repositoryPackage =
		// props.getProperty("spring.data.repository.package");
		// LOG.info("spring.data.repository.package: " + repositoryPackage);

		// Name
		String repositoryName = className + "Repository";
		repositoryName = repositoryName.substring(0, 1).toLowerCase() + repositoryName.substring(1);
		LOG.info("Looking for repository: " + repositoryName);

		// Retrieve
		// String repositoryFullName = repositoryPackage + "." + repositoryName;
		return (JpaRepository) applicationContext.getBean(repositoryName);
	}

}
