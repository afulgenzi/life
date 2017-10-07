package com.fulg.life.migration.converter.populator.impl;

import java.util.Map;

import com.fulg.life.model.entities.Duty;
import com.fulg.life.model.entities.Item;

public class DutyPopulator extends GenericItemPopulator {

    @Override
    public void populate(Item item, Map<String, Object> map) {
        super.populate(item, map);
        Duty duty = (Duty) item;
        map.put("Date", duty.getDate());
    }

}
