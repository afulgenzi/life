package com.fulg.life.service.impl;

import com.fulg.life.model.entities.FrequencyType;
import com.fulg.life.model.repository.FrequencyTypeRepository;
import com.fulg.life.service.FrequencyTypeService;
import com.googlecode.ehcache.annotations.Cacheable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by alessandro.fulgenzi on 13/07/16.
 */
@Service
public class DefaultFrequencyTypeService extends DefaultItemService implements FrequencyTypeService {
    @SuppressWarnings("unused")
    private static Logger LOG = LoggerFactory.getLogger(DefaultFrequencyTypeService.class);

    private static final String DAILY_CODE = "m";
    private static final String WEEKLY_CODE = "w";
    private static final String MONTHLY_CODE = "m";
    private static final String YEARLY_CODE = "y";

    @Resource(name = "lifeServiceProperties")
    private Properties properties;
    @Resource
    private FrequencyTypeRepository FrequencyTypeRepository;

    private Map<String, FrequencyType> myCacheMap = new HashMap<String, FrequencyType>();

    @Override
    @Cacheable(cacheName = "frequencyTypeGetAll")
    public List<FrequencyType> getAll() {
        List<FrequencyType> items = FrequencyTypeRepository.findAll();
        for (FrequencyType item : items) {
            addItemToCache(item);
        }
        return items;
    }

    @Override
//    @Cacheable(cacheName = "frequencyTypeGetOne")
    public FrequencyType getOne(Long pk) {
        LOG.info("SEARCHING FrequencyType FOR PK: " + pk);
        FrequencyType FrequencyType = FrequencyTypeRepository.findOne(pk);
        return FrequencyType;
    }

    @Override
    public FrequencyType getDaily() {
        return getByCode(DAILY_CODE);
    }

    @Override
    public FrequencyType getWeekly() {
        return getByCode(WEEKLY_CODE);
    }

    @Override
    public FrequencyType getMonthly() {
        return getByCode(MONTHLY_CODE);
    }

    @Override
    public FrequencyType getYearly() {
        return getByCode(YEARLY_CODE);
    }

    @Override
    @Cacheable(cacheName = "bankGetByCode")
    public FrequencyType getByCode(String code) {

        FrequencyType item = (FrequencyType) getItemFromCacheByCode(code);
        if (item == null) {
            LOG.info("FrequencyTypeGetByCode - getting value from db");
            item = FrequencyTypeRepository.findByCode(code);

            if (item != null) {
                addItemToCacheByCode(item.getCode(), item);
            }
        } else {
            LOG.debug("FrequencyTypeGetByCode - getting value from cache");
        }

        return item;
    }

    @Override
    public FrequencyType update(FrequencyType bank) {
        FrequencyTypeRepository.save(bank);
        return bank;
    }

    @Override
    public FrequencyType insert(FrequencyType bank) {
        FrequencyTypeRepository.save(bank);
        return bank;
    }

    @Override
    public void delete(FrequencyType bank) {
        FrequencyTypeRepository.delete(bank);
    }

}
