package com.fulg.life.migration.converter.populator.impl;

import java.util.Map;

import com.fulg.life.model.entities.GenericItem;
import com.fulg.life.model.entities.Item;

public class GenericItemPopulator extends ItemPopulator {

    @Override
    public void populate(Item item, Map<String, Object> map) {
        super.populate(item, map);        
        GenericItem genericItem = (GenericItem) item;
        map.put("Pk", genericItem.getPk());
        map.put("Title", genericItem.getTitle());
        map.put("Description", genericItem.getDescription());
    }

}
