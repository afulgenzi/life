package com.fulg.life.migration.converter.populator.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.CollectionUtils;

import com.fulg.life.migration.converter.populator.NewModelPopulator;
import com.fulg.life.migration.exception.PopulatorNotFoundException;
import com.fulg.life.model.entities.Item;

public class ItemPopulator implements NewModelPopulator {

    Map<String, NewModelPopulator> populators;

	@Override
	public void populate(Item item, Map<String, Object> map) {
		Item genericItem = (Item) item;
		map.put("Pk", genericItem.getPk());
	}

	@Override
	public String getItemAsString(Item item) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer retValue = new StringBuffer("[");
        try {
			NewModelPopulator itemPopulator = getPopulator(item);
			itemPopulator.populate(item, map);
			
			if (!CollectionUtils.isEmpty(map)){
				int count=0;
				for (String key : map.keySet()) {
					count++;
					if (count>1){
						retValue.append(",");
					}
					retValue.append(key+":"+map.get(key));
				}
			}
		} catch (PopulatorNotFoundException e) {
			e.printStackTrace();
		}
		retValue.append("]");
		return retValue.toString();
	}

    private NewModelPopulator getPopulator(Item item) throws PopulatorNotFoundException {
        if (item != null) {
            String itemName = item.getClass().getSimpleName();
            NewModelPopulator populator = populators.get(itemName);
            if (populator == null) {
                throw new PopulatorNotFoundException(itemName);
            }
            return populator;
        }
        return null;
    }

    protected Map<String, NewModelPopulator> getPopulators() {
        return populators;
    }

    @Required
    public void setPopulators(Map<String, NewModelPopulator> populators) {
        this.populators = populators;
    }

}
