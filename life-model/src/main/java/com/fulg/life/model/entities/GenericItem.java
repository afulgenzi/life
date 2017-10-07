package com.fulg.life.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "genericitems")
public class GenericItem extends Item {
    @Column(name = "TITLE")
    String title;

    @Column(name = "DESCRIPTION")
    String description;

    @ManyToOne
    MasterItem parent;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MasterItem getParent() {
        return parent;
    }

    public void setParent(MasterItem parent) {
        this.parent = parent;
    }
}
