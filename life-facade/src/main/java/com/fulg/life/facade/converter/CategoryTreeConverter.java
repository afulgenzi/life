package com.fulg.life.facade.converter;

import com.fulg.life.data.tree.CategoryTreeData;
import com.fulg.life.model.entities.Category;
import com.fulg.life.service.CategoryService;

/**
 * Created by alessandro.fulgenzi on 03/07/16.
 */
public class CategoryTreeConverter extends AbstractConverter<CategoryTreeData, Category> {
    private UserConverter userConverter;

    @Override
    protected CategoryTreeData createTarget() {
        return new CategoryTreeData();
    }

    @Override
    public CategoryTreeData populate(Category source, CategoryTreeData target) {
        if (source != null) {
            target.setPk(source.getPk());
            target.setCode(source.getCode());
            target.setText(source.getTitle());
        }
        return target;
    }

    public UserConverter getUserConverter() {
        return userConverter;
    }

    public void setUserConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

}
