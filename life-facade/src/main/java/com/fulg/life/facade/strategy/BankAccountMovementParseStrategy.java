package com.fulg.life.facade.strategy;

import com.fulg.life.data.BankAccountMovementData;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 12/02/16.
 */
public interface BankAccountMovementParseStrategy {
    List<BankAccountMovementData> parseText(String bankCode, String text);
}
