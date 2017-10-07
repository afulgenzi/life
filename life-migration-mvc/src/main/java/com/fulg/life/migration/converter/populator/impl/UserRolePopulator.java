package com.fulg.life.migration.converter.populator.impl;

import java.util.Map;

import com.fulg.life.model.entities.Item;
import com.fulg.life.model.entities.UserRole;

public class UserRolePopulator extends ItemPopulator {

    @Override
    public void populate(Item item, Map<String, Object> map) {
        super.populate(item, map);
        UserRole target = (UserRole) item;
        map.put("Role", getItemAsString(target.getRole()));
        map.put("User", getItemAsString(target.getUser()));
    }

}
