package com.fulg.life.migration.converter.populator;

import java.util.Map;

import com.fulg.life.model.entities.Item;

public interface NewModelPopulator {
	public void populate(Item item, Map<String, Object> map);

	public String getItemAsString(Item item);
}
