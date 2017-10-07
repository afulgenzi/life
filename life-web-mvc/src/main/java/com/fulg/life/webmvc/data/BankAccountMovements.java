package com.fulg.life.webmvc.data;

import com.fulg.life.data.BankAccountMovementData;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 23/06/16.
 */
public class BankAccountMovements {
    private List<BankAccountMovementData> movements;

    public List<BankAccountMovementData> getMovements()
    {
        return movements;
    }

    public void setMovements(List<BankAccountMovementData> movements)
    {
        this.movements = movements;
    }
}
