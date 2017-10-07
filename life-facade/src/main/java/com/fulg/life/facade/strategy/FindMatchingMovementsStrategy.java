package com.fulg.life.facade.strategy;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.data.MatchingMovementsData;

import java.util.List;

public interface FindMatchingMovementsStrategy {

	MatchingMovementsData findMatchingMovements(BankAccountMovementData importMovement, List<BankAccountMovementData> movsByMonth);
}
