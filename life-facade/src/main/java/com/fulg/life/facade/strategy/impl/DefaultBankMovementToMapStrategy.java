package com.fulg.life.facade.strategy.impl;

import com.fulg.life.data.BankAccountMovementData;
import com.fulg.life.facade.strategy.BankMovementToMapStrategy;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alessandro.fulgenzi on 19/05/16.
 */
public class DefaultBankMovementToMapStrategy implements BankMovementToMapStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultBankMovementToMapStrategy.class);

    @Override
    public Map<String, List<BankAccountMovementData>> asMap(List<BankAccountMovementData> list)
    {
        final Map<String, List<BankAccountMovementData>> map = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(list))
        {
            for (final BankAccountMovementData mov : list)
            {
                final String key = getKeyForMovement(mov);
                final List<BankAccountMovementData> movs = map.get(key);
                if (map.get(key) == null)
                {
                    map.put(key, new ArrayList<BankAccountMovementData>());
                }
                map.get(key).add(mov);
            }
        }
        return map;
    }

    @Override
    public BankAccountMovementData popCorrespondingMovement(BankAccountMovementData mov, Map<String, List<BankAccountMovementData>> map)
    {
        final String key = getKeyForMovement(mov);
        final List<BankAccountMovementData> movs = map.get(key);
        if (CollectionUtils.isNotEmpty(movs))
        {
            return movs.remove(0);
        }
        return null;
    }

    @Override
    public List<BankAccountMovementData> asList(Map<String, List<BankAccountMovementData>> map)
    {
        final List<BankAccountMovementData> result = Lists.newArrayList();
        if (MapUtils.isNotEmpty(map))
        {
            for (List<BankAccountMovementData> movs : map.values())
            {
                result.addAll(movs);
            }
        }
        return result;
    }

    @Override
    public String getKeyForMovement(final BankAccountMovementData mov)
    {
        final DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
        final NumberFormat amountFormatter = new DecimalFormat("000.00");

        final String euKey = mov.getEu();
        final String dateKey = dateFormatter.format(mov.getDate());
        final String amountKey = amountFormatter.format(mov.getAmount());

        final String key = euKey + "_" + dateKey + "_" + amountKey;
        LOG.info("Key: [{}], Date: [{}], Description [{}]", key, mov.getDate().toString(), mov.getDescription());
        return key;
    }

}
