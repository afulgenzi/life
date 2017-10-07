package com.fulg.life.migration.converter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.CollectionUtils;

import com.fulg.life.migration.converter.NewModelCovnerter;
import com.fulg.life.migration.converter.populator.NewModelPopulator;
import com.fulg.life.migration.exception.PopulatorNotFoundException;
import com.fulg.life.model.entities.Item;

public class NewModelConverterImpl implements NewModelCovnerter {

    Map<String, NewModelPopulator> populators;

    @Override
    public List<Map<String, Object>> convert(List<Item> items) throws PopulatorNotFoundException {
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();

        if (!CollectionUtils.isEmpty(items)) {
            // gets the populator
            Item firstItem = items.iterator().next();
            NewModelPopulator populator = getPopulator(firstItem);

            // iteration to populate all rows
            for (Item item : items) {
                Map<String, Object> map = new HashMap<String, Object>();
                populator.populate(item, map);
                rows.add(map);
            }
        }

        return rows;
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
