package com.fulg.life.data;

/**
 * Created by alessandro.fulgenzi on 18/07/16.
 */
public class CategoryAlertData {
    private CategoryData category;
    private BankAccountMovementData latestMovement;
    private Integer alertLevel;

    public CategoryData getCategory()
    {
        return category;
    }

    public void setCategory(CategoryData category)
    {
        this.category = category;
    }

    public BankAccountMovementData getLatestMovement()
    {
        return latestMovement;
    }

    public void setLatestMovement(BankAccountMovementData latestMovement)
    {
        this.latestMovement = latestMovement;
    }

    public Integer getAlertLevel()
    {
        return alertLevel;
    }

    public void setAlertLevel(Integer alertLevel)
    {
        this.alertLevel = alertLevel;
    }
}
