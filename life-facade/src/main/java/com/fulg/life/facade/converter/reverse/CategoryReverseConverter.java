package com.fulg.life.facade.converter.reverse;

import com.fulg.life.data.CategoryData;
import com.fulg.life.model.entities.Category;
import com.fulg.life.service.CategoryService;
import com.fulg.life.service.FrequencyTypeService;

import javax.annotation.Resource;

/**
 * Created by alessandro.fulgenzi on 03/07/16.
 */
public class CategoryReverseConverter extends AbstractReverseConverter<Category, CategoryData> {
    @Resource
    CategoryService categoryService;
    @Resource
    FrequencyTypeService frequencyTypeService;

    @Override
    protected Category createTarget(Long pk)
    {
        Category item = null;
        if (pk != null)
        {
            item = (Category) categoryService.getOne(pk);
        } else
        {
            item = new Category();
        }
        return item;
    }

    @Override
    public Category populate(CategoryData source, Category target)
    {
        target.setCode(source.getCode());
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        if (source.getFrequency() != null && source.getFrequency().getFrequencyType() != null)
        {
            target.setFrequencyType(frequencyTypeService.getByCode(source.getFrequency().getFrequencyType().getCode()));
            target.setFrequencyInterval(Integer.valueOf(source.getFrequency().getFrequencyInterval()));
            if (source.getFrequency().getFrequencyEndDate() != null)
            {
                target.setFrequencyEndDate(source.getFrequency().getFrequencyEndDate());
            }
        }
        return target;
    }

}
