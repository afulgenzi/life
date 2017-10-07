package com.fulg.life.facade.converter;

import com.fulg.life.data.FrequencyTypeData;
import com.fulg.life.model.entities.FrequencyType;

/**
 * Created by alessandro.fulgenzi on 14/07/16.
 */
public class FrequencyTypeConverter extends AbstractConverter<FrequencyTypeData, FrequencyType> {
    private UserConverter userConverter;

    @Override
    protected FrequencyTypeData createTarget() {
        return new FrequencyTypeData();
    }

    @Override
    public FrequencyTypeData populate(FrequencyType source, FrequencyTypeData target) {
        if (source != null) {
            target.setPk(source.getPk());
            target.setCode(source.getCode());
            target.setTitle(source.getTitle());
            target.setDescription(source.getDescription());
            target.setIntervalUnit(source.getIntervalUnit());
            target.setIntervalUnitInternal(source.getIntervalUnitInternal());
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
