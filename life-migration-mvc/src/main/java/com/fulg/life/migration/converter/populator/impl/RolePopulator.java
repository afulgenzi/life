package com.fulg.life.migration.converter.populator.impl;

import java.util.Map;

import com.fulg.life.model.entities.Item;
import com.fulg.life.model.entities.Role;

public class RolePopulator extends ItemPopulator {

    @Override
    public void populate(Item item, Map<String, Object> map) {
        super.populate(item, map);
        Role target = (Role) item;
        map.put("Role", target.getRole());
    }

}
