package com.fulg.life.migration.converter.populator.impl;

import java.util.Map;

import com.fulg.life.model.entities.BankAccountMovement;
import com.fulg.life.model.entities.Item;

public class BankAccountMovementPopulator extends ItemPopulator {

	@Override
	public void populate(Item item, Map<String, Object> map) {
		super.populate(item, map);
		BankAccountMovement target = (BankAccountMovement) item;
		map.put("Date", target.getDate());
		map.put("Description", target.getDescription());
		map.put("Amount", target.getAmount());
		map.put("Currency", target.getCurrency().getCode());
		map.put("BankAccount", target.getBankAccount().getAccountNumber());
	}

}
