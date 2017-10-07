package com.fulg.life.service.impl;

import com.fulg.life.model.entities.Bank;
import com.fulg.life.model.repository.BankRepository;
import com.fulg.life.service.BankService;
import com.googlecode.ehcache.annotations.Cacheable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service
public class DefaultBankService extends DefaultItemService implements BankService {
    @SuppressWarnings("unused")
    private static Logger LOG = LoggerFactory.getLogger(DefaultBankService.class);

    @Resource(name = "lifeServiceProperties")
    private Properties properties;
    @Resource
    private BankRepository bankRepository;

    @Resource
    String barclaysBank;
    @Resource
    String lloydsBank;
    @Resource
    String intesaBank;

    private Map<String, Bank> myCacheMap = new HashMap<String, Bank>();

    @Override
    @Cacheable(cacheName = "bankGetAll")
    public List<Bank> getAll() {
        List<Bank> items = bankRepository.findAll();
        for (Bank item : items) {
            addItemToCache(item);
        }
        return items;
    }

    @Override
    @Cacheable(cacheName = "bankGetOne")
    public Bank getOne(Long pk) {
        LOG.info("SEARCHING Bank FOR PK: " + pk);
        Bank Bank = bankRepository.findOne(pk);
        return Bank;
    }

    @Override
    public Bank getBarclays() {
        return getByCode(barclaysBank);
    }

    @Override
    public Bank getLloyds() {
        return getByCode(lloydsBank);
    }

    @Override
    public Bank getIntesa() {
        return getByCode(intesaBank);
    }

    @Override
    @Cacheable(cacheName = "bankGetByCode")
    public Bank getByCode(String code) {

        Bank bank = (Bank) getItemFromCacheByCode(code);
        if (bank == null) {
            LOG.info("BankGetByCode - getting value from db");
            bank = bankRepository.findByCode(code);

            if (bank != null) {
                addItemToCacheByCode(bank.getCode(), bank);
            }
        } else {
            LOG.debug("BankGetByCode - getting value from cache");
        }

        return bank;
    }

    @Override
    public Bank update(Bank bank) {
        bankRepository.save(bank);
        return bank;
    }

    @Override
    public Bank insert(Bank bank) {
        bankRepository.save(bank);
        return bank;
    }

    @Override
    public void delete(Bank bank) {
        bankRepository.delete(bank);
    }

}
