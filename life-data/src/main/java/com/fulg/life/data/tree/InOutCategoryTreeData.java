package com.fulg.life.data.tree;

import com.fulg.life.data.CurrencyData;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 05/07/16.
 */
public class InOutCategoryTreeData extends CategoryTreeData {
    private Double totIn = Double.valueOf(0d);
    private Double totOut = Double.valueOf(0d);
    private CurrencyData currency;
    private List<String> tags = Lists.newArrayList();

    public Double getTotIn()
    {
        return totIn;
    }

    public void setTotIn(Double totIn)
    {
        this.totIn = totIn;
    }

    public Double getTotOut()
    {
        return totOut;
    }

    public void setTotOut(Double totOut)
    {
        this.totOut = totOut;
    }

    public List<String> getTags()
    {
        return tags;
    }

    public void setTags(List<String> tags)
    {
        this.tags = tags;
    }

    public CurrencyData getCurrency()
    {
        return currency;
    }

    public void setCurrency(CurrencyData currency)
    {
        this.currency = currency;
    }
}
