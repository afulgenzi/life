package com.fulg.life.service;

import com.fulg.life.model.entities.FrequencyType;

import java.util.List;

/**
 * Created by alessandro.fulgenzi on 13/07/16.
 */
public interface FrequencyTypeService extends ItemService {
    List<FrequencyType> getAll();

    FrequencyType getByCode(String code);
    FrequencyType getDaily();
    FrequencyType getWeekly();
    FrequencyType getMonthly();
    FrequencyType getYearly();

    FrequencyType update(FrequencyType eu);
    FrequencyType insert(FrequencyType eu);
    void delete(FrequencyType eu);
}
