package com.fulg.life.migration.converter.populator.impl;

import java.util.Map;

import com.fulg.life.model.entities.Currency;
import com.fulg.life.model.entities.Item;

public class CurrencyPopulator extends ItemPopulator {

    @Override
    public void populate(Item item, Map<String, Object> map) {
        super.populate(item, map);
        Currency target = (Currency) item;
        map.put("Abbreviation", target.getAbbreviation());
        map.put("AmountInEuro", target.getAmountInEuro());
        map.put("AmountInLire", target.getAmountInLire());
        map.put("Code", target.getCode());
        map.put("Description", target.getDescription());
    }

}
