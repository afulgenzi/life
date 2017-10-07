package com.fulg.life.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.fulg.life.model.entities.Item;
import com.fulg.life.service.ItemService;
import org.springframework.stereotype.Service;

@Service
public abstract class DefaultItemService implements ItemService {

	private Map<Long, Item> itemMap = new HashMap<Long, Item>();
	private Map<String, Item> itemMapByCode = new HashMap<String, Item>();

	protected void addItemToCache(Item item) {
		if (!itemMap.containsKey(item.getPk())) {
			itemMap.put(item.getPk(), item);
		}
	}

	protected Item getItemFromCache(Long pk) {
		return itemMap.get(pk);
	}

	protected void addItemToCacheByCode(String code, Item item) {
		itemMapByCode.put(code, item);
	}

	protected Item getItemFromCacheByCode(String code) {
		return itemMapByCode.get(code);
	}

}
