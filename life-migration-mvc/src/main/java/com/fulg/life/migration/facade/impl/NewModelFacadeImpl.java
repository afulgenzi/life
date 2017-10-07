package com.fulg.life.migration.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.fulg.life.migration.converter.NewModelCovnerter;
import com.fulg.life.migration.dto.NewModelTable;
import com.fulg.life.migration.exception.BusinessException;
import com.fulg.life.migration.exception.PopulatorNotFoundException;
import com.fulg.life.migration.facade.NewModelFacade;
import com.fulg.life.model.entities.Item;
import com.fulg.life.model.repository.locator.RepositoryLocator;

public class NewModelFacadeImpl implements NewModelFacade {
    RepositoryLocator repositoryLocator;
    List<String> entityList;
    NewModelCovnerter newModelConverter;

    @Override
    public void createInstance(Item item) throws BusinessException {
        return;
    }

    @Override
    public List<String> getEntityNames() throws BusinessException {
        return entityList;
    }

    @Override
    @Transactional
    public NewModelTable getTableInfo(String tableName, boolean loadContent) throws PopulatorNotFoundException {
        JpaRepository<Item, Long> repository = (JpaRepository<Item, Long>) repositoryLocator.getInstance(tableName);

        List<Item> items = repository.findAll();

        NewModelTable newModelTable = new NewModelTable();
        newModelTable.setName(tableName);
        if (items != null) {
            newModelTable.setSize(items.size());
            newModelTable.setRows(getNewModelConverter().convert(items));
        }
        return newModelTable;
    }

    protected List<String> getEntityList() {
        return entityList;
    }

    @Required
    public void setEntityList(List<String> entityList) {
        this.entityList = entityList;
    }

    protected RepositoryLocator getRepositoryLocator() {
        return repositoryLocator;
    }

    @Required
    public void setRepositoryLocator(RepositoryLocator repositoryLocator) {
        this.repositoryLocator = repositoryLocator;
    }

    protected NewModelCovnerter getNewModelConverter() {
        return newModelConverter;
    }

    @Required
    public void setNewModelConverter(NewModelCovnerter newModelConverter) {
        this.newModelConverter = newModelConverter;
    }

}
