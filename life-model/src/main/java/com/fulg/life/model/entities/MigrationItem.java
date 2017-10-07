package com.fulg.life.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "migrationitems")
public class MigrationItem extends Item {
    @Column(name = "MIGRATION_DATE")
    Date migrationDate;

    @Column(name = "SOURCE")
    String source;

    @Column(name = "DESTINATION")
    String destination;

    @Column(name = "MIGRATED_ITEMS")
    Integer migratedItems;

    public Date getMigrationDate() {
        return migrationDate;
    }

    public void setMigrationDate(Date migrationDate) {
        this.migrationDate = migrationDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getMigratedItems() {
        return migratedItems;
    }

    public void setMigratedItems(Integer migratedItems) {
        this.migratedItems = migratedItems;
    }

}
