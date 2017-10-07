package com.fulg.life.migration.facade;

import java.util.List;

import com.fulg.life.migration.dto.NewModelTable;
import com.fulg.life.migration.exception.BusinessException;
import com.fulg.life.migration.exception.PopulatorNotFoundException;
import com.fulg.life.model.entities.Item;

public interface NewModelFacade {
    void createInstance(Item item) throws BusinessException;

    List<String> getEntityNames() throws BusinessException;

    NewModelTable getTableInfo(String tableName, boolean loadContent) throws PopulatorNotFoundException;
}
