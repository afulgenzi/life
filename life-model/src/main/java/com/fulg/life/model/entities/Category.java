package com.fulg.life.model.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends GenericItem {

    @ManyToOne
    Category supercategory;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "supercategory", cascade = CascadeType.REMOVE)
    List<Category> subcategories;

    @Column(name = "CODE")
    String code;

    @ManyToOne
    FrequencyType frequencyType;

    @Column(name = "FREQUENCY_INTERVAL")
    Integer frequencyInterval = Integer.valueOf(0);

    @Column(name = "FREQUENCY_END_DATE")
    Date frequencyEndDate;

    public Category getSupercategory()
    {
        return supercategory;
    }

    public void setSupercategory(Category supercategory)
    {
        this.supercategory = supercategory;
    }

    public List<Category> getSubcategories()
    {
        return subcategories;
    }

    public void setSubcategories(List<Category> subcategories)
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

    public FrequencyType getFrequencyType()
    {
        return frequencyType;
    }

    public void setFrequencyType(FrequencyType frequencyType)
    {
        this.frequencyType = frequencyType;
    }

    public Integer getFrequencyInterval()
    {
        return frequencyInterval;
    }

    public void setFrequencyInterval(Integer frequencyInterval)
    {
        this.frequencyInterval = frequencyInterval;
    }

    public Date getFrequencyEndDate()
    {
        return frequencyEndDate;
    }

    public void setFrequencyEndDate(Date frequencyEndDate)
    {
        this.frequencyEndDate = frequencyEndDate;
    }
}
