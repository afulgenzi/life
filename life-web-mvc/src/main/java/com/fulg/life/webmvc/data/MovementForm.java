package com.fulg.life.webmvc.data;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.data.CategoryData;
import com.fulg.life.data.FrequencyData;

import java.util.Date;

/**
 * Created by alessandro.fulgenzi on 18/07/16.
 */
public class MovementForm {
    private BankAccountMovementData movement;
    private FrequencyData frequency;
    private Date fromDate;
    private Date untilDate;
    private boolean skipFirstDate;

    public BankAccountMovementData getMovement()
    {
        return movement;
    }

    public void setMovement(BankAccountMovementData movement)
    {
        this.movement = movement;
    }

    public FrequencyData getFrequency()
    {
        return frequency;
    }

    public void setFrequency(FrequencyData frequency)
    {
        this.frequency = frequency;
    }

    public Date getFromDate()
    {
        return fromDate;
    }

    public void setFromDate(Date fromDate)
    {
        this.fromDate = fromDate;
    }

    public Date getUntilDate()
    {
        return untilDate;
    }

    public void setUntilDate(Date untilDate)
    {
        this.untilDate = untilDate;
    }

    public boolean isSkipFirstDate()
    {
        return skipFirstDate;
    }

    public void setSkipFirstDate(boolean skipFirstDate)
    {
        this.skipFirstDate = skipFirstDate;
    }
}
