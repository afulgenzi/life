package com.fulg.life.facade.converter;

import com.fulg.life.data.CategoryData;
import com.fulg.life.data.FrequencyData;
import com.fulg.life.model.entities.Category;
import com.fulg.life.service.CategoryService;

import javax.annotation.Resource;

/**
 * Created by alessandro.fulgenzi on 02/07/16.
 */
public class CategoryConverter extends AbstractConverter<CategoryData, Category> {
    @Resource
    private FrequencyTypeConverter frequencyTypeConverter;
    @Resource
    private CategoryService categoryService;

    @Override
    protected CategoryData createTarget()
    {
        return new CategoryData();
    }

    @Override
    public CategoryData populate(Category source, CategoryData target)
    {
        if (source != null)
        {
            target.setPk(source.getPk());
            target.setCode(source.getCode());
            target.setTitle(source.getTitle());
            target.setDescription(source.getDescription());
            target.setPath(categoryService.getCategoryPath(source));
            if (source.getFrequencyType() != null)
            {
                final FrequencyData frequency = new FrequencyData();
                frequency.setFrequencyInterval(source.getFrequencyInterval());
                frequency.setFrequencyType(frequencyTypeConverter.convert(source.getFrequencyType()));
                frequency.setFrequencyEndDate(source.getFrequencyEndDate());
                target.setFrequency(frequency);
            }
        }
        return target;
    }
}
