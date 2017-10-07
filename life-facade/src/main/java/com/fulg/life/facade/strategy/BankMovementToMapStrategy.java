package com.fulg.life.facade.strategy;

import com.fulg.life.data.BankAccountMovementData;

import java.util.List;
import java.util.Map;

/**
 * Created by alessandro.fulgenzi on 19/05/16.
 */
public interface BankMovementToMapStrategy {
    Map<String, List<BankAccountMovementData>> asMap(List<BankAccountMovementData> list);
    BankAccountMovementData popCorrespondingMovement(BankAccountMovementData mov, Map<String, List<BankAccountMovementData>> map);
    List<BankAccountMovementData> asList(Map<String, List<BankAccountMovementData>> map);
    String getKeyForMovement(final BankAccountMovementData mov);
}
