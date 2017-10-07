package com.fulg.life.migration.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fulg.life.model.entities.MigrationItem;

public class MigrationPackage {
    private String packageName;
    private Date lastMigration;
    private List<MigrationItem> migrationItems = new ArrayList<MigrationItem>();

    public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Date getLastMigration() {
        return lastMigration;
    }

    public void setLastMigration(Date lastMigration) {
        this.lastMigration = lastMigration;
    }

    public List<MigrationItem> getMigrationItems() {
        return migrationItems;
    }

}
