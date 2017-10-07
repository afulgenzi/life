package com.fulg.life.facade.converter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConverter<TARGET, SOURCE> {

    protected abstract TARGET createTarget();

    public TARGET convert(SOURCE source)
    {
        if (source == null)
        {
            return null;
        } else
        {
            return convert(source, createTarget());
        }
    }

    public TARGET convert(SOURCE source, TARGET target)
    {
        if (source == null)
        {
            return target;
        } else
        {
            return populate(source, target);
        }
    }

    public final List<TARGET> convertAll(List<SOURCE> list)
    {
        List<TARGET> retList = new ArrayList<TARGET>();

        for (SOURCE obj : list)
        {
            retList.add(this.convert(obj));
        }

        return retList;
    }

    protected abstract TARGET populate(SOURCE source, TARGET target);
}
