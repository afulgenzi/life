package com.fulg.life.model.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "masteritems")
public class MasterItem extends GenericItem {
    @OneToMany
    List<GenericItem> children;

    @Column(name = "FROMDATE")
    Date fromDate;

    @Column(name = "TODATE")
    Date toDate;

    public List<GenericItem> getChildren() {
        return children;
    }

    public void setChildren(List<GenericItem> children) {
        this.children = children;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

}
