package com.fulg.life.data;

/**
 * Created by alessandro.fulgenzi on 14/07/16.
 */
public class FrequencyTypeData extends ItemData implements Comparable<FrequencyTypeData> {
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

    public int compareTo(FrequencyTypeData o)
    {
        FrequencyTypeData item = (FrequencyTypeData) o;
        return item.getPk().compareTo(o.getPk());
    }
}
