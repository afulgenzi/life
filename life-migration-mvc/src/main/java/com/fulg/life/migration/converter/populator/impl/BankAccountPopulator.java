package com.fulg.life.migration.converter.populator.impl;

import java.util.Map;

import com.fulg.life.model.entities.BankAccount;
import com.fulg.life.model.entities.Item;

public class BankAccountPopulator extends ItemPopulator {

	@Override
	public void populate(Item item, Map<String, Object> map) {
		super.populate(item, map);
		BankAccount target = (BankAccount) item;
		map.put("AccountNumber", target.getAccountNumber());
		map.put("isDefaultAccount", target.getDefaultAccount());
		map.put("SubscriptionDate", target.getSubscriptionDate());
		map.put("user", getItemAsString(target.getUser()));
		map.put("movements", target.getMovements() == null ? 0 : target.getMovements().size());
	}

}
