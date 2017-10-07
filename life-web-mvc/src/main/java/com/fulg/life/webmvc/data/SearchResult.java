package com.fulg.life.webmvc.data;

import com.fulg.life.data.BankAccountMovementData;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 28/06/16.
 */
public class SearchResult {
    List<AccountTotals> accountTotals = Lists.newArrayList();
    List<BankAccountMovementData> movements = Lists.newArrayList();

    public List<AccountTotals> getAccountTotals()
    {
        return accountTotals;
    }

    public void setAccountTotals(List<AccountTotals> accountTotals)
    {
        this.accountTotals = accountTotals;
    }

    public List<BankAccountMovementData> getMovements()
    {
        return movements;
    }

    public void setMovements(List<BankAccountMovementData> movements)
    {
        this.movements = movements;
    }
}
