package com.fulg.life.webmvc.data;

import com.fulg.life.data.CategoryData;
import com.fulg.life.data.FrequencyTypeData;

import javax.validation.constraints.NotNull;

/**
 * Created by alessandro.fulgenzi on 14/07/16.
 */
public class CategoryForm {
    @NotNull(message = "{general.required}")
    private CategoryData category;
    private Boolean noSupercategory;

    public CategoryData getCategory()
    {
        return category;
    }

    public void setCategory(CategoryData category)
    {
        this.category = category;
    }

    public Boolean getNoSupercategory()
    {
        return noSupercategory;
    }

    public void setNoSupercategory(Boolean noSupercategory)
    {
        this.noSupercategory = noSupercategory;
    }
}
