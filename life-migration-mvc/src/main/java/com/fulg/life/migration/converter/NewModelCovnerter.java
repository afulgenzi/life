package com.fulg.life.migration.converter;

import java.util.List;
import java.util.Map;

import com.fulg.life.migration.exception.PopulatorNotFoundException;
import com.fulg.life.model.entities.Item;

public interface NewModelCovnerter {

    public List<Map<String, Object>> convert(List<Item> item) throws PopulatorNotFoundException;
}
