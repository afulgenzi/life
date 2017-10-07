package com.fulg.life.data;

import java.util.Date;

/**
 * Created by alessandro.fulgenzi on 19/07/16.
 */
public class FrequencyData {
    private int frequencyInterval;
    private FrequencyTypeData frequencyType;
    private Date frequencyEndDate;

    public int getFrequencyInterval()
    {
        return frequencyInterval;
    }

    public void setFrequencyInterval(int frequencyInterval)
    {
        this.frequencyInterval = frequencyInterval;
    }

    public FrequencyTypeData getFrequencyType()
    {
        return frequencyType;
    }

    public void setFrequencyType(FrequencyTypeData frequencyType)
    {
        this.frequencyType = frequencyType;
    }

    public Date getFrequencyEndDate()
    {
        return frequencyEndDate;
    }

    public void setFrequencyEndDate(Date frequencyEndDate)
    {
        this.frequencyEndDate = frequencyEndDate;
    }
}
