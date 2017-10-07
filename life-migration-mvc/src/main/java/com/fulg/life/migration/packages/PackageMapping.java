package com.fulg.life.migration.packages;

import java.util.Map;

import com.fulg.life.migration.service.AbstractMigrator;

public class PackageMapping {
	private String packageName;
	private Map<String, AbstractMigrator> beanMapping;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Map<String, AbstractMigrator> getBeanMapping() {
		return beanMapping;
	}

	public void setBeanMapping(Map<String, AbstractMigrator> beanMapping) {
		this.beanMapping = beanMapping;
	}
}
