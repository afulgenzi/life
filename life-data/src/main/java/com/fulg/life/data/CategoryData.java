package com.fulg.life.data;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 01/07/16.
 */
public class CategoryData extends ItemData implements Comparable<CategoryData> {
    String code;
    String title;
    String description;
    String path;
    CategoryData supercategory;
    List<CategoryData> subcategories = Lists.newArrayList();
    FrequencyData frequency;

    public CategoryData getSupercategory()
    {
        return supercategory;
    }

    public void setSupercategory(CategoryData supercategory)
    {
        this.supercategory = supercategory;
    }

    public List<CategoryData> getSubcategories()
    {
        return subcategories;
    }

    public void setSubcategories(List<CategoryData> subcategories)
    {
        this.subcategories = subcategories;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public FrequencyData getFrequency()
    {
        return frequency;
    }

    public void setFrequency(FrequencyData frequency)
    {
        this.frequency = frequency;
    }

    public int compareTo(CategoryData item)
    {
        if (this.title.compareTo(item.getTitle()) == 0)
        {
            return this.code.compareTo(item.getCode());
        } else
        {
            return this.title.compareTo(item.getTitle());
        }
    }

}
