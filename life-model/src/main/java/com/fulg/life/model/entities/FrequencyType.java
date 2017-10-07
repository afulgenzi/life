package com.fulg.life.model.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by alessandro.fulgenzi on 13/07/16.
 */
@Entity
@Table(name = "frequencytypes")
public class FrequencyType extends Item {

    private String code;
    private String intervalUnit;
    private String intervalUnitInternal;
    private String title;
    private String description;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getIntervalUnit()
    {
        return intervalUnit;
    }

    public void setIntervalUnit(String intervalUnit)
    {
        this.intervalUnit = intervalUnit;
    }

    public String getIntervalUnitInternal()
    {
        return intervalUnitInternal;
    }

    public void setIntervalUnitInternal(String intervalUnitInternal)
    {
        this.intervalUnitInternal = intervalUnitInternal;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
