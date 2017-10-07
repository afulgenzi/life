package com.fulg.life.migration.facade;

import java.util.List;

import com.fulg.life.migration.dto.MigrationPackage;


public interface MigrationFacade {
	MigrationPackage migrate(MigrationPackage migrationPackage, boolean cleanAllBeforeMigrating);
	
	List<String> getMigrationPackages();
}
