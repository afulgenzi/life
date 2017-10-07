package com.fulg.life.migration.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.fulg.life.migration.dto.MigrationPackage;
import com.fulg.life.migration.facade.MigrationFacade;
import com.fulg.life.migration.packages.PackageMapping;
import com.fulg.life.migration.service.AbstractMigrator;
import com.fulg.life.migration.service.impl.ValuteToCurrencyMigrator;

public class MigrationFacadeImpl implements MigrationFacade, ApplicationContextAware {

	ApplicationContext applicationContext = null;

	@Resource
	ValuteToCurrencyMigrator valuteMigrator;

	@Override
	public MigrationPackage migrate(MigrationPackage migrationPackage, boolean cleanAllBeforeMigrating) {

		PackageMapping mapping = getPackageMapping(migrationPackage.getPackageName());

		// clean (in reverse mapping order)
		int ind = mapping.getBeanMapping().size();
		AbstractMigrator migrator = mapping.getBeanMapping().get(String.valueOf(ind));
		while (migrator != null) {
			System.out.println("Cleaning up running "+migrator.getClass().getSimpleName());
			migrator.clearAll();
			migrator = mapping.getBeanMapping().get(String.valueOf(--ind));
		}
		
		// migrate
		ind = 1;
		migrator = mapping.getBeanMapping().get(String.valueOf(ind));
		while (migrator != null) {
			System.out.println("Migrating running "+migrator.getClass().getSimpleName());
			migrationPackage.getMigrationItems().add(migrator.migrate());
			migrator = mapping.getBeanMapping().get(String.valueOf(++ind));
		}

		return migrationPackage;
	}

	public PackageMapping getPackageMapping(String packageName) {
		Map<String, PackageMapping> mappings = applicationContext.getBeansOfType(PackageMapping.class);
		for (PackageMapping mapping : mappings.values()) {
			if (packageName.equals(mapping.getPackageName())) {
				return mapping;
			}
		}
		return null;
	}

	@Override
	public List<String> getMigrationPackages() {
		Map<String, PackageMapping> mappings = applicationContext.getBeansOfType(PackageMapping.class);
		List<String> packageNames = new ArrayList<String>();
		for (PackageMapping mapping : mappings.values()) {
			packageNames.add(mapping.getPackageName());
		}
		return packageNames;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
