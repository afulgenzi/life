package com.fulg.life.data;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 26/08/16.
 */
public class MatchingMovementsData {
    private List<BankAccountMovementData> matchingBankAccountMovements = Lists.newArrayList();
    private int matchingPriority = 0;

    public List<BankAccountMovementData> getMatchingBankAccountMovements()
    {
        return matchingBankAccountMovements;
    }

    public void setMatchingBankAccountMovements(
            List<BankAccountMovementData> matchingBankAccountMovements)
    {
        this.matchingBankAccountMovements = matchingBankAccountMovements;
    }

    public int getMatchingPriority()
    {
        return matchingPriority;
    }

    public void setMatchingPriority(int matchingPriority)
    {
        this.matchingPriority = matchingPriority;
    }
}
